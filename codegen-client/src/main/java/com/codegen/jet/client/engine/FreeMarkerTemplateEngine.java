package com.codegen.jet.client.engine;

import com.codegen.jet.core.BaseEngine;
import com.codegen.jet.core.exception.EngineException;
import com.codegen.jet.dolphins.tools.FileHelper;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.*;

import java.io.*;
import java.util.*;

/**
 * Created by RoyChan on 2017/12/8.
 */
public class FreeMarkerTemplateEngine extends BaseEngine {

    private static Configuration cfg;

    public FreeMarkerTemplateEngine(String templateRootDir) throws EngineException {
        super(templateRootDir);
        this.init(templateRootDir);
    }

    private void init(String templateRootDir) throws EngineException {
        try {
            // 1. Configure FreeMarker
            //
            // You should do this ONLY ONCE, when your application starts,
            // then reuse the same Configuration object elsewhere.
            cfg = new Configuration();

            // Where do we load the templates from:
            cfg.setTemplateLoader(new FileTemplateLoader(FileHelper.getFileByClassLoader(templateRootDir)));

            // Some other recommended settings:
            cfg.setIncompatibleImprovements(new Version(2, 3, 20));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (Exception e){
            throw new EngineException(e.getMessage());
        }
    }


    @Override
    public void generate(Map<String, Object> data, String templateFile, String destFile) throws EngineException {
        try{
            Template template = cfg.getTemplate(templateFile);

            // For the sake of example, also write output into a file:
            Writer fileWriter = new FileWriter(new File(destFile));

            try {
                template.process(data, fileWriter);
            } finally {
                fileWriter.close();
            }
        } catch (Exception e) {
            throw new EngineException(e.getMessage());
        }
    }



}
