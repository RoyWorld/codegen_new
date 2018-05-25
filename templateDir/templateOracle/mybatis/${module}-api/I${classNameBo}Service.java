<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
<#assign issubmeter = false>
<#if table.submeterTable==true>
<#assign issubmeter = true>
</#if>
<#include "/macro.include"/>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}Service.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
<#if issubmeter>
import cn.thinkjoy.common.utils.SqlOrderEnum;

import java.util.List;
import java.util.Map;
</#if>

public interface I${className}Service<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{


<#if issubmeter>
    /**
     * 新增一条数据
     *
     * @param entity
     */
    public int add(String ${table.submeterTablePrefixName},T entity);

    /**
     * 修改一条数据
     *
     * @param entity
     *            要更新的实体对象
     */
    public int edit(String ${table.submeterTablePrefixName},T entity);

    /**
     * 删除一条数据
     *
     * @param id
     */
    public int delete(String ${table.submeterTablePrefixName},Object id);

    /**
     * 查看一条确定的数据
     *
     * @param id
     * @return
     */
    public T view(String ${table.submeterTablePrefixName},Object id);

    /**
     * 根据条件集合进行分页查询
     *
     * @param condition
     *            查询条件
     * @param offset
     *            偏移
     * @param rows
     *            查询条数
     * @return 返回Pager对象
     */
    public List<T> listByPage(String ${table.submeterTablePrefixName},Map<String, Object> condition, int offset, int rows);
    public List<T> listByPage(String ${table.submeterTablePrefixName},Map<String, Object> condition, int offset, int rows, String orderBy, SqlOrderEnum sqlOrderEnum);

    /**
     * 保存单一对象，如果要保存多个对象集合，请参看{(java.util.List)}
     *
     * @param entity
     */
    public int insert(String ${table.submeterTablePrefixName},T entity);

    /**
     * 更新对象,如果属性为空，则不会进行对应的属性值更新,如果有属性要更新为null}
     *
     * @param entity
     *            要更新的实体对象
     */
    public int update(String ${table.submeterTablePrefixName},T entity);


    public int insertMap(String ${table.submeterTablePrefixName},Map<String, Object> entityMap);

    public int updateMap(String ${table.submeterTablePrefixName},Map<String, Object> entityMap);

    /**
     * 更新对象,如果属性为空，会进行对应的属性值更新,如果有属性不想更新为null}
     *
     * @param entity
     */
    public int updateNull(String ${table.submeterTablePrefixName},T entity);

    /**
     * 根据id删除对象
     *
     * @param id
     */
    public int deleteById(String ${table.submeterTablePrefixName},Object id);

    /**
     * 根据list(ids)删除对象
     *
     * @param list
     */
    public int deleteByIds(String ${table.submeterTablePrefixName},List list);

    /**
     * 根据条件集合删除对象
     *
     * @param condition
     */
    public int deleteByCondition(String ${table.submeterTablePrefixName},Map<String, Object> condition);

    /**
     * 根据属性和属性值删除对象
     *
     * @param property
     * @param value
     */
    public int deleteByProperty(String ${table.submeterTablePrefixName},String property, Object value);

    /**
     * 根据id进行对象查询
     *
     * @param id
     * @return
     */
    public T fetch(String ${table.submeterTablePrefixName},Object id);

    /**
     * 根据任意属性和属性值进行对象查询，如果返回多个对象，将抛出异常
     *
     * @param property
     *            进行对象匹配的属性
     * @param value
     *            进行对象匹配的属性值
     * @return 返回泛型参数类型对象，如何取到泛型类型参数，请参看{@link #getEntityClass()}
     */
    public T findOne(String ${table.submeterTablePrefixName},String property, Object value);

    /**
     * 根据任意（单一）属性和属性值进行集合查询
     *
     * @param property
     *            进行对象匹配的属性
     * @param value
     *            进行对象匹配的属性值
     * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
     */
    public List<T> findList(String ${table.submeterTablePrefixName},String property, Object value);
    public List<T> findList(String ${table.submeterTablePrefixName},String property, Object value, String orderBy, SqlOrderEnum sqlOrderEnum);

    /**
     * 根据传入的泛型参数类型查询该类型对应表中的所有数据，返回一个集合对象
     *
     * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
     */
    public List<T> findAll(String ${table.submeterTablePrefixName});
    public List<T> findAll(String ${table.submeterTablePrefixName},String orderBy, SqlOrderEnum sqlOrderEnum);

