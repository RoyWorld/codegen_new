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

import ${mergePkgService}.I${className}Service;
import cn.thinkjoy.common.managerui.dao.IResourceGridDAO;
import cn.thinkjoy.common.managerui.service.IResourceGridService;
import cn.thinkjoy.common.domain.view.BizData4Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.thinkjoy.common.managerui.controller.AbstractAdminController;

@Controller
@RequestMapping(value="/admin/${module}")
public class ${className}Controller extends AbstractAdminController<I${className}Service>{


    @Autowired
    private I${className}Service ${classNameLower}Service;

    /**
     * 页面主请求
     * @param request
     * @param response
     * @return  返回菜单数据、表格描述元数据、当前主描述  如本页面为org
     */
    @RequestMapping(value="/${classNameAllLower}")
    public ModelAndView renderMainView(HttpServletRequest request,HttpServletResponse response){


        return doRenderMainView(request, response);
    }

    /**
     * 获取所有的组织信息
     * @return
     */
    @RequestMapping(value="/${classNameAllLower}s")
    @ResponseBody
    public BizData4Page findAll${className}s(HttpServletRequest request,HttpServletResponse response){
        return doPage(request, response);
    }

    @Override
    protected I${className}Service getMainService() {
        return ${classNameLower}Service;
    }

    @Override
    protected String getBizSys() {
        return "${module}";
    }

    @Override
    protected String getMainObjName() {
        return "${classNameAllLower}";
    }

    @Override
    protected String getViewTitle() {
        return "${table.resName}";
    }

    @Override
    protected String getParentTitle() {
        return "${table.parentResName}";
    }

    @Override
    public boolean getEnableDataPerm() {
        return true;
    }
}
