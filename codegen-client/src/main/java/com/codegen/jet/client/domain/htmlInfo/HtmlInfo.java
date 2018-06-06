package com.codegen.jet.client.domain.htmlInfo;

import com.codegen.jet.core.BaseDomain;
import com.codegen.jet.core.annotion.TemplateField;

import java.lang.*;
import java.util.List;

/**
 * Created by RoyChan on 2017/12/11.
 */
public class HtmlInfo extends BaseDomain {

    @TemplateField("title")
    public String title;

    @TemplateField("exampleObject")
    public ExampleObject exampleObject;

    @TemplateField("systems")
    public List<ExampleObject> system;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExampleObject getExampleObject() {
        return exampleObject;
    }

    public void setExampleObject(ExampleObject exampleObject) {
        this.exampleObject = exampleObject;
    }

    public List<ExampleObject> getSystem() {
        return system;
    }

    public void setSystem(List<ExampleObject> system) {
        this.system = system;
    }
}
