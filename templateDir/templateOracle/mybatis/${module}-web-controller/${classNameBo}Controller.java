<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}Controller.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.web.controller;

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
public class ${className}Controller{
    private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

    @Autowired
    private I${className}Service ${classNameLower}Service;

    /**
     * 新增 ${table.name}
     *
     * @param
     * @return 处理条数
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}", method = RequestMethod.POST)
    public StringWrapper add${className}(@RequestBody ${className} ${classNameLower}) {
        try {
            int msg = ${classNameLower}Service.add(${classNameLower});
            return new StringWrapper(String.valueOf(msg));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "新增${table.name}异常");
        }
    }

    /**
     * 删除学校数据
     *
     * @param schoolId 学校ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}/{${classNameLower}Id}", method = RequestMethod.DELETE)
    public StringWrapper del${className}(@PathVariable String ${classNameLower}Id) {
        try {
            int msg = ${classNameLower}Service.deleteByProperty("id", ${classNameLower}Id);
                return new StringWrapper(String.valueOf(msg));
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "删除${table.name}异常");
        }
    }


    /**
     * 修改学校数据
     *
     * @param jsonObject 提交上来的学校JSON数据
     * @param schoolid   学校ID
     * @return 修改条数
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}/{${classNameLower}Id}", method = RequestMethod.PATCH)
    public StringWrapper edit${className}(@RequestBody com.alibaba.fastjson.JSONObject jsonObject, @PathVariable String ${classNameLower}Id) {
        try {
            ${className} ${classNameLower} = (${className}) ${classNameLower}Service.findOne("id", ${classNameLower}Id);
            //other props setting
            int msg = ${classNameLower}Service.edit(${classNameLower});
            return new StringWrapper(String.valueOf(msg));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "修改${table.name}数据异常");
        }
    }


    /**
     * 获取单学校实体
     *
     * @param schoolid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/${classNameAllLower}/{${classNameLower}Id}", method = RequestMethod.GET)
    public ${className} get${className}(@PathVariable String ${classNameLower}Id) {
        try {
            Map<String, Object> whereParams = new HashMap<String, Object>();
            whereParams.put("id", ${classNameLower}Id);
            ${className} ${classNameLower} = (${className}) ${classNameLower}Service.queryOne(whereParams);
            return ${classNameLower};
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "获取单${table.name}实体异常");
        }
    }


    /**
     * 学校数据输出 带分页
     *
     * @param casSchool 过滤条件
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
            if (!StringUtils.isEmpty(${classNameLower}.getName())) {
                whereParams.put("name", new SearchField("name", "like", "%" + ${classNameLower}.getName() + "%"));
            }
            //other props filter
            whereParams.put("groupOp", "and");

            BizData4Page bizData4Page = new BizData4Page();
            bizData4Page.setConditions(whereParams);
            if (page != null) {
                bizData4Page.setPage(page);
            }

            ${classNameLower}Service.queryPageByDataPerm(bizData4Page);
            return bizData4Page;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizException(RtnCodeEnum.UNKNOW.getValue(), "查询${table.name}数据异常");
        }
    }
}
