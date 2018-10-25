package com.codegen.jet.client.factory.htmlInfo;

import com.codegen.jet.core.BaseFactory;
import com.codegen.jet.core.BaseDomain;
import com.codegen.jet.core.BaseEngine;

import java.util.HashMap;


/**
 * Created by RoyChan on 2017/12/11.
 */
public class HtmlGeneratorFactory extends BaseFactory {

    public HtmlGeneratorFactory(BaseEngine baseEngine) {
        super(baseEngine);
    }

    @Override
    public void getDataFromDomain(BaseDomain... baseDomain) {
        data = new HashMap(20);
        for (BaseDomain bd : baseDomain){
            data.putAll(resloverClass(bd));
        }
    }

    public void getDataFromDomain(BaseDomain baseDomain) {
        data = resloverClass(baseDomain);
    }

}
