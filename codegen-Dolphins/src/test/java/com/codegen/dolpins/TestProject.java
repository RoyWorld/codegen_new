package com.codegen.dolpins;


import com.codegen.jet.dolphins.TableFactory;
import com.codegen.jet.dolphins.domains.Table;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by RoyChan on 2017/12/11.
 */
public class TestProject {

    private final static Logger logger = LoggerFactory.getLogger(TestProject.class);


    @Test
    public void testCompile(){
        logger.info("success");
    }

    @Test
    public void testTableFactory(){
        List<Table> tableList = TableFactory.getInstance().getAllTables();

        for (Table table : tableList){
            logger.info(table.toString());
        }

        logger.info("read database success");
    }
}
