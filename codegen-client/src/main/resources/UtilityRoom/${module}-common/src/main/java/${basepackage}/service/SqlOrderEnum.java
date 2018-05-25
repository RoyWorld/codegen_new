package ${basepackage}.service;

/**
 * 功能按钮枚举
 * <p/>
 * 创建时间: 14-10-2 下午12:58<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public enum SqlOrderEnum {
    DESC("desc", "倒序"),
    ASC("asc", "顺序");

    private String action;
    private String name;

    private SqlOrderEnum(String action){
        this.action = action;
    }

    private SqlOrderEnum(String action, String name){
        this.action = action;
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
