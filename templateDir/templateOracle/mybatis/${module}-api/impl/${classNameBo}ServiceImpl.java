<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign classNameAllLower = table.classNameBo?lower_case>
<#assign issubmeter = false>
<#if table.submeterTable==true>
<#assign issubmeter = true>
</#if>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}ServiceImpl.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */
<#include "/macro.include"/>
package ${basepackage}.service.impl;

import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.dao.IBaseDAO;
import ${basepackage}.dao.I${className}DAO;
import ${basepackage}.domain.${className};
import ${basepackage}.service.I${className}Service;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
<#if issubmeter>

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import org.apache.commons.lang3.StringUtils;

</#if>


@Service("${className}ServiceImpl")
public class ${className}ServiceImpl extends AbstractPageService<IBaseDAO<${className}>, ${className}> implements I${className}Service<IBaseDAO<${className}>,${className}>{
    @Autowired
    private I${className}DAO ${classNameLower}DAO;

    @Override
    public IBaseDAO<${className}> getDao() {
        return ${classNameLower}DAO;
    }


<#if issubmeter>

    @Override
    //@CachePut(key = "#entity.id") 暂时不加缓存，使用的主键自增，并且没有返回整个对象  TODO 可以使用编程式解决
    public final int add(String ${table.submeterTablePrefixName},${className} entity){
        return ${classNameLower}DAO.insert(${table.submeterTablePrefixName},entity);
    }

    @Override
    @CacheEvict(key = "#entity.id")
    public final int edit(String ${table.submeterTablePrefixName},${className} entity){

        return ${classNameLower}DAO.update(${table.submeterTablePrefixName},entity);
    }

    @Override
    @CacheEvict(key = "#id")
    public final int delete(String ${table.submeterTablePrefixName},Object id){
        return ${classNameLower}DAO.deleteById(${table.submeterTablePrefixName},id);
    }

    @Override
    //    @CacheEvict(key = "#id")
    public final int deleteByIds(String ${table.submeterTablePrefixName},List list){
        return ${classNameLower}DAO.deleteByIds(${table.submeterTablePrefixName},list);
    }

    @Override
    @Cacheable(key = "#id")
    public final ${className} view(String ${table.submeterTablePrefixName},Object id){
        return(${className})${classNameLower}DAO.fetch(${table.submeterTablePrefixName},id);
    }

    @Override
    //@CachePut(key = "#entity.id") 暂时不加缓存，使用的主键自增，并且没有返回整个对象  TODO 可以使用编程式解决
    public final int insert(String ${table.submeterTablePrefixName},${className} entity){
        return ${classNameLower}DAO.insert(${table.submeterTablePrefixName},entity);
    }

    @Override
    @CacheEvict(key = "#entity.id")
    public final int update(String ${table.submeterTablePrefixName},${className} entity){
        return ${classNameLower}DAO.update(${table.submeterTablePrefixName},entity);
    }

    @Override
    @CacheEvict(key = "#entity.id")
    public final int updateNull(String ${table.submeterTablePrefixName},${className} entity){

        return ${classNameLower}DAO.updateNull(${table.submeterTablePrefixName},entity);

    }

