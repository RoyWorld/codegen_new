<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}Controller.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.${persistence};

import cn.thinkjoy.common.managerui.controller.helpers.BasePersistenceProviderMaps;
import cn.thinkjoy.common.managerui.controller.helpers.BaseServiceMaps;
import cn.thinkjoy.common.managerui.controller.AbstractCommonController;

import ${basepackage}.common.PersistenceProviderMaps;
import ${basepackage}.common.ServiceMaps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/admin/${module}")
public class CommonController extends AbstractCommonController{
    @Autowired
    private ServiceMaps serviceMaps;
    @Autowired
    private PersistenceProviderMaps persistenceProviderMaps;

    @Override
    protected BaseServiceMaps getServiceMaps() {
        return serviceMaps;
    }

    @Override
    protected BasePersistenceProviderMaps getPersistenceProviderMaps() {
        return persistenceProviderMaps;
    }
}
