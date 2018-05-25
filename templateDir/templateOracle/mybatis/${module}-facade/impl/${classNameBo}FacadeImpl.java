<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}FacadeImpl.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */
<#include "/macro.include"/>
package ${basepackage}.facade.impl;

import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.impl.AbstractPersistenceProvider;
import ${basepackage}.facade.I${className}Facade;
import ${basepackage}.service.I${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("${className}FacadeImpl")
public class ${className}FacadeImpl extends AbstractPersistenceProvider implements I${className}Facade {
    @Autowired
    private I${className}Service ${classNameLower}Service;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        ${classNameLower}Service.add();
//    }

    @Override
    public IBaseService getService() {
        return ${classNameLower}Service;
    }
}
