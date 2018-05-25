package ${basepackage}.domain.dto;

import cn.thinkjoy.common.managerui.domain.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdong on 15/7/31.
 */
public class ResourceDTO extends BaseDTO{

    private Resource resource;
    private List<ResourceDTO> list;

    private long resourceId;  //本身id
    private String resourceName;
    private long parentResourceId;
    private List<ResourceDTO> resourceInfos  = new ArrayList<ResourceDTO>();
    private long roleId;




    public long getRoleId() {
        return roleId;
    }
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
    public long getResourceId() {
        return resourceId;
    }
    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }
    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public long getParentResourceId() {
        return parentResourceId;
    }
    public void setParentResourceId(long parentResourceId) {
        this.parentResourceId = parentResourceId;
    }
    public List<ResourceDTO> getResourceInfos() {
        return resourceInfos;
    }
    public void setResourceInfos(List<ResourceDTO> resourceInfos) {
        this.resourceInfos = resourceInfos;
    }

    public List<ResourceDTO> getList() {
        return list;
    }

    public void setList(List<ResourceDTO> list) {
        this.list = list;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
