package com.codegen.jet.client.engine;

import com.codegen.core.jet.BaseEngine;
import com.codegen.core.jet.exception.EngineException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by RoyChan on 2017/12/11.
 */
public class VelocityTemplateEngine extends BaseEngine {

    //引擎的默认配置
    private static final Properties defalutPropertis = new Properties();
    //模板默认的编码方式
    private static final String defalutEncoding = "UTF-8";

    static {
        //设置模板的加载方式和路径，从class path中加载
        defalutPropertis.setProperty("resource.loader", "class");
        defalutPropertis.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    }

    //velocity引擎，加载模板
    private static final VelocityEngine ve = new VelocityEngine();
    //模板操作类
    private static Template template;
    //模板数据
    private static VelocityContext context;
    //模板根目录
    private static String templateRootDirPath;

    public VelocityTemplateEngine(String templateRootDir) throws EngineException {
        super(templateRootDir);
        init(templateRootDir);
    }

    private void init(String templateRootDir) throws EngineException {
        ve.init(defalutPropertis);
        templateRootDirPath = templateRootDir;
    }

    @Override
    public void generate(Map<String, Object> data, String templateFile, String destFile) throws EngineException {
        try {
            template = ve.getTemplate(templateFile, defalutEncoding);
            context = new VelocityContext();
            for (String key : data.keySet()) {
                context.put(key, data.get(key));
            }
            //静态文件生成
            BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            bw.append(writer.getBuffer());//Internally it does aSB.toString();
            bw.flush();
        } catch (Exception e) {
            throw new EngineException(e.getMessage());
        }
    }

}
