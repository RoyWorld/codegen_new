package com.codegen.jet.client.factory.Pet;

import com.codegen.core.jet.BaseDomain;
import com.codegen.core.jet.BaseEngine;
import com.codegen.core.jet.BaseFactory;

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
