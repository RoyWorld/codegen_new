package ${basepackage}.service;

/**
 * Created by BIG on 2016/3/11.
 */

/**
 * 业务主对象DAO注入
 * <p/>
 * 创建时间: 14-9-23 下午2:04<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface IMyDaoAware<D extends IMyBaseDAO,T extends MyBaseDomain> {
    D getDao();
}
