package com.example.demo.entity;

import javax.persistence.*;

/**
 * Created by medivh on 2017/7/3.
 */
@Entity
@Table(name = "t_demo_entity")
public class DemoEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="value")
    private String value;

    public DemoEntity() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
