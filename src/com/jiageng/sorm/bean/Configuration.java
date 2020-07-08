package com.jiageng.sorm.bean;

/**
 * used to load and store configuration information (not used in this demo, leave for more complex configuration file)
 */
public class Configuration {
    private static String usingDB;
    private static String driver;
    private static String url;
    private static String user;
    private static String pwd;
    private static String srcPath;
    private static String poPackage;
    private static String minConn;
    private static String maxConn;

    private Configuration(){}

    public static String getUsingDB() {
        return usingDB;
    }

    public static void setUsingDB(String usingDB) {
        Configuration.usingDB = usingDB;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        Configuration.driver = driver;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Configuration.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Configuration.user = user;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        Configuration.pwd = pwd;
    }

    public static String getSrcPath() {
        return srcPath;
    }

    public static void setSrcPath(String srcPath) {
        Configuration.srcPath = srcPath;
    }

    public static String getPoPackage() {
        return poPackage;
    }

    public static void setPoPackage(String poPackage) {
        Configuration.poPackage = poPackage;
    }

    public static String getMinConn() {
        return minConn;
    }

    public static void setMinConn(String minConn) {
        Configuration.minConn = minConn;
    }

    public static String getMaxConn() {
        return maxConn;
    }

    public static void setMaxConn(String maxConn) {
        Configuration.maxConn = maxConn;
    }
}
