package com.ynsh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syb on 2017/5/5.
 */
public class Reflection {
    private static final Logger logger = LoggerFactory.getLogger(Reflection.class);

    /**
     * 根据属性名获取属性值
     * */
    public Object getFieldValueByName(String fieldName, Object o) {

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            logger.error("根据属性名获取属性值失败: {}",e.getMessage());
            return null;
        }
    }

    /**
     * 根据属性名获取属性类型
     * @param fieldName 属性名
     * @param o 对象
     * @return
     */
    public String getFieldTypeByName(String fieldName, Object o) {
        try {
            List<Map> fieldsInfo = new ArrayList();
            fieldsInfo = this.getFiledsInfo(o);

            for (int i=0;i<fieldsInfo.size();i++){
                Map map = fieldsInfo.get(i);
                if (fieldName.equals(map.get("name"))) return map.get("type").toString();
            }
            return null;
        } catch (Exception e){
            logger.error("获取属性类型失败,{}",e.getMessage());
            return null;
        }
    }

    /**
     * 获取属性名数组
     * */
    public String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    public List getFiledsInfo(Object o) throws Exception{
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        List list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public Object[] getFiledValues(Object o) throws Exception{
        String[] fieldNames=this.getFiledName(o);
        Object[] value=new Object[fieldNames.length];
        for(int i=0;i<fieldNames.length;i++){
            value[i]=this.getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }
}
