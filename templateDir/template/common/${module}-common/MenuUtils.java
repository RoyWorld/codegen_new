package ${basepackage}.common;

import cn.thinkjoy.common.managerui.domain.Resource;
import cn.thinkjoy.common.managerui.domain.ResourceGrid;
import ${basepackage}.domain.dto.ResourceDTO;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdong on 15/7/31.
 */
public class MenuUtils {


    public static void setGridValue(List<ResourceGrid> resourceGridList)
    {
        for(ResourceGrid resourceGrid:resourceGridList)
        {
            String f = resourceGrid.getFormatter();
            if(!StringUtils.isEmpty(f) && f.equals("\"\"")) {
                f = "setDefultValue";
                resourceGrid.setFormatter(f);
            }
        }
    }
    public static List<ResourceDTO> getTreeMenu(List<Resource> list)
    {
        List<ResourceDTO> dtoList = new ArrayList<>();
        ResourceDTO resourceDTO = null;
        for(Resource resource:list)
        {
            resourceDTO = new ResourceDTO();
            if(resource.getParentId()==0)
            {
                resourceDTO.setResource(resource);
                resourceDTO.setList(getList(list, resource.getId().intValue()));
                dtoList.add(resourceDTO);
            }
        }
        return dtoList;
    }
    private static List<ResourceDTO> getList(List<Resource> list,int parentId)
    {
        List<ResourceDTO> dtoList = new ArrayList<>();
        ResourceDTO resourceDTO = null;
        for(Resource resource:list)
        {
            resourceDTO = new ResourceDTO();
            if(resource.getParentId()==parentId)
            {
                resourceDTO.setResource(resource);
                resourceDTO.setList(getList(list, resource.getId().intValue()));
                dtoList.add(resourceDTO);
            }
        }
        return dtoList;
    }
}
