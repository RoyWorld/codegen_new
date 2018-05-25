/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: cv
 * $Id:  AdminController.java 2015-01-27 15:38:37 $
 */

package cn.thinkjoy.${module}.controller;

import cn.thinkjoy.cloudstack.context.CloudContextFactory;
import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;
import cn.thinkjoy.cloudstack.dynconfig.IChangeListener;
import cn.thinkjoy.cloudstack.dynconfig.domain.Configuration;
import cn.thinkjoy.${module}.domain.dto.AssignDTO;
import cn.thinkjoy.${module}.domain.dto.AssignDetailDTO;
import cn.thinkjoy.${module}.domain.dto.RoleDTO;
import cn.thinkjoy.${module}.domain.dto.UserDTO;
import cn.thinkjoy.common.domain.UserDomain;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.managerui.controller.AbstractAdminController;
import cn.thinkjoy.common.managerui.domain.Role;
import cn.thinkjoy.common.managerui.domain.RoleUser;
import cn.thinkjoy.common.managerui.domain.User;
import cn.thinkjoy.common.managerui.service.IRoleService;
import cn.thinkjoy.common.managerui.service.IRoleUserService;
import cn.thinkjoy.common.protocol.ResponseT;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.common.utils.UserContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ${basepackage}.controller.BaseController;


/**
 * 注意：具体的业务系统将##dap###替换成appName
 */


@Controller
@RequestMapping(value = "/admin/${module}")
public class UserController extends BaseController {
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IRoleUserService iRoleUserService;

    private String appName = CloudContextFactory.getCloudContext().getApplicationName();
    private String product = CloudContextFactory.getCloudContext().getProduct();

    private final static RestTemplate restTemplate = new RestTemplate();


    private static String uwwUrl = null;

