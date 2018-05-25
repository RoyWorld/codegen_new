<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}Facade.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */
<#include "/macro.include"/>
package ${basepackage}.facade;

import ${basepackage}.domain.${className};
import ${basepackage}.service.IMyBaseDAO;
import ${basepackage}.service.IMyPersistenceProvider;

public interface I${className}Facade extends IMyPersistenceProvider<IMyBaseDAO, ${className}> {

}
