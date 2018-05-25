package ${basepackage}.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by BIG on 2016/3/11.
 */
public interface IMyBaseDAO<T extends MyBaseDomain> {


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
    int updateByCondition(@Param("update") Map<String, Object> updateMap, @Param("condition") Map<String, Object> conditionMap);


    /**
     * map类型数据的新增
     *
     * @param insertMap    需要更新的值
     */
    int insertByCondition(@Param("insert") Map<String, Object> insertMap);

    /**
     * 根据条件集合删除对象
     *
     * @param condition
     */
    int deleteByCondition(@Param("condition") Map<String, Object> condition);

    /**
     * 查询一个 增加排序支持
     *
     * @param conditionMap
     * @param orderMap
     * @return
     */
    T queryOne(@Param("condition") Map<String, Object> conditionMap, @Param("order") Map<String, Object> orderMap);


//	/**
//	 * 查询一个
//	 *
//	 * @param conditionMap
//	 * @return
//	 */
//	T queryOne(@Param("condition") Map<String, Object> conditionMap);

    /**
     * 查询列表 增加排序支持
     *
     * @param conditionMap 查询条件
     * @param orderMap     排序
     * @return
     */
    List<T> queryList(@Param("condition") Map<String, Object> conditionMap, @Param("order") Map<String, Object> orderMap);

    /**
     * 查询列表
     *
     * @param conditionMap 查询条件
     * @return
     */
    List<T> queryList(@Param("condition") Map<String, Object> conditionMap);


    /**
     * 根据条件集合进行分页查询
     *
     * @param conditionMap 查询条件
     * @param orderMap     排序
     * @param offset       偏移
     * @param rows         查询条数
     * @return 返回Pager对象
     */
    List<T> queryPage(@Param("condition") Map<String, Object> conditionMap, @Param("order") Map<String, Object> orderMap, @Param("offset") int offset, @Param("rows") int rows);


    /**
     * 增加排序支持
     *
     * @param conditionMap 查询条件
     * @param orderMap     排序
     * @param offset       偏移
     * @param rows         查询条数
     * @return 返回Pager对象
     */
    List<T> likePage(@Param("condition") Map<String, Object> conditionMap, @Param("order") Map<String, Object> orderMap, @Param("offset") int offset, @Param("rows") int rows);


    /**
     * 根据条件进行数量的查询
     *
     * @param condition
     * @return 返回符合条件的泛型参数对应表中的数量
     */
    int count(@Param("condition") Map<String, Object> condition);

    /**
     * 根据原生sql查询获取返回的值[模型动态化]
     *
     * @return
     */
//	List<Map> queryBySql(@Param("executeSql") String executeSql);

//	Long queryBySqlCount(@Param("executeSqlCount") String executeSql);
}
