package com.demo.builder;

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

    private String className;

    private Map<String, Object> javaObjectCache = new ConcurrentHashMap<String, Object>();

    public JavaObjectBuilder(String className) {
        this.className = className;
    }

    public Object build() {
        return doBuild(this.className);
    }

    private Object doBuild(String className) {

        if(javaObjectCache.containsKey(className)) {
            return javaObjectCache.get(className);
        }
        if(isObject(className)) {
            return null;
        }
        if(isByte(className) || isShort(className) || isInteger(className) || isLong(className)) {
            return 0;
        }
        if(isFloat(className) || isDouble(className)) {
            return 0.0;
        }
        if(isBoolean(className)) {
            return true;
        }
        if(isString(className)) {
            return "string";
        }
        Object instance = null;
        try {
            Class clazz = Class.forName(className);
            if(clazz.isInterface()) {
                return null;
            }
            instance = clazz.newInstance();
            Field[] fields =clazz.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                Object value = null;
                if(Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if(isList(field.getType())) {
                    List<Object> list = new ArrayList<Object>();
                    Type genericType = field.getGenericType();
                    if(genericType != null && genericType instanceof ParameterizedType){
                        ParameterizedType pt = (ParameterizedType) genericType;
                        Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                        list.add(doBuild(genericClazz.getName()));
                    }
                    value = list;
                } else if(isSet(field.getType())) {
                    Set<Object> set = new HashSet<Object>();
                    Type genericType = field.getGenericType();
                    if(genericType != null && genericType instanceof ParameterizedType){
                        ParameterizedType pt = (ParameterizedType) genericType;
                        Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                        set.add(doBuild(genericClazz.getName()));
                    }
                    value = set;
                } else if(isMap(field.getType())) {
                    Map<Object, Object> map = new HashMap<Object, Object>();
                    Type genericType = field.getGenericType();
                    if(genericType != null && genericType instanceof ParameterizedType){
                        ParameterizedType pt = (ParameterizedType) genericType;
                        Class<?> keyClazz = (Class<?>)pt.getActualTypeArguments()[0];
                        Class<?> valueClazz = (Class<?>)pt.getActualTypeArguments()[1];
                        map.put(doBuild(keyClazz.getName()),doBuild(valueClazz.getName()));
                    }
                    value = map;
                } else {
                    value = doBuild(field.getType().getName());
                }
                field.set(instance, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaObjectCache.put(className, instance);
        return instance;
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

    private boolean isList(Class clazz) {
        return java.util.List.class.isAssignableFrom(clazz);
    }

    private boolean isSet(Class clazz) {
        return java.util.Set.class.isAssignableFrom(clazz);
    }

    private boolean isMap(Class clazz) {
        return java.util.Map.class.isAssignableFrom(clazz);
    }
 }
