package com.jiageng.sorm.bean;

/**
 * used to store src code for a field in a table
 */
public class FieldGetSet {
    private String fieldName; // eg "private String 'Name'"
    private String fieldGetter; // eg "public String getName(){}"
    private String fieldSetter; // eg "public void setName(String name){}"

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldGetter() {
        return fieldGetter;
    }

    public void setFieldGetter(String fieldGetter) {
        this.fieldGetter = fieldGetter;
    }

    public String getFieldSetter() {
        return fieldSetter;
    }

    public void setFieldSetter(String fieldSetter) {
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String toString(){
        System.out.println(fieldName + fieldGetter + fieldSetter);
        return super.toString();
    }

}
