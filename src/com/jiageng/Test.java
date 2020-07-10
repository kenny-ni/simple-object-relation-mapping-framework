package com.jiageng;

import com.jiageng.po.Employee;
import com.jiageng.sorm.bean.Table;
import com.jiageng.sorm.core.Query;
import com.jiageng.sorm.core.QueryFactory;
import com.jiageng.sorm.core.TableContext;

import java.util.List;

public class Test {
    public static void main(String[] args){
        Query query = QueryFactory.getQuery(); //get query object
        TableContext.updateMap(); // init map from PO Class to table info
//        query.testUpdate();
//        query.testQueryRows();
//        query.testQueryValue();
        for (int i = 0; i < 100; ++i){
            testQueryRows(query);
        }
    }

    public static void testUpdate(Query query){
        Employee e = new Employee();
        e.setID(4);
        e.setAge(24);
        e.setName("Genny");
        e.setContact("111-111-1111");
        query.update(e);
    }

    public static void testQueryRows(Query query){
        List<Object> records = query.queryRows("select ID, Name, Age from employee where Age > ?", Employee.class,
                new Object[]{22});
        for (Object record: records){
            System.out.println("ID: " + ((Employee)record).getID() + " | Name: " + ((Employee)record).getName() + " | Age: " +((Employee)record).getAge());
        }
    }

    public void testQueryValue(Query query){
        Object count = query.queryValue("select count(*) from employee", null);
        System.out.println(count);
//        System.out.println(((Number) count).floatValue());
    }

}
