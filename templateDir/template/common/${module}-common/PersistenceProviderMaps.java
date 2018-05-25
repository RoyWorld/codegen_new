
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${module}ServiceMaps.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */

package ${basepackage}.common;

import cn.thinkjoy.common.managerui.controller.helpers.BasePersistenceProviderMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by xule on 16-1-11.
 * 有负责业务CUD操作时需要添加相关facade服务到这里，facade中覆写对应的接口
 */
@Service("${module}PersistenceProviderMaps")
public class PersistenceProviderMaps extends BasePersistenceProviderMaps {

//    @Autowired
//    private IReportMailTemplateFacade reportMailTemplateFacade;

    @PostConstruct
    public void init() {
//        providerMap.put("reportmailtemplate".toLowerCase(), reportMailTemplateFacade);
    }

}
