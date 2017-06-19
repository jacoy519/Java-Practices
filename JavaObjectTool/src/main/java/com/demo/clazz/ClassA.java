package com.demo.clazz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by medivh on 2017/6/19.
 */
public class ClassA {

    private int obj1;

    private Integer obj2;

    private HashMap<ClassB,ClassB> obj3;

    private HashSet<ClassB> set;

    //private List<ClassB> list;


    public int getObj1() {
        return obj1;
    }

    public void setObj1(int obj1) {
        this.obj1 = obj1;
    }

    public Integer getObj2() {
        return obj2;
    }

    public void setObj2(Integer obj2) {
        this.obj2 = obj2;
    }

    public HashSet<ClassB> getSet() {
        return set;
    }

    public void setSet(HashSet<ClassB> set) {
        this.set = set;
    }
}
