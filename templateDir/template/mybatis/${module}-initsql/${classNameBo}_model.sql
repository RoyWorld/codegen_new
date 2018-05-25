<#assign className = table.classNameBo>
<#assign classNameFirstLower = table.classNameFirstLower>

INSERT INTO `model`(`id`,`name`,`tblName`)
VALUES(1,'name','${table.sqlName}');