package com.codegen.jet.client.domain.htmlInfo;

import com.codegen.core.jet.annotion.TemplateField;

/**
 * Created by RoyChan on 2017/12/12.
 */
public class Fruit {
    @TemplateField("name")
    String name;
    @TemplateField("developer")
    String country;

    public Fruit(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
