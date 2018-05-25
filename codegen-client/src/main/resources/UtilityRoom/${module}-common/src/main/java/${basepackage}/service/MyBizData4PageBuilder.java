package ${basepackage}.service;

/**
 * Created by BIG on 2016/3/11.
 */
import ${basepackage}.domain.BizData4Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * BizData4Page 构建者
 * <p/>
 * 创建时间: 15/7/31 下午2:57<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class MyBizData4PageBuilder {

	/**
	 * 生成 BizData4Page 实例
	 *
	 * @param dao
	 * @param conditions
	 * @param curPage
	 * @param offset
	 * @param rows
	 * @return
	 */
	public static BizData4Page createBizData4Page(IMyBaseDAO dao, Map<String, Object> conditions, int curPage, int offset, int rows) {
		return createBizData4Page(dao, conditions, curPage, offset, rows, null);
	}

	/**
	 * 生成 BizData4Page 实例
	 *
	 * @param dao
	 * @param conditions
	 * @param curPage
	 * @param offset
	 * @param rows
	 * @return
	 */
	public static BizData4Page createBizData4Page(IMyBaseDAO dao, Map<String, Object> conditions, int curPage, int offset, int rows, Map<String, Object> selector) {
		String orderBy = null;
		String sortBy = null;

		Map<String, Object> orderMap = new HashMap<>();

		if (conditions.containsKey("orderBy")) {
			orderBy = conditions.get("orderBy").toString();
			conditions.remove("orderBy");
		}
		if (conditions.containsKey("sortBy")) {
			sortBy = conditions.get("sortBy").toString();
			conditions.remove("sortBy");
		}

		if (orderBy != null && sortBy != null)
			orderMap.put(orderBy, sortBy);

		List mainData = dao.queryPage(conditions, null, offset, rows);
		int records = dao.count(conditions);

		BizData4Page bizData4Page = new BizData4Page();
		bizData4Page.setRows(mainData);
		bizData4Page.setPage(curPage);
		bizData4Page.setRecords(records);

		int total = records / rows;
		int mod = records % rows;
		if (mod > 0) {
			total = total + 1;
		}
		bizData4Page.setTotal(total);

		return bizData4Page;
	}

}

