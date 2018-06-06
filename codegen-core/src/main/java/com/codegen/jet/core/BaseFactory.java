package com.codegen.jet.core;

import com.codegen.jet.core.exception.FactoryException;
import com.codegen.jet.core.annotion.TemplateField;
import com.codegen.jet.core.exception.EngineException;
import com.codegen.jet.dolphins.tools.FileHelper;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by RoyChan on 2017/12/6.
 */
public abstract class BaseFactory {

    //模板根目录路径
    protected String templateRootDir;

    //需生成模板的相对路径
    protected String srcTemplatePath;

    //目标生成目录
    protected String destPath;

    protected BaseEngine baseEngine;

    protected Map data;

    protected List<File> ignoreFiles;

    private final static Logger logger = LoggerFactory.getLogger(BaseFactory.class);

    public BaseFactory(BaseEngine baseEngine){
        this.baseEngine = baseEngine;
        this.templateRootDir = baseEngine.getTemplateRootDir();
    }

    /**
     *
     * @param srcTemplatePath
     * @param destPath
     */
    protected void setPath(String srcTemplatePath, String destPath){
        this.srcTemplatePath = srcTemplatePath;
        this.destPath = destPath;
    }

    public abstract void getDataFromDomain(BaseDomain... baseDomain);

    /**
     * 利用反射，将实体中含有注解的field转化一个dataMap
     * @param object
     * @return
     */
    protected Map<String, Object> resloverClass(Object object){
        Map<String, Object> data = new HashMap();
        try{
            Class clazz = object.getClass();
            for (Field field : clazz.getDeclaredFields()){
                if (field.isAnnotationPresent(TemplateField.class)){
                    String methodName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                    Method method = clazz.getMethod(methodName);
                    TemplateField templateField = field.getAnnotation(TemplateField.class);
                    data.put(templateField.value(), method.invoke(object));
                }
            }
        } catch (Exception e){
            FactoryException factoryException = new FactoryException(e.getMessage());
            logger.error(factoryException.getMessage());
        }
        return data;
    }

    /**
     * 提供一个接口给外部，可以从别处读取ignoreFiles
     * @param ignoreFile
     */
    public void readIgnoreFiles(File ignoreFile){
        try {
            setIgnoreFiles(ignoreFile);
            deleteIgnoreFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 约定ignoreFile.txt位于templateFileDir的根目录下
     */
    private void readIgnoreFilesFromRoot(){
        try {
            setIgnoreFiles(FileHelper.getFileByClassLoader(combinePathString2(templateRootDir, "ignorefile.txt")));
            deleteIgnoreFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void setIgnoreFiles(File ignoreFile) throws IOException{
        ignoreFiles = new LinkedList<>();
        FileReader fileReader = new FileReader(ignoreFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null){
            if (!line.equals("") && line.charAt(0) != '#'){
                ignoreFiles.add(new File(destPath + line));
            }
        }
        fileReader.close();
    }

    /**
     * 删除ignoreFiles
     */
    protected void deleteIgnoreFiles(){
        for (File file : ignoreFiles) {
            FileHelper.deleteQuietly(file);
        }
    }

    /**
     * 生成单个模板文件
     * 模板方法
     */
    public void generateSingleTemplates(String srcTemplatePath, String destPath){
        try {
            setPath(srcTemplatePath, destPath);
            baseEngine.generate(data, combinePathString2(templateRootDir, srcTemplatePath), destPath);
        } catch (EngineException e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 生成多个模板文件
     * 模板方法
     */
    public void generateMultiTemplates(String srcTemplatePath, String destPath){
        try {
            setPath(srcTemplatePath, combinePathString2(destPath, templateRootDir));
            File fileDir = FileHelper.getFileByClassLoader(combinePathString2(templateRootDir, srcTemplatePath));
            if (!srcTemplatePath.isEmpty()){
                generateTemplatesDir(fileDir, fileDir.getName());
            }else {
                generateTemplatesDir(fileDir, "");
            }
            readIgnoreFilesFromRoot();
        } catch (Exception e){
            logger.error(String.valueOf(e.getCause()));
        }
    }

    /**
     * 生成一个目录的模板
     * @param fileDir
     * @param parentPath
     */
    private void generateTemplatesDir(File fileDir, String parentPath){
        try {
            if (fileDir.listFiles().length == 0){
                File file = new File(combinePathString2(destPath, parentPath, fileDir.getName()));
                file.mkdir();
            }

            for (File file : fileDir.listFiles()){
                if (file.isDirectory()){
                    generateTemplatesDir(file, combinePathString(parentPath, file.getName()));
                }else {
                    String fileName = file.getName().replaceAll("\\$\\{.+\\}", (String) data.get("fileName"));
                    String combinaStr = combinePathString(destPath, parentPath);
                    String destFullPath = combinaStr.replaceAll("\\$\\{module\\}", (String) data.get("module"))
                            .replaceAll("\\$\\{basepackage\\}", (String) data.get("basepackage_dir"));
                    File destFile = new File(destFullPath + fileName);
                    Files.createParentDirs(destFile);
                    baseEngine.generate(data, combinePathString2(parentPath, file.getName()), destFile.getPath());
                }
            }
        } catch (Exception e){
            logger.error(String.valueOf(e.getCause()));
        }
    }

    /**
     * 将pathStr1、pathStr2、...拼接成pathStr1/pathStr2/..../
     * @param pathStrs
     * @return
     */
    private String combinePathString(String... pathStrs){
        String result = "";
        for (String pathStr : pathStrs){
            //对空字符串进行判断
            if (!pathStr.equals("")){
                result = result + pathStr + File.separator;
            }
        }
        return result;
    }

    /**
     * 将pathStr1、pathStr2、...拼接成pathStr1/pathStr2/....
     * @param pathStrs
     * @return
     */
    private String combinePathString2(String... pathStrs){
        String result = combinePathString(pathStrs);
        return result.substring(0, result.length()-1);
    }

    /**
     * 从pathStr中移除removePathStr
     * @param pathStr
     * @param removePathStr
     * @return
     */
    private String removePathString(String pathStr, String removePathStr){
        String[] pathStrArray = pathStr.split("\\\\");
        List<String> pathList = new LinkedList<>(Arrays.asList(pathStrArray));
        int index = pathList.indexOf(removePathStr);
        if (index == -1) {
            System.out.println("removePathStr not found");
        } else {
            pathList.remove(index);
        }
        return combinePathString(pathList.stream().toArray(String[]::new));
    }

}
