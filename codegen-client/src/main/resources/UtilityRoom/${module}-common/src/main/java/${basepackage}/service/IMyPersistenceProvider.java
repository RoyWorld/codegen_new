package ${basepackage}.service;


import java.util.Map;

/**
 * Created by BIG on 2016/4/11.
 */
public interface IMyPersistenceProvider<D extends IMyBaseDAO, T extends MyBaseDomain> extends IMyBaseService<D,T>, IMyPageService<D,T> {
}
