package com.demo;

import com.demo.builder.JavaObjectBuilder;
import com.demo.clazz.ClassA;
import com.demo.clazz.ClassB;
import com.demo.clazz.ClassC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Created by medivh on 2017/6/19.
 */
public class Client {

    public  Set<Map<List<ClassB>,List<ClassA>>> test(){
        return null;
    }

    public static void main(String[] args) throws  Exception{
        Method method = new Client().getClass().getMethod("test");
        JavaObjectBuilder builder = new JavaObjectBuilder(method.getGenericReturnType());
        Object instance = builder.build();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        System.out.println(gson.toJson(instance));

    }
}
