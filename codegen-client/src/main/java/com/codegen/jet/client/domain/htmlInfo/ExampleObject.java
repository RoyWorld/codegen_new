package com.codegen.jet.client.domain.htmlInfo;

import com.codegen.core.jet.annotion.TemplateField;
import com.codegen.core.jet.BaseDomain;

/**
 * Created by RoyChan on 2017/12/11.
 */
public class ExampleObject extends BaseDomain {

    @TemplateField("name")
    public String name;

    @TemplateField("developer")
    public String developer;

    public ExampleObject(String name, String developer) {
        this.name = name;
        this.developer = developer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
