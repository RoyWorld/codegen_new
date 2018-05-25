package ${basepackage}.service;

/**
 * Created by BIG on 2016/3/11.
 */

import ${basepackage}.domain.BizData4Page;
import ${basepackage}.service.SqlOrderEnum;

import java.util.Map;


/**
 * 进行分页数据查询的service
 * <p/>
 * 创建时间: 14-9-3 下午9:57<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface IMyPageService<D extends IMyBaseDAO, T extends MyBaseDomain> {
    /**
     * 数据权限后的分页数据获取
     *
     * @param resUri
     * @param conditions
     * @param offset
     * @param rows
     * @return
     */
    BizData4Page queryPageByDataPerm(String resUri, Map<String, Object> conditions, int curPage, int offset, int rows);

    /**
     * 条件查询包含各种查询
     *
     * @return
     */
//	void queryPageByDataPerm(BizData4Page bizData4Page);

    /***
     * 条件查询包含各种查询
     * baseDAO 主要为扩展exdao
     *
     * @param baseDAO
     * @param bizData4Page
     */
    void queryPageByDataPerm(IMyBaseDAO baseDAO, BizData4Page bizData4Page);


    /**
     * 条件查询包含各种查询
     *
     * @return
     */
    void queryPageByDataPerm(BizData4Page bizData4Page, String orderBy, SqlOrderEnum sqlOrderEnum);


    /**
     * 具有排序的分页
     *
     * @param resUri
     * @param conditions
     * @param curPage
     * @param offset
     * @param rows
     * @param orderBy
     * @param sqlOrderEnum
     * @return
     */
    BizData4Page queryPageByDataPerm(String resUri, Map<String, Object> conditions, int curPage, int offset, int rows, String orderBy, SqlOrderEnum sqlOrderEnum);


    /**
     * 条件查询包含各种查询
     *
     * @return
     */
    void queryPageByDataPerm(BizData4Page bizData4Page, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);

    void queryPageByDataPerm(IMyBaseDAO dao, BizData4Page bizData4Page, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);

    /**
     * 具有排序的分页
     *
     * @param dao
     * @param conditions
     * @param curPage
     * @param offset
     * @param rows
     * @param orderBy
     * @param sqlOrderEnum
     * @return
     */
    BizData4Page queryPageByDataPerm(IMyBaseDAO dao, Map<String, Object> conditions, int curPage, int offset, int rows, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);


    BizData4Page queryPageByDataPerm(BizData4Page bizData4Page, Map<String, Object> orderMap);


    BizData4Page queryPageByDataPerm(BizData4Page bizData4Page);
}

