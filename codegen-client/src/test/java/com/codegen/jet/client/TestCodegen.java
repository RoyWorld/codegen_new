package com.codegen.jet.client;

import com.codegen.jet.client.domain.htmlInfo.*;
import com.codegen.jet.client.domain.TableDomain;
import com.codegen.jet.client.factory.Pet.PetFactory;
import com.codegen.jet.dolphins.TableFactory;
import com.codegen.jet.client.engine.FreeMarkerTemplateEngine;
import com.codegen.jet.client.engine.VelocityTemplateEngine;
import com.codegen.jet.dolphins.domains.Table;
import com.codegen.core.jet.exception.EngineException;
import com.codegen.jet.client.factory.htmlInfo.HtmlGeneratorFactory;
import com.codegen.jet.client.factory.mybatis.MybatisGeneratorFactory;
import com.codegen.jet.dolphins.util.ReadProperties;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Created by RoyChan on 2017/12/11.
 */
public class TestCodegen {

    private final static Logger logger = LoggerFactory.getLogger(TestCodegen.class);

    HtmlInfo htmlInfo;

    Shop shop;

    List<Table> tables;

    @Before
    public void setDomain(){
        htmlInfo = new HtmlInfo();

        htmlInfo.setTitle("Vogella example11");

        List<ExampleObject> systems = new ArrayList<>();
        systems.add(new ExampleObject("Android", "Google"));
        systems.add(new ExampleObject("iOS States", "Apple"));
        systems.add(new ExampleObject("Ubuntu", "Canonical"));
        systems.add(new ExampleObject("Windows7", "Microsoft"));
        htmlInfo.setSystem(systems);

        htmlInfo.setExampleObject(new ExampleObject("Java object", "me"));
    }

    @Before
    public void setDomain2(){
        shop = new Shop();

        shop.setName("Jack's grocery");

        List<Fruit> systems = new ArrayList<>();
        systems.add(new Fruit("apple", "China"));
        systems.add(new Fruit("banana", "Japan"));
        systems.add(new Fruit("watermelon", "Australia"));
        systems.add(new Fruit("strawberry", "Brazil"));
        shop.setFruits(systems);

        shop.setFruit(new Fruit("all fruit", "me"));
    }

    @Before
    public void setMybatisDomain(){
        tables = TableFactory.getInstance().getAllTables();
    }

    @Before
    public void setProperties(){
        ReadProperties.addPropertiesFile("generator.xml");
        ReadProperties.reload();
    }

    @Test
    public void testFreemarker(){
        try{
            //destFile
            File file = new File("hellworld.html");

            HtmlGeneratorFactory htmlGeneratorFactory = new HtmlGeneratorFactory(new FreeMarkerTemplateEngine("template"));

            htmlGeneratorFactory.getDataFromDomain(htmlInfo);

            htmlGeneratorFactory.generateSingleTemplates("helloworld.ftl", file.getAbsolutePath());
        } catch (EngineException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testFreemarkerMulti(){
        try{
            //destFile
            File file = new File("hellworld");

            if (!file.exists()){
                file.mkdir();
            }

            HtmlGeneratorFactory htmlGeneratorFactory = new HtmlGeneratorFactory(new FreeMarkerTemplateEngine("templateNew"));

            htmlGeneratorFactory.getDataFromDomain(htmlInfo);

            htmlGeneratorFactory.generateMultiTemplates("templateNew", file.getAbsolutePath());
        } catch (EngineException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testVelocity(){
        try{
            //templateDir
            File templateDir = new File(TestCodegen.class.getClassLoader().getResource("template").getFile());
            //destFile
            File file = new File("hellworld.html");

            HtmlGeneratorFactory htmlGeneratorFactory = new HtmlGeneratorFactory(new VelocityTemplateEngine("template"));

            htmlGeneratorFactory.getDataFromDomain(htmlInfo);

            htmlGeneratorFactory.generateSingleTemplates("helloworld.vm", file.getAbsolutePath());
        } catch (EngineException e){
            logger.error(e.getMessage());
        }
    }


    @Test
    public void testMybatis2(){
        try{
            //destFile
            File file = new File("hellworld.java");

            MybatisGeneratorFactory mybatisGeneratorFactory = new MybatisGeneratorFactory(new FreeMarkerTemplateEngine("templateNew"));

            mybatisGeneratorFactory.getDataFromDomain(new TableDomain(tables.get(1)));

            mybatisGeneratorFactory.generateSingleTemplates("/${module}-common", file.getAbsolutePath());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testMybatis3(){
        try{
            //destFile
            File file = new File("hellworld");

            if (!file.exists()){
                file.mkdir();
            }

            MybatisGeneratorFactory mybatisGeneratorFactory = new MybatisGeneratorFactory(new FreeMarkerTemplateEngine("UtilityRoom"));

            for (Table table : tables){
                mybatisGeneratorFactory.getDataFromDomain(new TableDomain(table));
                mybatisGeneratorFactory.generateMultiTemplates("", file.getAbsolutePath());
            }


        } catch (EngineException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testCompile(){
        String path = "template";
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        System.out.println(ReadProperties.getProperties().get("projectModule"));
        System.out.println(System.getProperty("user.dir"));
    }


    @Test
    public void petHtml(){
        //destFile
        File fileDir = new File("hellworld");

        if (!fileDir.exists()){
            fileDir.mkdir();
        }

        List<Pet> petList = new ArrayList<>();
        petList.add(new Pet("horse" , "00.00"));
        petList.add(new Pet("dog" , "9.99"));
        petList.add(new Pet("bear" , ".99"));

        File destFile = new File(fileDir.getAbsolutePath() + "\\pet.html");

        try{
            PetFactory petFactory = new PetFactory(new VelocityTemplateEngine("pet"));
            petFactory.getDataFromDomain(petList.stream().toArray(Pet[]::new));
            petFactory.generateSingleTemplates("pet.html", destFile.getAbsolutePath());

        } catch (EngineException e){
            logger.error(e.getMessage());
        }
    }


}
