-- model
<#list tables as table>
<#assign className = table.classNameBo>
<#assign classNameLower = className?uncap_first>
INSERT INTO `${module}_model`(`id`,`name`,`tblName`,`description`, `creator`, `createDate`, `lastModifier`, `lastModDate`, `status`)
VALUES('${table.seq}','${table.name}','${table.sqlName}','${table.description}', 0, 1, 0, 1, 10);
</#list>


-- parent resource
<#list parentRes?keys as key>
<#assign prId = parentRes[key].seq>
<#assign prNumber = parentRes[key].number>
INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`)
VALUES('${prId}','/admin/${bizSys}/${key}','${prId}',0,'${prNumber}','${prNumber}','${key}',0,${times},0,${times},null,null, '');
</#list>

-- resource
<#assign resourceId=1/>
<#assign resourceGridId=1/>
<#assign resourceActionId=1/>
<#list tables as table>

<#assign className = table.classNameBo>
<#assign classNameFirstLower = table.classNameFirstLower>
<#assign classNameAllLower = table.classNameBo?lower_case>


INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`,`status`)
VALUES('${table.seq}','/admin/${bizSys}/${classNameAllLower}','${table.seq}','${table.parentId}','${table.number}','${table.longnumber}','${table.resName}',0,${times},0,${times},null,'${table.seq}', '${classNameAllLower}',0);

  <#assign className = table.classNameBo>
  <#assign classNameFirstLower = table.classNameFirstLower>
  <#assign idJavaType = table.idColumn.javaType>
  <#macro mapperEl value>${r"#{"}${value}}</#macro>
  <#macro namespace>${basepackage}.${persistence}</#macro>

  -- resource_action
  <#list actions as ac>
      INSERT INTO `${module}_resource_action` (`id`,`resourceId`,`name`,`actionAlias`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`status`)
      VALUES(${resourceActionId},${table.seq},'${ac.name}','${ac.action}',0,${times},0,${times},'${ac.name}',0);
      <#assign resourceActionId=resourceActionId+1/>
  </#list>

  -- resource_grid
  <#list table.columns as column>
      INSERT INTO `${module}_resource_grid` (`id`,`resId`,`displayName`,`colId`,`orderNum`,`width`,`editoptions`,`edittype`,`unformat`,`description`,`moduleName`, `creator`, `createDate`, `lastModifier`, `lastModDate`, `status`)
      VALUES(${resourceGridId},${table.seq},'${column.name}','${column.sqlName}',${column_index},200,'{}',null,'','${column.description}','${classNameAllLower}', 0, 1, 0, 1, 0);
      <#assign resourceGridId=resourceGridId+1/>
  </#list>
<#assign resourceId=resourceId+1/>
</#list>


-- 用户管理
INSERT INTO `${module}_model`(`id`,`name`,`tblName`,`description`, `creator`, `createDate`, `lastModifier`, `lastModDate`, `status`)
VALUES('500','用户','${module}_user','用户', 0, 1, 0, 1, 10);

INSERT INTO `${module}_resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`, `bizModelName`,`status`)
VALUES('500','/admin/${bizSys}/user','500','1','n500','a1_n500','用户管理',0,${times},0,${times},null,'500', 'user',0);

INSERT INTO `${module}_resource_action` (`resourceId`,`name`,`actionAlias`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`status`) VALUES
('500',  '新增', 'add', '0', '1451888820030', '0', '1451888820030', '新增', '0'),
('500',  '修改', 'edit', '0', '1451888820030', '0', '1451888820030', '修改', '0'),
('500',  '删除', 'del', '0', '1451888820030', '0', '1451888820030', '删除', '0'),
('500',  '导出数据', 'export', '0', '1451888820030', '0', '1451888820030', '导出数据', '0'),
('500', '导入数据', 'import', '0', '1451888820030', '0', '1451888820030', '导入数据', '0'),
('500',  '分配角色', 'role_assign', '0', '1451888820030', '0', '1451888820030', '分配角色', '0');


 INSERT INTO `${module}_resource_grid` (`resId`,`displayName`,`colId`,`orderNum`,`width`,`editoptions`,`edittype`,`unformat`,`description`,`moduleName`, `creator`, `createDate`, `lastModifier`, `lastModDate`, `status`,`editable`)VALUES
 ('500','用户id','id','1',200,'{}',null,'','用户id','user', 0, 1, 0, 1, 0,'false'),
 ('500','用户名','username','1',200,'{}',null,'','用户名','user', 0, 1, 0, 1, 0,'true'),
 ('500','密码（md5）','password','1',200,'{}',null,'','密码','user', 0, 1, 0, 1, 0,'true');