package com.jiageng.sorm.utils;

import java.lang.reflect.Method;

/**
 * encapsulate reflection relevant operations
 */
public class ReflectUtils {
    /**
     * invoke the getter method for the selected field and return the value of that field of the object (record)
     * @param o record object
     * @param field name of selected field
     * @return
     */
    public static Object invokeGetter(Object o, String field){
        try{
            Class c = o.getClass();
            Method m = c.getMethod("get" + StringUtils.firstCharUpper(field), null);
            return m.invoke(o, null);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
