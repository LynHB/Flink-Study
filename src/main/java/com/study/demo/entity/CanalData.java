package com.study.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * canal 数据
 * @param <T>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CanalData<T> {
    private List<T> data;
    private String database;
    private Long es;
    private String gtid;
    private Long id;
    private Boolean isDdl;
    private Object mysqlType;
    private Object old;
    private Object pkNames;
    private String sql;
    private Object sqlType;
    private String table;
    private Long ts;
    private String type;
}