    /**
     * 根据条件集合进行分页查询
     *
     * @param condition
     *            查询条件
     * @param offset
     *            偏移
     * @param rows
     *            查询条数
     * @return 返回Pager对象
     */
    public List<T> queryPage(String ${table.submeterTablePrefixName},Map<String, Object> condition, int offset, int rows);
    public List<T> queryPage(String ${table.submeterTablePrefixName},Map<String, Object> condition, int offset, int rows, String orderBy, SqlOrderEnum sqlOrderEnum);
    public List<T> queryPage(String ${table.submeterTablePrefixName},Map<String, Object> condition, int offset, int rows, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selectorpage);

    /**
     * 根据任意属性和属性值进行对象模糊查询
     *
     *            进行对象匹配的模糊属性值
     * @return
     */
    public List<T> like(String ${table.submeterTablePrefixName},Map<String, Object> condition);
    public List<T> like(String ${table.submeterTablePrefixName},Map<String, Object> condition, String orderBy, SqlOrderEnum sqlOrderEnum);
    public List<T> like(String ${table.submeterTablePrefixName},Map<String, Object> condition, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);

    /**
     * 根据条件集合进行指定类型对象集合查询
     *
     * @param condition
     *            进行查询的条件集合
     * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
     */
    public List<T> queryList(String ${table.submeterTablePrefixName},Map<String, Object> condition, String orderBy, String sortBy);
    public List<T> queryList(String ${table.submeterTablePrefixName},Map<String, Object> condition, String orderBy, String sortBy, Map<String, Object> selector);

    /**
     * 根据条件集合进行指定类型单一对象查询
     *
     * @param condition
     *            进行查询的条件集合
     * @return 返回泛型参数类型的对象，如何取到泛型类型参数，请参看{@link #getEntityClass()}，
     */
    public T queryOne(String ${table.submeterTablePrefixName},Map<String, Object> condition);
    public T queryOne(String ${table.submeterTablePrefixName},Map<String, Object> condition, String orderBy, SqlOrderEnum sqlOrderEnum);
    public T queryOne(String ${table.submeterTablePrefixName},Map<String, Object> condition, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);

    /**
     * 根据条件进行数量的查询
     *
     * @param condition
     * @return 返回符合条件的泛型参数对应表中的数量
     */
    public int count(String ${table.submeterTablePrefixName},Map<String, Object> condition);
    //    public int count(Map<String, Object> condition, String orderBy, SqlOrderEnum sqlOrderEnum);

    /**
     * 该方法只有在主键为long时才有用，如果主键为其他数据类型进行使用，则会抛出异常
     *
     * @name selectMaxId
     * @active 查询实体对应表最大Id
     * @time 上午10:04:05
     * @exception/throws 如果主键类型不为数字，会抛出类型转换异常
     * @return 返回泛型参数对应表的主键最大值
     */
    public Object selectMaxId(String ${table.submeterTablePrefixName});

    /**
     * 更新或保存，涉及到Mabatis使用的bean只是一个简单的值对象，不能进行id的注解，不知道哪个是主键，所以，必须同时指定t的主键值
     *
     * @param t
     *            要更新或保存的对象
     * @param id
     *            要更新或保存的对象的id
     * @return 返回更新或保存的对象。
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    public void updateOrSave(String ${table.submeterTablePrefixName},T t, Object id);

    /**
     * 根据泛型类型，执行最原始的sql
     *
     * @param mapperId
     * @param obj
     * @return 返回泛型类型对象，如果返回多个结果集会抛出异常，如果要返回多个结果集，请参看
     *         {@link #selectList(String, Object)}
     */
    public T selectOne(String ${table.submeterTablePrefixName},String mapperId, Object obj);

    /**
     * 根据泛型类型，执行最原始的sql
     *
     * @param mapperId
     * @param obj
     * @return 返回泛型类型对象集合，如果要返回单个结果对象，请参看{@link #selectOne(String, Object)}
     */
    public List<T> selectList(String ${table.submeterTablePrefixName},String mapperId, Object obj);


    /**
     * 通用的更新操作
     * @param updateMap 需要更新的值
     * @param conditionMap 需要被更新的条件
     */
    public int updateByCondition(String ${table.submeterTablePrefixName},Map<String, Object> updateMap, Map<String, Object> conditionMap);
</#if>
}
