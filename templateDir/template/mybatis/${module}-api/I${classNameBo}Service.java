<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#include "/macro.include"/>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}Service.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.service;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import ${basepackage}.dao.I${className}DAO;
import ${basepackage}.domain.${className};

public interface I${className}Service extends IMyBaseService<I${className}DAO, ${className}>,IMyPageService<I${className}DAO, ${className}>{

}
