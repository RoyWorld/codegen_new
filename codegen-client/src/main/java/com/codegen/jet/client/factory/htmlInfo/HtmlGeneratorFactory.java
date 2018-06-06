package com.codegen.jet.client.factory.htmlInfo;

import com.codegen.jet.core.BaseFactory;
import com.codegen.jet.core.BaseDomain;
import com.codegen.jet.core.BaseEngine;


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
