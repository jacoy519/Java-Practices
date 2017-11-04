package com.demo.dao;

import com.demo.entity.DemoEntity;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoEntityDao extends SqlMapClientDaoSupport {

    List<DemoEntity> queryDemoEntityListByCodeNo(String codeNo) {
        Map param = new HashMap<>();
        param.put("codeNo", codeNo);
        List<DemoEntity> demoEntityList = (List<DemoEntity>) getSqlMapClientTemplate().queryForList("demoEntityDao.queryByCodeNo", param);
        return demoEntityList;
    }

    List<DemoEntity> queryDemoEntityListByBeginDataAndEndDataAndCodeList(String beginData, String endData, List<String> codeList) {
        Map param = new HashMap<>();
        param.put("beginData", beginData);
        param.put("endData", endData);
        param.put("codeList", codeList);
        List<DemoEntity> demoEntityList = (List<DemoEntity>) getSqlMapClientTemplate().queryForList("demoEntityDao.queryByCodeNoAndData", param);
        return demoEntityList;
    }

}
