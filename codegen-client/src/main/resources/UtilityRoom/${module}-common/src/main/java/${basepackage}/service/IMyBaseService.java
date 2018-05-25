package ${basepackage}.service;

/**
 * Created by BIG on 2016/3/11.
 */

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 14-9-5 上午10:27<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface IMyBaseService<D extends IMyBaseDAO, T extends MyBaseDomain> {


	/**
	 * 保存单一对象
	 *
	 * @param entity
	 */
	int insert(T entity);

	/**
	 * 更新对象,如果属性为空，则不会进行对应的属性值更新,如果有属性要更新为null
	 *
	 * @param entity 要更新的实体对象
	 */
	int update(T entity);

	/**
	 * 通用的更新操作
	 *
	 * @param updateMap    需要更新的值
	 * @param conditionMap 需要被更新的条件
	 */
	int updateByCondition(@Param("update") Map<String, Object> updateMap, Map<String, Object> conditionMap);

	/**
	 * map类型数据的新增
	 *
	 * @param insertMap 需要更新的值
	 */
	int insertByCondition(@Param("insert") Map<String, Object> insertMap);

	/**
	 * 根据条件集合删除对象
	 *
	 * @param condition
	 */
	int deleteByCondition(Map<String, Object> condition);

	/**
	 * 查询一个 增加排序支持
	 *
	 * @param conditionMap
	 * @param orderMap
	 * @return
	 */
	T queryOne(Map<String, Object> conditionMap, Map<String, Object> orderMap);

	/**
	 * 查询一个
	 *
	 * @param conditionMap
	 * @return
	 */
	T queryOne(Map<String, Object> conditionMap);
	/**
	 * 查询列表 增加排序支持
	 *
	 * @param conditionMap 查询条件
	 * @param orderMap     排序
	 * @return
	 */
	List<T> queryList(Map<String, Object> conditionMap, Map<String, Object> orderMap);

	/**
	 * 查询列表
	 *
	 * @param conditionMap 查询条件
	 * @return
	 */
	List<T> queryList(Map<String, Object> conditionMap);


	/**
	 * 根据条件集合进行分页查询
	 *
	 * @param conditionMap 查询条件
	 * @param orderMap     排序
	 * @param offset       偏移
	 * @param rows         查询条数
	 * @return 返回Pager对象
	 */
	List<T> queryPage(Map<String, Object> conditionMap, Map<String, Object> orderMap, int offset, int rows);


	/**
	 * 增加排序支持
	 *
	 * @param conditionMap 查询条件
	 * @param orderMap     排序
	 * @param offset       偏移
	 * @param rows         查询条数
	 * @return 返回Pager对象
	 */
	List<T> likePage(Map<String, Object> conditionMap, Map<String, Object> orderMap, int offset, int rows);


	/**
	 * 根据条件进行数量的查询
	 *
	 * @param condition
	 * @return 返回符合条件的泛型参数对应表中的数量
	 */
	int count(Map<String, Object> condition);

}

