package com.jiageng.sorm.core;

import com.jiageng.po.Employee;
import com.jiageng.sorm.bean.Column;
import com.jiageng.sorm.bean.Table;
import com.jiageng.sorm.utils.JDBCUtils;
import com.jiageng.sorm.utils.ReflectUtils;
import com.jiageng.sorm.utils.StringUtils;

import java.lang.reflect.Field;
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
        Class c = object.getClass();
        Table table = TableContext.getPoClass2TableMap().get(c);
        StringBuilder sqlBuilder = new StringBuilder("insert into " + table.getName() + " (");
        Field[] fields = c.getDeclaredFields();
        List<Object> params = new ArrayList<>();
        for (Field field: fields){
            String fieldName = field.getName();
            Object fieldValue = ReflectUtils.invokeGetter(object, fieldName);
            if(fieldValue != null){
                sqlBuilder.append(fieldName + ", ");
                params.add(fieldValue);
            }
        }
        sqlBuilder.setCharAt(sqlBuilder.length() - 2, ')');
        sqlBuilder.append("values (");
        for (int i = 0; i < params.size(); ++i){
            sqlBuilder.append("?, ");
        }
        sqlBuilder.setCharAt(sqlBuilder.length() - 2, ')');
        executeDML(sqlBuilder.toString(), params.toArray());
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
    public void update(Object object, String[] fields){
     Class c = object.getClass();
     Table table = TableContext.getPoClass2TableMap().get(c);
     String priKey = table.getPriKey().getName();
     List<Object> params = new ArrayList<>();
     StringBuilder sqlBuilder = new StringBuilder("update " + table.getName() + " set ");
     for (String field: fields){
         Object fieldValue = ReflectUtils.invokeGetter(object, field);
         params.add(fieldValue);
         sqlBuilder.append(field + "=?, ");
     }
     sqlBuilder.setCharAt(sqlBuilder.length() - 2, ' ');
     sqlBuilder.append("where " + priKey + "=?");
     params.add(ReflectUtils.invokeGetter(object, priKey));
     executeDML(sqlBuilder.toString(), params.toArray());
    }

    /**
     * update one record
     * @param object Java bean object of the record
     */
    public void update(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; ++i){
            columns[i] = fields[i].getName();
        }
        update(object, columns);
    }

    /**
     * update selected fields of all records
     * @param clazz class object of corresponding tables
     * @param fields columns to be updated
     * @param values new values
     * @return rows affected
     */
    public int update(Class clazz, String[] fields, Object[] values){
        Table table = TableContext.getPoClass2TableMap().get(clazz);
        StringBuilder sqlBuilder = new StringBuilder("update " + table.getName() + " set ");
        for (String field: fields){
            sqlBuilder.append(field + "=?, ");
        }
        sqlBuilder.setCharAt(sqlBuilder.length() - 2, ' ');
        return executeDML(sqlBuilder.toString(), values);
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
        e.setID(4);
        e.setAge(24);
        e.setName("Genny");
        e.setContact("858-203-8323");
        new Query().update(e);
    }
}
