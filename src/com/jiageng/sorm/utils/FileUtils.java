package com.jiageng.sorm.utils;

import com.jiageng.sorm.bean.Column;
import com.jiageng.sorm.bean.FieldGetSet;
import com.jiageng.sorm.bean.Table;
import com.jiageng.sorm.core.DBManager;
import com.jiageng.sorm.core.MySQLTypeConverter;
import com.jiageng.sorm.core.TableContext;
import com.jiageng.sorm.core.TypeConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * generate source code for class representing each table
 */
public class FileUtils {
    /**
     * generate setter & getter source code for a field based on a specific converter
     * @param column information of a column
     * @param converter type converter
     * @return a FieldGetSet object with generated source code
     */
    private static FieldGetSet createFieldGetSet(Column column, TypeConverter converter){
        FieldGetSet fgs = new FieldGetSet();
        String fieldType = converter.databaseType2JavaType(column.getDataType());
        fgs.setFieldName("\tprivate " + fieldType + " " + column.getName() + ";\n");
        StringBuilder getBuilder = new StringBuilder();
        getBuilder.append("\tpublic ").append(fieldType).append(" get")
                .append(StringUtils.firstCharUpper(column.getName())).append("(){\n")
                .append("\t\treturn ").append(column.getName()).append(";\n")
                .append("\t}\n");
        fgs.setFieldGetter(getBuilder.toString());
        StringBuilder setBuilder = new StringBuilder();
        setBuilder.append("\tpublic ").append("void ").append("set")
                .append(StringUtils.firstCharUpper(column.getName())).append("(")
                .append(fieldType).append(" field){\n")
                .append("\t\t").append(column.getName()).append(" = field;\n").append("\t}\n");
        fgs.setFieldSetter(setBuilder.toString());
        return fgs;
    }

    /**
     * generate source code to create Java class for each table
     * @param table table information
     * @param converter type converter
     * @return source code for a class
     */
    private static String createPOClass(Table table, TypeConverter converter){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Column> columnMap = table.getColumns();
        List<FieldGetSet> fields = new ArrayList<>();
        for (Column c: columnMap.values()){
            fields.add(createFieldGetSet(c, converter));
        }
        //generate source code
        stringBuilder.append("package " + DBManager.getConfiguration().getProperty("poPackage") + ";\n\n")
                .append("import java.sql.*;\n").append("import java.util.*;\n\n")
                .append("public class " + StringUtils.firstCharUpper(table.getName()) + " {\n\n");
        for (FieldGetSet field: fields){
            stringBuilder.append(field.getFieldName());
        }
        stringBuilder.append("\n\n");
        for (FieldGetSet field: fields){
            stringBuilder.append(field.getFieldGetter()).append("\n").append(field.getFieldSetter()).append("\n");
        }
        stringBuilder.append("}\n");
        String src = stringBuilder.toString();
//        System.out.println(src);
        return src;
    }

    /**
     * generate .java files for auto generated PO class
     * @param table table info
     * @param converter type converter
     */
    public static void createPOFile(Table table, TypeConverter converter){
        String src = createPOClass(table, converter);
        Writer writer = null;
        try {
            Properties configuration = DBManager.getConfiguration();
            String path = configuration.getProperty("srcPath") + "/" +
                    configuration.getProperty("poPackage").replaceAll("\\.","/");

//            System.out.println(path);

            File f = new File(path);
            if (!f.exists()){ //create path if not exist
               f.mkdirs();
            }
            path = path + "/" + StringUtils.firstCharUpper(table.getName()) + ".java";
            f = new File(path);
            writer = new BufferedWriter(new FileWriter(f));
            writer.write(src);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try{
                    writer.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }



    //test
//    public static void main(String[] args){
//        List<Table> tables = TableContext.getTables();
//        Table table = tables.get(1);
//        createPOFile(table, new MySQLTypeConverter());
//    }
}
