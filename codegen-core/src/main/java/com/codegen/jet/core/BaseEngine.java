package com.codegen.jet.core;

import com.codegen.jet.core.exception.EngineException;

import java.util.Map;

/**
 * Created by RoyChan on 2017/12/8.
 */
public abstract class BaseEngine {

    protected String templateRootDir;

    /**
     *
     * @param templateRootDir 模板文件的根目录
     * @throws EngineException
     */
    public BaseEngine(String templateRootDir) throws EngineException{
        this.templateRootDir = templateRootDir;
    }

    /**
     *
     * @param data
     * @param templateFile 需要生成的模板文件路径
     * @param destFile
     * @throws EngineException
     */
    public abstract void generate(Map<String, Object> data, String templateFile, String destFile) throws EngineException;


    public String getTemplateRootDir() {
        return templateRootDir;
    }
}