    @PostConstruct
    public void init() {
        try {
            uwwUrl = DynConfigClientFactory.getClient().getConfig("common", "uwwUrl");
        } catch (Exception e) {
        }

        DynConfigClientFactory.getClient().registerListeners(CloudContextFactory.getCloudContext().getProductCode(),
                CloudContextFactory.getCloudContext().getApplicationName(), "common", "uwwUrl",
                new IChangeListener() {
                    @Override
                    public Executor getExecutor() {
                        return Executors.newSingleThreadExecutor();
                    }

                    @Override
                    public void receiveConfigInfo(final Configuration configuration) {
                        getExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                uwwUrl = configuration.getConfig();

                            }
                        });
                    }
                });
    }


    /**
     * 页面主请求
     *
     * @param request
     * @param response
     * @return 返回菜单数据、表格描述元数据、当前主描述  如本页面为org
     */
    @RequestMapping(value = "/user")
    public ModelAndView renderMainView(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {


        return doRenderMainView(request, response);
    }

    /**
     * 获取所有的组织信息
     *
     * @return
     */
    @RequestMapping(value = "/users")
    @ResponseBody
    public BizData4Page findAllAdmins(HttpServletRequest request, HttpServletResponse response) {
        List<UserDTO> assignUsers = Lists.newArrayList();

        String result = restTemplate.postForObject(uwwUrl + "/queryUsers?appName=" + appName + "&product=" + product, null, String.class);
        TypeReference typeReference = new TypeReference<ResponseT<List<User>>>() {
        };
        ResponseT<List<User>> responseT = (ResponseT<List<User>>) JSON.parseObject(result, typeReference);
        List<User> users = responseT.getBizData();
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int currentPage = 1;
        int pageSize = 10;
        if(!StringUtils.isEmpty(page))
        {
            currentPage = Integer.valueOf(page);
        }

        if(!StringUtils.isEmpty(rows))
        {
            pageSize = Integer.valueOf(rows);
        }
        int start = (currentPage-1)*pageSize;

        int end = start+pageSize;
        if(!(end<users.size()))
        {
            end = users.size();
        }
        int total = (users.size()-1)/pageSize+1;


        for (int i=start;i<end; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setPassword(users.get(i).getPassword());
            userDTO.setUsername(users.get(i).getName());
            userDTO.setId(String.valueOf(users.get(i).getId()));
            assignUsers.add(userDTO);

        }
        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRecords(users.size());
        bizData4Page.setRows(assignUsers);
        bizData4Page.setTotal(total);
        bizData4Page.setPage(currentPage);
        return bizData4Page;
    }


    /**
     * 重写save/update用户
     *
     * @param request
     * @param response
     * @param userDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/commonsave/user")
    public String save(HttpServletRequest request,
                       HttpServletResponse response, UserDTO userDTO) {

        if (StringUtils.isBlank(userDTO.getId())
                || "_empty".equals(userDTO.getId())) {
            userDTO.setId("0");
        }

        UserDomain currentUser = UserContext.getCurrentUser();

        User user = new User();
        user.setCreateDate(System.currentTimeMillis());
        user.setCreator(Long.valueOf(currentUser.getId().toString()));
        user.setName(userDTO.getUsername());
        user.setId(Long.valueOf(userDTO.getId()));
        user.setPassword(userDTO.getPassword());
        user.setStatus(1);


        if (user.getId() > 0) {
            /**
             * 修改用户，只修改密码
             */
            restTemplate.postForObject(uwwUrl + "/changeUserPassword?userId=" + user.getId() + "&password=" + user.getPassword(), null, String.class);

        } else {

            /**
             * 插入新用户
             */
            user.setId(0L);
            TypeReference typeReference = new TypeReference<ResponseT<User>>() {
            };
            String reletiveUrl = "/createNewUser?userName=" + userDTO.getUsername() + "&password=" + userDTO.getPassword();
            String result = restTemplate.postForObject(uwwUrl + reletiveUrl, null, String.class);
            ResponseT<User> responseT = (ResponseT<User>) JSON.parseObject(result, typeReference);
            if (responseT.getBizData() != null) {
                reletiveUrl = "/authorization?userId=" + responseT.getBizData().getId() + "&appName=" + appName + "&product=" + product + "&isAdd=" + true;
                restTemplate.postForObject(uwwUrl + reletiveUrl, null, String.class);
            }

        }
        return "true";
    }

    /**
     * 重写删除用户
     *
     * @param request
     * @param response
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/commondel/user")
    public ModelAndView delete(HttpServletRequest request,
                               HttpServletResponse response, UserDTO userDTO) {
        restTemplate.postForObject(uwwUrl + "/deleteUser?userId=" + userDTO.getId(), null, String.class);
        return doRenderMainView(request, response);
    }


    /**
     * 获取所有角色资源
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "/user/getAllResources")
    @ResponseBody
    public List<RoleDTO> getAllResource(HttpServletRequest request,
                                        HttpServletResponse response) {

        String userId = request.getParameter("userId");
        List<Role> roles = iRoleService.findAll();
        List<RoleDTO> roleDTOs = Lists.newArrayList();

        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setParentResourceId(role.getId());
            roleDTO.setResourceId(role.getId());
            roleDTO.setResourceName(role.getName());

            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("roleId", role.getId());

            RoleUser roleUser = iRoleUserService.queryOne(map);
            if (roleUser != null) {
                roleDTO.setUserId(Long.valueOf(roleUser.getUserId().toString()));
            }
            roleDTOs.add(roleDTO);

        }
        return roleDTOs;

    }


    /**
     * 给某一个用户分配角色
     *
     * @param request
     * @param response
     * @param assign
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/user/assign")
    public Object assign(HttpServletRequest request,
                         HttpServletResponse response, AssignDTO assign) {
        String result = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", assign.getObjId());

        /**
         * 先删除该用户的所有角色
         */
        iRoleUserService.deleteByCondition(map);

        if (assign != null && StringUtils.isNotBlank(assign.getResources())) {
            List<AssignDetailDTO> details = JSON.parseArray(
                    assign.getResources(), AssignDetailDTO.class);
            for (AssignDetailDTO detail : details) {
                RoleUser roleUser = new RoleUser();
                roleUser.setCreateDate(System.currentTimeMillis());
                roleUser.setCreator(Long.valueOf(UserContext.getCurrentUser().getId().toString()));
                roleUser.setRoleId(detail.getResourceId());
                roleUser.setUserId(assign.getObjId());
                iRoleUserService.insert(roleUser);
            }
            result = "分配成功";

        }

        return result;
    }


    @Override
    protected IPageService getMainService() {
        return null;
    }

    @Override
    protected String getBizSys() {
        return "${module}";
    }

    @Override
    protected String getMainObjName() {
        return "user";
    }

    @Override
    protected String getViewTitle() {
        return "用户管理";
    }

    @Override
    protected String getParentTitle() {
        return "基础管理";
    }

    @Override
    public boolean getEnableDataPerm() {
        return true;
    }
}
