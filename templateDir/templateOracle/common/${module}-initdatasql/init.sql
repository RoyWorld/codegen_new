-- model
<#list tables as table>
<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
INSERT INTO `${module}_model`(`id`,`name`,`tblName`,`description`)
VALUES('${table.seq}','${table.name}','${table.sqlName}','${table.description}');
</#list>


-- parent resource
<#list parentRes?keys as key>
<#assign prId = parentRes[key].seq>
<#assign prNumber = parentRes[key].number>
INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`)
VALUES('${prId}','/admin/${bizSys}/${key}','${prId}',null,'${prNumber}','${prNumber}','${key}',0,${times},0,${times},null,null, '');
</#list>

-- resource
<#assign resourceId=1/>
<#assign resourceGridId=1/>
<#assign resourceActionId=1/>
<#list tables as table>

<#assign className = table.classNameBo>
<#assign classNameFirstLower = table.classNameFirstLower>
<#assign classNameAllLower = table.classNameBo?lower_case>


INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`)
VALUES('${table.seq}','/admin/${bizSys}/${classNameAllLower}','${table.seq}','${table.parentId}','${table.number}','${table.longnumber}','${table.resName}',0,${times},0,${times},null,'${table.seq}', '${classNameAllLower}');

  <#assign className = table.classNameBo>
  <#assign classNameFirstLower = table.classNameFirstLower>
  <#macro mapperEl value>${r"#{"}${value}}</#macro>
  <#macro namespace>${basepackage}.${persistence}</#macro>

  -- resource_action
  <#list actions as ac>
      INSERT INTO `${module}_resource_action` (`id`,`resourceId`,`name`,`actionAlias`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`)
      VALUES(${resourceActionId},${table.seq},'${ac.name}','${ac.action}',0,${times},0,${times},'${ac.name}');
      <#assign resourceActionId=resourceActionId+1/>
  </#list>

  -- resource_grid
  <#list table.columns as column>
      INSERT INTO `${module}_resource_grid` (`id`,`resId`,`displayName`,`colId`,`orderNum`,`width`,`editoptions`,`edittype`,`unformat`,`description`,`moduleName`)
      VALUES(${resourceGridId},${table.seq},'${column.name}','${column.sqlName}',${column_index},200,'{}',null,'','${column.description}','${classNameAllLower}');
      <#assign resourceGridId=resourceGridId+1/>
  </#list>
<#assign resourceId=resourceId+1/>
</#list>