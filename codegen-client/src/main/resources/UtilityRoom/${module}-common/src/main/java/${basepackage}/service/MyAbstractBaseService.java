package ${basepackage}.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by BIG on 2016/3/11.
 */

/**
 * 服务抽象类
 * <p/>
 * 创建时间: 14-9-22 下午5:51<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public abstract class MyAbstractBaseService<D extends IMyBaseDAO, T extends MyBaseDomain> implements IMyBaseService<D, T>, IMyDaoAware<D, T> {


    /**
     * 保存单一对象
     *
     * @param entity
     */
    public int insert(T entity) {
        return getDao().insert(entity);
    }

    /**
     * 更新对象,如果属性为空，则不会进行对应的属性值更新,如果有属性要更新为null
     *
     * @param entity 要更新的实体对象
     */
    public int update(T entity) {
        return getDao().update(entity);
    }

    /**
     * 通用的更新操作
     *
     * @param updateMap    需要更新的值
     * @param conditionMap 需要被更新的条件
     */
    public int updateByCondition(Map<String, Object> updateMap, Map<String, Object> conditionMap) {
        return getDao().updateByCondition(updateMap, conditionMap);
    }

    /**
     * map类型数据的新增
     *
     * @param insertMap 需要更新的值
     */
    public int insertByCondition(@Param("insert") Map<String, Object> insertMap) {
        return getDao().insertByCondition(insertMap);
    }

    /**
     * 根据条件集合删除对象
     *
     * @param condition
     */
    public int deleteByCondition(Map<String, Object> condition) {
        return getDao().deleteByCondition(condition);
    }

    /**
     * 增加排序支持
     *
     * @param conditionMap
     * @param orderMap
     * @return
     */
    public T queryOne(Map<String, Object> conditionMap, Map<String, Object> orderMap) {
        return (T) getDao().queryOne(conditionMap, orderMap);
    }

    /**
     * 增加排序支持
     *
     * @param conditionMap
     * @return
     */
    public T queryOne(Map<String, Object> conditionMap) {
        return (T) getDao().queryOne(conditionMap, null);
    }

    /**
     * 增加排序支持
     *
     * @param conditionMap 查询条件
     * @param orderMap     排序
     * @return
     */
    public List<T> queryList(Map<String, Object> conditionMap, Map<String, Object> orderMap) {
        return (List<T>) getDao().queryList(conditionMap, orderMap);
    }

    /**
     * 增加排序支持
     *
     * @param conditionMap 查询条件
     * @return
     */
    public List<T> queryList(Map<String, Object> conditionMap) {
        return (List<T>) getDao().queryList(conditionMap, null);
    }


    /**
     * 根据条件集合进行分页查询
     *
     * @param conditionMap 查询条件
     * @param orderMap     排序
     * @param offset       偏移
     * @param rows         查询条数
     * @return 返回Pager对象
     */
    public List<T> queryPage(Map<String, Object> conditionMap, Map<String, Object> orderMap, @Param("offset") int offset, @Param("rows") int rows) {
        return (List<T>) getDao().queryPage(conditionMap, orderMap, offset, rows);
    }


    /**
     * 增加排序支持
     *
     * @param conditionMap 查询条件
     * @param orderMap     排序
     * @param offset       偏移
     * @param rows         查询条数
     * @return 返回Pager对象
     */
    public List<T> likePage(Map<String, Object> conditionMap, Map<String, Object> orderMap, @Param("offset") int offset, @Param("rows") int rows) {
        return (List<T>) getDao().likePage(conditionMap, orderMap, offset, rows);
    }


    /**
     * 根据条件进行数量的查询
     *
     * @param condition
     * @return 返回符合条件的泛型参数对应表中的数量
     */
    public int count(Map<String, Object> condition) {
        return getDao().count(condition);
    }

    /**
     * 根据原生sql查询获取返回的值[模型动态化]
     *
     * @return
     */
//	public List<Map> queryBySql(@Param("executeSql") String executeSql) {
//	}
//
//	public Long queryBySqlCount(@Param("executeSqlCount") String executeSql) {
//	}
}

