package ${basepackage}.domain;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于客户端grid展示的分页对象
 * <p/>
 * 创建时间: 14-9-3 下午9:49<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class BizData4Page<T> implements Serializable {
    /** 当前页 */
    private int page=1;
    /** 总页数 */
    private int total=0;
    /** 分页数 */
    private int pagesize=10;
    /** 总记录数 */
    private int records=0;
    /** 具体数据 */
    private List<T> rows;
    private Map<String,Object> conditions = new HashMap<String,Object>();

    public Map<String, Object> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }

    /** 返回的用户自定义数据 例如总计行？ */
    private Map<List,Object> userdata;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Map<List, Object> getUserdata() {
        return userdata;
    }

    public void setUserdata(Map<List, Object> userdata) {
        this.userdata = userdata;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