    @Override
    @CacheEvict(key = "#entity.id")
    public final int deleteById(String ${table.submeterTablePrefixName},Object id){

        return ${classNameLower}DAO.deleteById(${table.submeterTablePrefixName},id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public final int deleteByProperty(String ${table.submeterTablePrefixName},String property,Object value){
        return ${classNameLower}DAO.deleteByProperty(${table.submeterTablePrefixName},property,value);

    }

    @Override
    @Cacheable(key = "#id")
    public final ${className} fetch(String ${table.submeterTablePrefixName},Object id){
        return(${className})${classNameLower}DAO.fetch(${table.submeterTablePrefixName},id);
    }

    @Override
    @Cacheable()
    public final ${className} findOne(String ${table.submeterTablePrefixName},String property,Object value){
        return(${className})${classNameLower}DAO.findOne(${table.submeterTablePrefixName},property,value,null,null);
    }

    @Override
    @Cacheable()
    public final List findList(String ${table.submeterTablePrefixName},String property,Object value){
        return ${classNameLower}DAO.findList(${table.submeterTablePrefixName},property,value,null,null);
    }

    @Override
    @Cacheable()
    public final List findList(String ${table.submeterTablePrefixName},String property,Object value,String orderBy,SqlOrderEnum sqlOrderEnum){
        return ${classNameLower}DAO.findList(${table.submeterTablePrefixName},property,value,orderBy,sqlOrderEnum.getAction());
    }

    @Override
    public final List findAll(String ${table.submeterTablePrefixName}){
        return ${classNameLower}DAO.findAll(${table.submeterTablePrefixName},null,null);
    }

    @Override
    public final List findAll(String ${table.submeterTablePrefixName},String orderBy,SqlOrderEnum sqlOrderEnum){
        return ${classNameLower}DAO.findAll(${table.submeterTablePrefixName},orderBy,sqlOrderEnum.getAction());
    }

    @Override
    @Cacheable()
    public final List like(String ${table.submeterTablePrefixName},Map<String, Object>condition){
        return ${classNameLower}DAO.like(${table.submeterTablePrefixName},condition,null,null,null);
    }

    @Override
    @Cacheable()
    public final List like(String ${table.submeterTablePrefixName},Map<String, Object>condition,String orderBy,SqlOrderEnum sqlOrderEnum){
        return ${classNameLower}DAO.like(${table.submeterTablePrefixName},condition,orderBy,sqlOrderEnum.getAction(),null);
    }

    @Override
    public final Object selectMaxId(String ${table.submeterTablePrefixName}){
        return ${classNameLower}DAO.selectMaxId(${table.submeterTablePrefixName});
    }

    @Override
    @CacheEvict(key = "#id")
    public final void updateOrSave(String ${table.submeterTablePrefixName},${className} entity,Object id){
        if(id!=null&&!StringUtils.isEmpty(id.toString())){
            ${classNameLower}DAO.update(${table.submeterTablePrefixName},entity);
        }else{
            ${classNameLower}DAO.insert(${table.submeterTablePrefixName},entity);
        }
    }

    @Override
    public final ${className} selectOne(String ${table.submeterTablePrefixName},String mapperId,Object obj){
        return(${className})${classNameLower}DAO.selectOne(${table.submeterTablePrefixName},mapperId,obj);
    }

    @Override
    public final List selectList(String ${table.submeterTablePrefixName},String mapperId,Object obj){
        return ${classNameLower}DAO.selectList(${table.submeterTablePrefixName},mapperId,obj);
    }

    @Override
    public final int count(String ${table.submeterTablePrefixName},Map condition){
        return ${classNameLower}DAO.count(${table.submeterTablePrefixName},condition);
    }

    @Override
    public final ${className} queryOne(String ${table.submeterTablePrefixName},Map condition){
        return(${className})${classNameLower}DAO.queryOne(${table.submeterTablePrefixName},condition,null,null,null);
    }

    @Override
    public final ${className} queryOne(String ${table.submeterTablePrefixName},Map condition,String orderBy,SqlOrderEnum sqlOrderEnum){
        return(${className})${classNameLower}DAO.queryOne(${table.submeterTablePrefixName},condition,orderBy,sqlOrderEnum.getAction(),null);
    }

    @Override
    public final List queryList(String ${table.submeterTablePrefixName},Map condition,String orderBy,String sortBy){
        return ${classNameLower}DAO.queryList(${table.submeterTablePrefixName},condition,orderBy,sortBy,null);
    }

    @Override
    public final List queryPage(String ${table.submeterTablePrefixName},Map condition,int offset,int rows){
        return ${classNameLower}DAO.queryPage(${table.submeterTablePrefixName},condition,offset,rows,null,null,null);
    }

    public List<${className}>queryPage(String ${table.submeterTablePrefixName},Map<String, Object>condition,int offset,int rows,String orderBy,SqlOrderEnum sqlOrderEnum){
        return ${classNameLower}DAO.queryPage(${table.submeterTablePrefixName},condition,offset,rows,orderBy,sqlOrderEnum.getAction(),null);
    }

    @Override
    @CacheEvict(allEntries = true) //失效本对象所有缓存  尽量不要调用该方法
    public final int deleteByCondition(String ${table.submeterTablePrefixName},Map condition){
        return ${classNameLower}DAO.deleteByCondition(${table.submeterTablePrefixName},condition);
    }

    @Override
    @CacheEvict(allEntries = true)
    public final int updateMap(String ${table.submeterTablePrefixName},Map entityMap){
        return ${classNameLower}DAO.updateMap(${table.submeterTablePrefixName},entityMap);
    }

    @Override
    //@CachePut(key = "#entityMap.id") 暂时不加缓存，使用的主键自增，并且没有返回整个对象  ${className}ODO 可以使用编程式解决
    public final int insertMap(String ${table.submeterTablePrefixName},Map entityMap){
        return ${classNameLower}DAO.insertMap(${table.submeterTablePrefixName},entityMap);
    }

    @Override
    public final List listByPage(String ${table.submeterTablePrefixName},Map condition,int offset,int rows){
        return ${classNameLower}DAO.queryPage(${table.submeterTablePrefixName},condition,offset,rows,null,null,null);
    }

    @Override
    public final List listByPage(String ${table.submeterTablePrefixName},Map condition,int offset,int rows,String orderBy,SqlOrderEnum sqlOrderEnum){
        return ${classNameLower}DAO.queryPage(${table.submeterTablePrefixName},condition,offset,rows,orderBy,sqlOrderEnum.getAction(),null);
    }


    private final Map enhanceCreateBaseDomain(String ${table.submeterTablePrefixName},Map entityMap){
        entityMap.put("lastModDate",System.currentTimeMillis());

        return entityMap;
    }

    private final Map enhanceNewCreateBaseDomain(String ${table.submeterTablePrefixName},Map entityMap){
        entityMap.put("createDate",System.currentTimeMillis());

        return entityMap;
    }

    // ---------------- 后加接口，在这里默认做空实现，避免实现类报错，如果需要使用这些特性，需要重载 -----------------//
    @Override
    public List<${className}>queryPage(String ${table.submeterTablePrefixName},Map<String, Object>condition,int offset,int rows,String orderBy,SqlOrderEnum sqlOrderEnum,Map<String, Object>selectorpage){
        return ${classNameLower}DAO.queryPage(${table.submeterTablePrefixName},condition,offset,rows,orderBy,sqlOrderEnum.getAction(),selectorpage);
    }

    @Override
    public List<${className}>like(String ${table.submeterTablePrefixName},Map<String, Object>condition,String orderBy,SqlOrderEnum sqlOrderEnum,Map<String, Object>selector){
        return ${classNameLower}DAO.like(${table.submeterTablePrefixName},condition,orderBy,sqlOrderEnum.getAction(),selector);
    }

    @Override
    public List<${className}>queryList(String ${table.submeterTablePrefixName},Map<String, Object>condition,String orderBy,String sortBy,Map<String, Object>selector){
        return ${classNameLower}DAO.queryList(${table.submeterTablePrefixName},condition,orderBy,sortBy,selector);
    }

    @Override
    public ${className} queryOne(String ${table.submeterTablePrefixName},Map<String, Object>condition,String orderBy,SqlOrderEnum sqlOrderEnum,Map<String, Object>selector){
        return(${className})${classNameLower}DAO.queryOne(${table.submeterTablePrefixName},condition,orderBy,sqlOrderEnum.getAction(),selector);
    }


    /**
     * 通用的更新操作
     * @param updateMap 需要更新的值
     * @param conditionMap 需要被更新的条件
     */
    public int updateByCondition(String ${table.submeterTablePrefixName},Map<String, Object>updateMap,Map<String, Object>conditionMap){
        return ${classNameLower}DAO.updateByCondition(${table.submeterTablePrefixName},updateMap,conditionMap);
    }
</#if>
}
