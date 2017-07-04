package com.example.demo.dao;

import com.example.demo.entity.DemoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by medivh on 2017/7/3.
 */

@Repository("demoEntityDao")
public interface DemoEntityDao extends CrudRepository<DemoEntity, Long> {
}
