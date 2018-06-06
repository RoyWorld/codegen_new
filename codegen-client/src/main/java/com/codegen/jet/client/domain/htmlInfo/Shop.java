package com.codegen.jet.client.domain.htmlInfo;

import com.codegen.jet.core.annotion.TemplateField;
import com.codegen.jet.core.BaseDomain;

import java.util.List;

/**
 * Created by RoyChan on 2017/12/12.
 */
public class Shop extends BaseDomain {
    @TemplateField("title")
    private String name;
    @TemplateField("exampleObject")
    private Fruit fruit;
    @TemplateField("systems")
    private List<Fruit> fruits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public List<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }
}
