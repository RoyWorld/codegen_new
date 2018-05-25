<#include "/macro.include"/>
<#assign className = table.classNameBo>
<#assign classNameFirstLower = table.classNameFirstLower>
<#assign classNameAllLower = table.classNameBo?lower_case>
<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro namespace>${basepackage}.${persistence}</#macro>

  <#list table.columns as column>
      INSERT INTO `resource_grid` (`id`,`resId`,`displayName`,`colId`,`orderNum`,`width`,`editoptions`,`edittype`,`unformat`,`description`,`moduleName`)
      VALUES(${column_index+1},#resId,'${column.remarks}','${column.sqlName}',${column_index},200,'{}',null,null,'','${classNameAllLower}');
  </#list>
