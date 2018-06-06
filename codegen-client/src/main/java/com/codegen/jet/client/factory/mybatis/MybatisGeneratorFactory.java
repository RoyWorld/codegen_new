package com.codegen.jet.client.factory.mybatis;

import com.codegen.jet.core.BaseFactory;
import com.codegen.jet.dolphins.util.ReadProperties;
import com.codegen.jet.core.BaseEngine;
import com.codegen.jet.core.BaseDomain;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by RoyChan on 2017/12/6.
 */
public class MybatisGeneratorFactory extends BaseFactory {


    public MybatisGeneratorFactory(BaseEngine baseEngine) {
        super(baseEngine);
    }

    @Override
    public void getDataFromDomain(BaseDomain... baseDomains) {
        getDataFromDomain(baseDomains[0]);
    }


    private void getDataFromDomain(BaseDomain baseDomain) {
        data = new HashMap();
        data.put("fileName", baseDomain.getFileName());
        data.put("table", baseDomain);
        data.putAll(ReadProperties.getProperties());


        data.put("env", System.getenv());
        data.put("now", new Date());
    }
}
