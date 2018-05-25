
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${module}ServiceMaps.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.common;

import cn.thinkjoy.common.service.IBaseService;
<#list newtables as table>
<#assign className = table.classNameBo>
import ${basepackage}.service.I${className}Service;
</#list>


import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

import cn.thinkjoy.common.managerui.controller.helpers.BaseServiceMaps;

/**
 * Created by shurrik on 14-9-24.
 */
@Service("${module}ServiceMaps")
public class ServiceMaps extends BaseServiceMaps{

    <#list newtables as table>
    <#assign className = table.classNameBo>
    <#assign classNameLower = className?uncap_first>

    @Autowired
    private I${className}Service ${classNameLower}Service;
    </#list>

    @PostConstruct
    public void init(){
        super.init();
        <#list newtables as table>
            <#assign className = table.classNameBo>
            <#assign classNameLower = className?uncap_first>
        serviceMap.put("${classNameLower}",${classNameLower}Service);
        </#list>
    }

}
