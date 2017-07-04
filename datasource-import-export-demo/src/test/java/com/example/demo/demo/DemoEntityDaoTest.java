package com.example.demo.demo;

import com.example.demo.dao.DemoEntityDao;
import com.example.demo.entity.DemoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by medivh on 2017/7/3.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DemoEntityDaoTest {

    @Resource
    private DemoEntityDao demoEntityDao;

    @Test
    public void test() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setValue("test");
        demoEntityDao.save(demoEntity);
    }
}
