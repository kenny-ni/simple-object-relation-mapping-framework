package com.jiageng.sorm.utils;

import java.sql.PreparedStatement;

/**
 * encapsulate common operations
 */
public class JDBCUtils {
    public static void setParams(PreparedStatement ps, Object[] params) throws Exception{
        if (params != null){
            for(int i = 0; i < params.length; ++i){
                ps.setObject(i + 1, params[i]);
            }
        }
    }
}
