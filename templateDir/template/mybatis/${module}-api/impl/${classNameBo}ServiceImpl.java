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
import cn.thinkjoy.common.service.impl.AbstractPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("${className}ServiceImpl")
public class ${className}ServiceImpl extends MyAbstractPageService<I${className}DAO, ${className}> {
    @Autowired
    private I${className}DAO dao;

    @Override
    public I${className}DAO getDao() {
        return dao;
    }
}
