package com.jiageng.sorm.bean;

import java.util.List;
import java.util.Map;

/**
 * class to store information of a table
 */
public class Table {
    private String name;
    private Map<String, Column> columns;
    private Column priKey;

    public Table(){}

    public Table(String name, Map<String, Column> columns, Column priKey) {
        this.name = name;
        this.columns = columns;
        this.priKey = priKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public Column getPriKey() {
        return priKey;
    }

    public void setPriKey(Column priKey) {
        this.priKey = priKey;
    }
}
