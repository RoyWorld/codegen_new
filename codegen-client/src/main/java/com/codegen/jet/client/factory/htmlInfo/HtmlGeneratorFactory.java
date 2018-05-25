package com.codegen.jet.client.factory.htmlInfo;

import com.codegen.core.jet.BaseFactory;
import com.codegen.core.jet.BaseDomain;
import com.codegen.core.jet.BaseEngine;


/**
 * Created by RoyChan on 2017/12/11.
 */
public class HtmlGeneratorFactory extends BaseFactory {

    public HtmlGeneratorFactory(BaseEngine baseEngine) {
        super(baseEngine);
    }

    @Override
    public void getDataFromDomain(BaseDomain... baseDomain) {
        for (BaseDomain bd : baseDomain){
            data.putAll(resloverClass(bd));
        }
    }

    private void getDataFromDomain(BaseDomain baseDomain) {
        data = resloverClass(baseDomain);
    }

}
