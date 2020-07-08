package com.jiageng.sorm.core;

/**
 * implementation of TypeConvertor interface for MySQL
 */
public class MySQLTypeConverter implements TypeConverter {

    @Override
    public String databaseType2JavaType(String type) {
        if("varchar".equalsIgnoreCase(type) || "char".equalsIgnoreCase(type)){
            return "String";
        }else if("int".equalsIgnoreCase(type)
                || "tinyint".equalsIgnoreCase(type)
                || "smallint".equalsIgnoreCase(type)
                || "integer".equalsIgnoreCase(type)){
            return "Integer";
        } else if("bigint".equalsIgnoreCase(type)){
            return "Long";
        } else if ("double".equalsIgnoreCase(type)){
            return "Double";
        } else if ("float".equalsIgnoreCase(type)){
            return "Float";
        } else if ("clob".equalsIgnoreCase(type)){
            return "java.sql.Clob";
        }else if ("blob".equalsIgnoreCase(type)){
            return "java.sql.Blob";
        }else if ("timestamp".equalsIgnoreCase(type)){
            return "java.sql.Timestamp";
        } else if ("time".equalsIgnoreCase(type)){
            return "java.sql.Time";
        } else if ("date".equalsIgnoreCase(type)){
            return "java.sql.Date";
        }
        return null;
    }

    @Override
    public String javaType2DatabaseType(String type) {
        return null;
    }
}
