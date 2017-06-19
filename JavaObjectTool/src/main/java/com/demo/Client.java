package com.demo;

import com.demo.builder.JavaObjectBuilder;
import com.demo.clazz.ClassA;
import com.demo.clazz.ClassB;
import com.demo.clazz.ClassC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by medivh on 2017/6/19.
 */
public class Client {

    public static void main(String[] args) {
        JavaObjectBuilder builder = new JavaObjectBuilder("com.demo.clazz.ClassA");
        List<ClassA> clazzA = new ArrayList<ClassA>();
        Object instance = builder.build();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

        System.out.println(gson.toJson(instance));
    }
}
