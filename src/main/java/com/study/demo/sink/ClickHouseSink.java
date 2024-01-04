package com.study.demo.sink;

import com.study.demo.entity.AccessEntity;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.io.Serializable;

public class ClickHouseSink extends RichSinkFunction<AccessEntity> implements Serializable {

    private static final long serialVersionUID = -4657100176803599916L;
}
