package com.demo.dao;

import com.demo.entity.DemoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DemoEntityDaoTest {

    private final static Logger logger = Logger.getLogger(DemoEntityDaoTest.class.getName());

    @Resource
    DemoEntityDao demoEntityDao;

    @Value("${test.property}")
    private String value;



    @Test
    public void test() {

        logger.info(value);
        List<DemoEntity> demoEntityList = demoEntityDao.queryDemoEntityListByCodeNo("1");
        logger.info("find entity number: " + demoEntityList.size());
    }

    @Test
    public void test2() {

        logger.info(value);
        List<String> codeList = new ArrayList<>();
        codeList.add("2");
        codeList.add("3");
        List<DemoEntity> demoEntityList = demoEntityDao.queryDemoEntityListByBeginDataAndEndDataAndCodeList("20170609", "20170611", codeList);
        logger.info("find entity number: " + demoEntityList.size());
    }

}
