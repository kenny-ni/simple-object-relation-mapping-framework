package com.jiageng.sorm.core;

import com.jiageng.sorm.bean.Column;
import com.jiageng.sorm.bean.Table;
import com.jiageng.sorm.utils.FileUtils;
import com.jiageng.sorm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * used to manage the relation between tables and corresponding classes
 */
public class TableContext {
    private static List<Table> tables = new ArrayList<Table>();
    private static Map<Class, Table> poClass2TableMap = new HashMap<>();

    private TableContext(){}

    public static Map<Class, Table> getPoClass2TableMap(){
        return poClass2TableMap;
    }

    public static List<Table> getTables(){return tables;}

    private static void loadTable (Connection conn) throws Exception{
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet tableSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
        while (tableSet.next()){
            String tableName = (String) tableSet.getObject("TABLE_NAME");
            Table table = new Table(tableName, new HashMap<String, Column>(), null);
            tables.add(table);
            ResultSet resultSet = dbmd.getColumns(null, "%", tableName, "%");//get all columns
            while (resultSet.next()){
                Column column = new Column(resultSet.getString("COLUMN_NAME"), resultSet.getString("TYPE_NAME"), 0);
                table.getColumns().put(column.getName(), column);
            }
            resultSet = dbmd.getPrimaryKeys(null, "%", tableName);//get primary key
            while (resultSet.next()){
                Column priKey = table.getColumns().get(resultSet.getString("COLUMN_NAME"));
                priKey.setKeyType(1);
                table.setPriKey(priKey);
            }
        }
        System.out.println("load tables completed");
    }
    public static void generatePo() throws Exception{
        Connection conn = null;
        try{
            conn = DBManager.getConnection();
            loadTable(conn);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBManager.closeConnection(conn);
        }
        Properties configuration = DBManager.getConfiguration();
        String db = configuration.getProperty("usingDB");
        String converterClass = "com.jiageng.sorm.core." + db + "TypeConverter";
        TypeConverter converter = (TypeConverter) Class.forName(converterClass).getConstructor().newInstance();
        for (Table table : tables) {
            FileUtils.createPOFile(table, converter);
        }
        System.out.println("PO files generated");
    }

    public static void updateMap(){
        Connection conn = null;
        try{
            conn = DBManager.getConnection();
            loadTable(conn);
            Properties configuration = DBManager.getConfiguration();
            String poPackage = configuration.getProperty("poPackage");
            for(Table table : tables){
                poClass2TableMap.put(Class.forName(poPackage + "." + StringUtils.firstCharUpper(table.getName())), table);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBManager.closeConnection(conn);
        }
    }

}
