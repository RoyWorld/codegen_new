<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}Controller.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.controller.api;

import ${mergePkgService}.I${className}Service;
import cn.thinkjoy.common.domain.SearchField;
import cn.thinkjoy.common.domain.StringWrapper;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.RtnCodeEnum;
import cn.thinkjoy.util.StringUtil;

import ${basepackage}.domain.${className};
import ${mergePkgService}.I${className}Service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/api")
public class Api${className}Controller{
    private static final Logger logger = LoggerFactory.getLogger(Api${className}Controller.class);

    @Autowired
    private I${className}Service ${classNameLower}Service;

   /**
     * 新增 ${table.name}
     * @param ${classNameLower}
     * @return  处理条数
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}", method = RequestMethod.POST)
    public Object add${className}(@RequestBody ${className} ${classNameLower}) {
        try {
            String msg = "";

            if(${classNameLower}==null) {
                msg = "添加${table.name}参数不正确";
<#list table.columns as column>
    <#if column != 'id' && !column.isNullable()>
        <#if column.possibleShortJavaType = 'String' >
            }else if(StringUtils.isBlank(${classNameLower}.get${column.columnName}())){
                msg = "${table.name}${column.remarks}不能为空";
            }else if(${classNameLower}.get${column.columnName}().length() > ${column.size}){
                msg = "${table.name}${column.remarks}长度不可超过${column.size}";
        <#else>
            <#if column.possibleShortJavaType != 'java.util.Date'>
            }else if(${classNameLower}.get${column.columnName}() != null){
                msg = "${table.name}${column.remarks}不能为空";
            </#if>
        </#if>
    </#if>
</#list>
            }

            if(StringUtils.isNotBlank(msg)){
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), msg);
            }

            int row=${classNameLower}Service.add(${classNameLower});
            if(row > 0) {
                return new StringWrapper( StringUtil.toString(${classNameLower}.getId()) );
            }else{
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "新增${table.name}失败");
            }
        } catch (BizException bize) {
            throw bize;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "新增${table.name}异常");
        }
    }


    /**
     * 删除 ${table.name}
     *
     * @param {${classNameLower}Id} ${table.name}ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}/{${classNameLower}Id}" ,method = RequestMethod.DELETE)
    public Object del${className}(@PathVariable String ${classNameLower}Id) {
        try {
            if(StringUtils.isBlank(${classNameLower}Id)){
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "删除${table.name}失败，参数不正确");
            }
            int row=${classNameLower}Service.deleteByProperty("id", ${classNameLower}Id);
            if(row > 0) {
                return new StringWrapper( "删除${table.name}成功" );
            }else{
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "删除${table.name}失败");
            }
        } catch (BizException bize) {
            throw bize;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "删除${table.name}异常");
        }
    }


    /**
     * 修改${table.name}数据
     * @param ${classNameLower} 提交上来的${table.name}JSON数据
     * @param ${classNameLower}Id  ${table.name}ID
     * @return  修改条数
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}/{${classNameLower}Id}", method = RequestMethod.PATCH)
    public StringWrapper edit${className}(@RequestBody ${className} ${classNameLower}, @PathVariable String ${classNameLower}Id) {
        try {
            String msg = "";
            if(${classNameLower}==null) {
                msg = "添加${table.name}参数不正确";
<#list table.columns as column>
    <#if column != 'id' && !column.isNullable()>
        <#if column.possibleShortJavaType = 'String' >
            }else if(StringUtils.isBlank(${classNameLower}.get${column.columnName}())){
                msg = "${table.name}${column.remarks}不能为空";
            }else if(${classNameLower}.get${column.columnName}().length() > ${column.size}){
                msg = "${table.name}${column.remarks}长度不可超过${column.size}";
        <#else>
        <#if column.possibleShortJavaType != 'java.util.Date'>
            }else if(${classNameLower}.get${column.columnName}() != null){
                msg = "${table.name}${column.remarks}不能为空";
        </#if>
        </#if>
    </#if>
</#list>

            }

            if(StringUtils.isNotBlank(msg)){
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), msg);
            }

            ${className} ${classNameLower}_old = (${className}) ${classNameLower}Service.findOne("id",${classNameLower}Id);
            if(${classNameLower}==null) {
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "修改失败，系统未找到相关数据");
            }

<#list table.columns as column>
    <#if column.possibleShortJavaType = 'String'>
            if(!StringUtils.isBlank(${classNameLower}.get${column.columnName}())){
                ${classNameLower}_old.set${column.columnName}(${classNameLower}.get${column.columnName}());
            }
    <#else>
            if(${classNameLower}.get${column.columnName}() != null){
                ${classNameLower}_old.set${column.columnName}(${classNameLower}.get${column.columnName}());
            }
    </#if>
</#list>


            int row=${classNameLower}Service.edit(${classNameLower}_old);
            if(row > 0) {
                return new StringWrapper("修改${table.name}成功");
            }else{
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "修改${table.name}失败");
            }
        } catch (BizException bize) {
            throw bize;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "修改${table.name}数据异常");
        }
    }


    /**
     * 获取单${table.name}实体
     *
     * @param ${classNameLower}Id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}/{${classNameLower}Id}", method = RequestMethod.GET)
    public ${className} get${className}(@PathVariable String ${classNameLower}Id) {
        try {
            if(StringUtils.isBlank(${classNameLower}Id)){
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "参数不正确！");
            }
            Map<String,Object> whereParams = new HashMap<String, Object>();
            whereParams.put("id", ${classNameLower}Id);
            ${className} ${classNameLower}= (${className}) ${classNameLower}Service.queryOne(whereParams);
            if(null == ${classNameLower}){
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "系统未找到${table.name}相关数据！");
            }
            return ${classNameLower};
        } catch (BizException bize) {
            throw bize;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "获取单${table.name}实体异常");
        }
    }


    /**
     * ${table.name}数据输出 带分页
     *
     * @param ${classNameLower} 过滤条件
     * @param page      第几页
     * @return 业务数据列表实体，最终转换为json数据输出
     * @throws ServletException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}list", method = RequestMethod.POST)
    public BizData4Page ${classNameLower}list(${className} ${classNameLower}, Integer page) {
        try {
            Map<String, Object> whereParams = new HashMap<String, Object>();
<#list table.columns as column>
    <#if column.possibleShortJavaType = 'String'>
            if(!StringUtils.isBlank(${classNameLower}.get${column.columnName}())){
                whereParams.put("${column}", new SearchField("${column}", "like", "%" + ${classNameLower}.get${column.columnName}() + "%"));
            }
    <#else>
            if(${classNameLower}.get${column.columnName}() != null){
                whereParams.put("${column}", new SearchField("${column}", "=", ${classNameLower}.get${column.columnName}()));
            }
    </#if>
</#list>


            //other props filter
            whereParams.put("groupOp", "and");

            BizData4Page bizData4Page = new BizData4Page();
            bizData4Page.setConditions(whereParams);
            if (page != null) {
                bizData4Page.setPage(page);
            }

            ${classNameLower}Service.queryPageByDataPerm(bizData4Page);
            return bizData4Page;
        } catch (BizException bize) {
            throw bize;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "查询${table.name}数据异常");
        }
    }
}
