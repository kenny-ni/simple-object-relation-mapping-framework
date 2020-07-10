package com.jiageng.sorm.core;

import java.util.Properties;

public class QueryFactory {
    /**
     * generate a Query instance based on configuration
     * @return
     */
    private static Query query;

    private QueryFactory(){}
    static {
        Properties configuration = DBManager.getConfiguration();
        String usingDB = configuration.getProperty("usingDB");
        String queryPkg = configuration.getProperty("queryPackage");
        try {
            query = (Query) Class.forName(queryPkg + "." + usingDB + "Query").getConstructor().newInstance(); //generate query instance
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Query getQuery(){
        return query;
    }
}
