package com.codegen.jet.core;

/**
 * Created by RoyChan on 2017/12/6.
 */
public abstract class BaseDomain {

    //模板文件对应的名字
    protected String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
