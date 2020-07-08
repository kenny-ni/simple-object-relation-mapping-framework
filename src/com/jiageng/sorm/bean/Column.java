package com.jiageng.sorm.bean;

/**
 * information of a column of a table
 */
public class Column {
    private String name;
    private String dataType;
    private int keyType; //0:not key, 1:primary key, 2:foreign key

    public Column(String name, String dataType, int keyType){
        this.name = name;
        this.dataType = dataType;
        this.keyType = keyType;
    }

    public Column(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
