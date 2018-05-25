<#assign className = table.classNameBo>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}DAO.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */
<#assign classNameLower = className?uncap_first>
<#assign issubmeter = false>
<#if table.submeterTable==true>
<#assign issubmeter = true>
</#if>
package ${basepackage}.${persistence};

import cn.thinkjoy.common.dao.IBaseDAO;
import ${basepackage}.domain.${className};
<#if issubmeter>
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
</#if>

public interface I${className}DAO extends IBaseDAO<${className}>{

<#if issubmeter>
	/**
	 * 保存单一对象，如果要保存多个对象集合，请参看{@link #insertList(java.util.List)}
	 *
	 * @param entity
	 */
	public int insert(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},${className} entity);

	/**
	 * 更新对象,如果属性为空，则不会进行对应的属性值更新,如果有属性要更新为null，请参看{@link #updateNull(${className})}
	 *
	 * @param entity
	 *            要更新的实体对象
	 */
	public int update(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},${className} entity);

	public int updateMap(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("map") Map<String, Object> entityMap);

	/**
	 * 通用的更新操作
	 * @param updateMap 需要更新的值
	 * @param conditionMap 需要被更新的条件
	 */
	public int updateByCondition(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("update") Map<String, Object> updateMap, @Param("condition") Map<String, Object> conditionMap);

	/**
	 * map类型数据的新增
	 * @param entityMap
	 */
	public int insertMap(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("map") Map<String, Object> entityMap);

	/**
	 * 更新对象,如果属性为空，会进行对应的属性值更新,如果有属性不想更新为null，请参看{@link #update(T)}
	 *
	 * @param entity
	 */
	public int updateNull(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},${className} entity);

	/**
	 * 根据id删除对象
	 *
	 * @param id
	 */
	public int deleteById(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},Object id);

	/**
	 * 根据list(ids)删除对象
	 *
	 * @param list
	 */
	public int deleteByIds(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},List list);

	/**
	 * 根据条件集合删除对象
	 *
	 * @param condition
	 */
	public int deleteByCondition(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},Map<String, Object> condition);

	/**
	 * 根据属性和属性值删除对象
	 *
	 * @param property
	 * @param value
	 */
	public int deleteByProperty(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("property") String property,@Param("value") Object value);

	/**
	 * 根据id进行对象查询
	 *
	 * @param id
	 * @return
	 */
	public ${className} fetch(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},Object id);

	/**
	 * 根据任意属性和属性值进行对象查询，如果返回多个对象，将抛出异常
	 *
	 * @param property
	 *            进行对象匹配的属性
	 * @param value
	 *            进行对象匹配的属性值
	 * @return 返回泛型参数类型对象，如何取到泛型类型参数，请参看{@link #getEntityClass()}
	 */
	//    public T findOne(@Param("property") String property, @Param("value") Object value);

	/**
	 * 增加排序支持
	 * @param property
	 * @param value
	 * @param orderBy
	 * @param sortBy
	 * @return
	 */
	public ${className} findOne(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("property") String property, @Param("value") Object value, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

	/**
	 * 根据任意（单一）属性和属性值进行集合查询
	 *
	 * @param property
	 *            进行对象匹配的属性
	 * @param value
	 *            进行对象匹配的属性值
	 * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
	 */
	//    public List<T> findList(@Param("property") String property, @Param("value") Object value);

	/**
	 * 增加排序支持
	 * @param property
	 * @param value
	 * @param orderBy
	 * @param sortBy
	 * @return
	 */
	public List<${className}> findList(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("property") String property, @Param("value") Object value, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

	/**
	 * 根据传入的泛型参数类型查询该类型对应表中的所有数据，返回一个集合对象
	 *
	 * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
	 */
	//    public List<T> findAll();

	/**
	 * 增加排序支持
	 * @param orderBy
	 * @param sortBy
	 * @return
	 */
	public List<${className}> findAll(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("orderBy") String orderBy, @Param("sortBy") String sortBy);
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
	//    public List<T> queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows);

	/**
	 * 增加排序支持
	 * @param condition
	 * @param offset
	 * @param rows
	 * @param orderBy
	 * @param sortBy
	 * @return
	 */
	public List<${className}> queryPage(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
	@Param("orderBy") String orderBy, @Param("sortBy") String sortBy);


	public List<${className}> queryPage(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
	@Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector")Map<String, Object> selector);

	/**
	 * 根据任意属性和属性值进行对象模糊查询
	 *
	 * @param property
	 *            进行对象匹配的属性
	 * @param value
	 *            进行对象匹配的模糊属性值
	 * @return
	 */
	//    public List<T> like(@Param("property") String property, @Param("value") Object value);

	/**
	 * 增加排序支持
	 * @param property
	 * @param value
	 * @param orderBy
	 * @param sortBy
	 * @return
	 */
	public List<${className}> like(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);
	public List<${className}> like(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector")Map<String, Object> selector);

	/**
	 * 根据条件集合进行指定类型对象集合查询
	 *
	 * @param condition
	 *            进行查询的条件集合
	 * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
	 */
	public List<${className}> queryList(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

	public List<${className}> queryList(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector")Map<String, Object> selector);

	/**
	 * 根据条件集合进行指定类型单一对象查询
	 *
	 * @param condition
	 *            进行查询的条件集合
	 * @return 返回泛型参数类型的对象，如何取到泛型类型参数，请参看{@link #getEntityClass()}，
	 */
	//    public T queryOne(Map<String, Object> condition);
	public ${className} queryOne(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

	/**
	 *
	 * @param condition
	 * @param orderBy
	 * @param sortBy
	 * @param selector  查询器，不进行不必要字段的查询，这样 生成的 model  在http传递给客户端时依赖fastjson的特性 就不会传递出去，减少带宽依赖
	 * @return
	 */
	public ${className} queryOne(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector")Map<String, Object> selector);

	/**
	 * 根据条件进行数量的查询
	 *
	 * @param condition
	 * @return 返回符合条件的泛型参数对应表中的数量
	 */
	public int count(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},Map<String, Object> condition);

	/**
	 * 增加排序支持  和 queryPage匹配
	 * @param condition
	 * @param orderBy
	 * @param sortBy
	 * @return
	 */
	//    public int count(Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

	/**
	 * 该方法只有在主键为long时才有用，如果主键为其他数据类型进行使用，则会抛出异常
	 *
	 * @name selectMaxId
	 * @active 查询实体对应表最大Id
	 * @time 上午10:04:05
	 * @exception/throws 如果主键类型不为long，会抛出类型转换异常
	 * @return 返回泛型参数对应表的主键最大值
	 */
	public Object selectMaxId(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName});

	/**
	 * 更新或保存，涉及到Mabatis使用的bean只是一个简单的值对象，不能进行id的注解，不知道哪个是主键，所以，必须同时指定t的主键值
	 *
	 * @param t
	 *            要更新或保存的对象
	 * @param id
	 *            要更新或保存的对象的id
	 * @return 返回更新或保存的对象。
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public ${className} updateOrSave(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") ${className} t, @Param("condition") Long id);

	/**
	 * 根据泛型类型，执行最原始的sql
	 *
	 * @param mapperId
	 * @param obj
	 * @return 返回泛型类型对象，如果返回多个结果集会抛出异常，如果要返回多个结果集，请参看
	 *         {@link #selectList(String, Object)}
	 */
	public ${className} selectOne(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") String mapperId, @Param("condition") Object obj);

	/**
	 * 根据泛型类型，执行最原始的sql
	 *
	 * @param mapperId
	 * @param obj
	 * @return 返回泛型类型对象集合，如果要返回单个结果对象，请参看{@link #selectOne(String, Object)}
	 */
	public List<${className}> selectList(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("condition") String mapperId, @Param("condition") Object obj);

	/**
	 * 根据原生sql查询获取返回的值[模型动态化]
	 * @return
	 */
	public List<Map> queryBySql(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("executeSql") String executeSql);

	public Long queryBySqlCount(@Param("${table.submeterTablePrefixName}")String ${table.submeterTablePrefixName},@Param("executeSqlCount") String executeSql);
</#if>

}
