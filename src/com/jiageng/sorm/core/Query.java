package com.jiageng.sorm.core;

import com.jiageng.po.Employee;
import com.jiageng.sorm.bean.Column;
import com.jiageng.sorm.bean.Table;
import com.jiageng.sorm.utils.JDBCUtils;
import com.jiageng.sorm.utils.ReflectUtils;
import com.jiageng.sorm.utils.StringUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * main interface used to manipulate the database, hide the difference between different databases
 * @author Jiageng
 */
public class Query {
    /**
     * @param sql raw sql statement with '?'
     * @param params detail parameters to fill in '?'
     * @return the number of rows affected
     *
     */
    public int executeDML(String sql, Object[] params){
        Connection conn = DBManager.getConnection();
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(sql);
            JDBCUtils.setParams(ps, params);
            System.out.println(ps);
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBManager.closeConnection(ps, conn);
        }
        return 0;
    }

    /**
     * insert one record to the database
     * @param object Java bean of the record
     */
    public void insert(Object object){

        //Todo
    }

    /**
     * delete one record
     * @param object Java bean of the record
     */
    public void delete(Object object){
        Class c = object.getClass();
        Table table = TableContext.getPoClass2TableMap().get(c);
        Column id = table.getPriKey();
        Object keyValue = ReflectUtils.invokeGetter(object, id.getName());
        if (keyValue != null){
            String sql = "delete from " + table.getName() + " where " + id.getName() + "=? ";
            executeDML(sql, new Object[]{keyValue});
        }
    }

    /**
     * delete one record
     * @param clazz Class object corresponding to the table
     * @param priKey value of the primary key of the record
     */
    public void delete(Class clazz, Object priKey){
        Table table = TableContext.getPoClass2TableMap().get(clazz);
        Column id = table.getPriKey();
        String sql = "delete from " + table.getName() + " where " + id.getName() + "=? ";
        executeDML(sql, new Object[]{priKey});
    }

    /**
     * delete records
     * @param clazz Class object corresponding to the table
     * @param fields names of selected columns
     * @param values values of selected columns
     * @return rows affected
     */
    public int delete(Class clazz, String[] fields, Object[] values){
        Table table = TableContext.getPoClass2TableMap().get(clazz);
        StringBuilder sqlBuilder = new StringBuilder("delete from " + table.getName() + " where ");
        for (String field : fields){
            sqlBuilder.append(field).append("=? and ");
        }
        String sql = sqlBuilder.toString();
        return executeDML(sql.substring(0, sql.length() - 5), values);
    }

    /**
     * update one record
     * @param object Java bean of the record
     * @param fields columns to be updated
     */
    public void update(Object object, List<String> fields){
     //todo
    }

    /**
     * update records
     * @param clazz class object of corresponding tables
     * @param fields columns to be updated
     * @param values new values
     * @return rows affected
     */
    public int update(Class clazz, List<String> fields, List<Object> values){
        //todo
        return 0;
    }

    /**
     * return query results
     * @param sql raw sql statement with '?'
     * @param clazz corresponding java bean class
     * @param params parameters to fill '?'
     * @return list of corresponding java beans
     */
    public List<Object> queryRows(String sql, Class clazz, List<Object> params){
        //todo
        return null;
    }

    /**
     * return a single query value
     * @param sql raw sql statement with '?'
     * @param params parameters to fill '?'
     * @return a value
     */
    public Object queryValue(String sql, List<Object> params){
        //todo
        return null;
    }

    public static void main(String[] args){
        TableContext.updateMap();
        Employee e = new Employee();
        new Query().delete(e.getClass(), new String[]{"Name", "Age"}, new Object[]{"Kenny", 24});
    }
}
