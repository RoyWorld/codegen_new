package com.codegen.jet.client.factory.Pet;

import com.codegen.jet.core.BaseDomain;
import com.codegen.jet.core.BaseEngine;
import com.codegen.jet.core.BaseFactory;

import java.util.HashMap;

/**
 * Created by RoyChan on 2018/5/25.
 */
public class PetFactory extends BaseFactory {

    public PetFactory(BaseEngine baseEngine) {
        super(baseEngine);
    }

    @Override
    public void getDataFromDomain(BaseDomain... baseDomain) {
        data = new HashMap();
        data.put("petList", baseDomain);
    }

}
