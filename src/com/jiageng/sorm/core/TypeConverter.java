package com.jiageng.sorm.core;

/**
 * convert data type between database system and java
 */
public interface TypeConverter {
    /**
     * convert data type in database system to corresponding type in Java
     * @param type data type in database system
     * @return data type in Java
     */
    public String databaseType2JavaType(String type);

    /**
     * convert data type in Java to corresponding type in database system
     * @param type data type in java
     * @return data type in database system
     */
    public String javaType2DatabaseType(String type);

}
