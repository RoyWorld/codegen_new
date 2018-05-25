<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}ServiceImpl.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */
<#include "/macro.include"/>
package ${basepackage}.service.impl;

import ${basepackage}.dao.I${className}DAO;
import ${basepackage}.domain.${className};
import ${basepackage}.facade.I${className}Facade;
import ${basepackage}.service.IMyBaseDAO;
import ${basepackage}.service.MyAbstractPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("I${className}ServiceImpl")
public class I${className}ServiceImpl extends MyAbstractPageService<IMyBaseDAO, ${className}> implements I${className}Facade{
    @Autowired
    private I${className}DAO dao;

    @Override
    public I${className}DAO getDao() {
        return dao;
    }
}
