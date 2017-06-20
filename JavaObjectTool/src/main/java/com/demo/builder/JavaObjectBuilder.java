package com.demo.builder;

import com.google.gson.annotations.Since;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by medivh on 2017/6/19.
 */

public class JavaObjectBuilder {

    private Type rootType;

    private Map<String, Object> javaObjectCache = new ConcurrentHashMap<String, Object>();

    private Set<String> preparedJavaObjectRecord = new HashSet<String>();

    public JavaObjectBuilder(Type rootType) {
        this.rootType = rootType;
    }

    public Object build() throws Exception {
        return doBuild(rootType);
    }

    private Object doBuild(Type type) throws Exception {
        if(preparedJavaObjectRecord.contains(type.getTypeName())) {
            return null;
        }
        if(javaObjectCache.containsKey(type.getTypeName())) {
            return javaObjectCache.get(type.getTypeName());
        }

        if(type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            if(isList(pt.getRawType().getTypeName())) {
                List<Object> list = new ArrayList<Object>();
                Type tp1 = pt.getActualTypeArguments()[0];
                list.add(doBuild(tp1));
                javaObjectCache.put(type.getTypeName(), list);
                return list;
            }

            if(isSet(pt.getRawType().getTypeName())) {
                Set<Object> set = new HashSet<Object>();
                Type tp1 = pt.getActualTypeArguments()[0];
                set.add(doBuild(tp1));
                javaObjectCache.put(type.getTypeName(), set);
                return set;
            }
            if(isMap(pt.getRawType().getTypeName())) {
                Map<Object, Object> map = new HashMap<Object, Object>();
                Type tp1 = pt.getActualTypeArguments()[0];
                Type tp2 = pt.getActualTypeArguments()[1];
                map.put(doBuild(tp1), doBuild(tp2));
                javaObjectCache.put(type.getTypeName(), map);
                return map;
            }
            Object object = doBuild(pt.getRawType());
            javaObjectCache.put(type.getTypeName(), object);
            return object;
        } else {
            if(isObject(type.getTypeName())) {
                return null;
            }
            if(isByte(type.getTypeName()) || isShort(type.getTypeName()) || isInteger(type.getTypeName()) || isLong(type.getTypeName())) {
                return 0;
            }
            if(isFloat(type.getTypeName()) || isDouble(type.getTypeName())) {
                return 0.0;
            }
            if(isBoolean(type.getTypeName())) {
                return true;
            }
            if(isString(type.getTypeName())) {
                return "string";
            }
            Object instance = null;
            try {
                Class clazz = Class.forName(type.getTypeName());
                if(clazz.isArray()) {
                    return null;
                }
                if(clazz.isInterface()) {
                    return null;
                }
                if(clazz.isEnum()) {
                    return null;
                }
                instance = clazz.newInstance();
                Field[] fields =clazz.getDeclaredFields();
                for(Field field : fields) {
                    field.setAccessible(true);
                    if(Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    if(clazz.isAssignableFrom(field.getClass()));
                    Object value = doBuild( field.getGenericType());
                    field.set(instance, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            javaObjectCache.put(type.getTypeName(), instance);
            return instance;
        }
    }

    private boolean isObject(String className) {
        return "java.lang.object".equals(className);
    }

    private boolean isByte(String className) {
        return "byte".equals(className) || "java.lang.Byte".equals(className);
    }

    private boolean isShort(String className) {
        return "short".equals(className) || "java.lang.Short".equals(className);
    }

    private boolean isInteger(String className) {
        return "int".equals(className) || "java.lang.Integer".equals(className);
    }

    private boolean isLong(String className) {
        return "long".equals(className) || "java.lang.Long".equals(className);
    }

    private boolean isFloat(String className) {
        return "float".equals(className) || "java.lang.Float".equals(className);
    }

    private boolean isDouble(String className) {
        return "double".equals(className) || "java.lang.Double".equals(className);
    }

    private  boolean isBoolean(String className) {
        return "boolean".equals(className) || "java.lang.Boolean".equals(className);
    }

    private  boolean isString(String className) {
        return  "java.lang.String".equals(className);
    }

    private boolean isList(String className) throws Exception{
        Class clazz = Class.forName(className);
        return java.util.List.class.isAssignableFrom(clazz);
    }

    private boolean isSet(String className) throws Exception{
        Class clazz = Class.forName(className);
        return java.util.Set.class.isAssignableFrom(clazz);
    }

    private boolean isMap(String className) throws Exception {
        Class clazz = Class.forName(className);
        return java.util.Map.class.isAssignableFrom(clazz);
    }

    private boolean isArray(String className) {
        return className.endsWith("[]");
    }
 }
