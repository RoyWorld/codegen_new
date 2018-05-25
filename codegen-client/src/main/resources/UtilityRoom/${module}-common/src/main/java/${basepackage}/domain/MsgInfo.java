package ${basepackage}.domain;


/**
 * Created by BIG on 2016/4/14.
 */
public class MsgInfo {

    private String s;
    /**
     * 返回的响应码 为空，说明是正常返回
     */
    private String rtnCode;

    /**
     * 错误信息 有业务异常的时候，来源于BizException；否则网关出错（系统异常），使用通用异常
     */
    public String getMsg() {
        return getS();
    }

    /**
     * 错误信息 有业务异常的时候，来源于BizException；否则网关出错（系统异常），使用通用异常
     */
    public void setMsg(String msg) {
        this.setS(msg);
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public MsgInfo(String rtnCode, String msg) {
        this.rtnCode = rtnCode;
        setMsg(msg);
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
