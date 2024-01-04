package com.study.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.study.demo.entity.AccessEntity;
import com.study.demo.entity.CanalData;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

public class LoginDataAnalyse {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        env.setParallelism(1);

        Properties properties = new Properties();
        properties.setProperty("auto.reset.offsets","earliest");
        properties.setProperty("enable.auto.commit", "true");


        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("bigdata-master:9092,bigdata-slave01:9092,bigdata-slave02:9092")
                .setGroupId("login_group")
                .setTopics("login_topic")
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setProperties(properties)
                .build();

        env.fromSource(kafkaSource,WatermarkStrategy.noWatermarks(),"kafkaSource")
                .map((MapFunction<String, CanalData<AccessEntity>>) value -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                    CanalData<AccessEntity> res =  objectMapper.readValue(value, new TypeReference<CanalData<AccessEntity>>() {});
                    return res;
                })
                .returns(new TypeHint<CanalData<AccessEntity>>(){})
                .filter((FilterFunction<CanalData<AccessEntity>>) value -> !value.getIsDdl())
                .returns(new TypeHint<CanalData<AccessEntity>>(){})
                .flatMap((FlatMapFunction<CanalData<AccessEntity>, AccessEntity>) (value, out) -> {
                    for(AccessEntity entity : value.getData()){
                        out.collect(entity);
                    }
                })
                .returns(Types.POJO(AccessEntity.class))
                .print();

        env.execute();

    };



}
