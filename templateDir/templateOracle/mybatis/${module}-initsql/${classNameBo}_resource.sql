<#assign className = table.classNameBo>
<#assign classNameFirstLower = table.classNameFirstLower>
<#assign classNameAllLower = table.classNameBo?lower_case>

INSERT INTO `resource`(`id`,`url`,`orderNum`,`parentId`,`number`,`longNumber`,`name`,`creator`,`createDate`,`lastModifier`,`lastModDate`,`description`,`modelId`)
VALUES(1,'/admin/${classNameAllLower}',1,null,'a','a_1','#name',0,0,0,0,null,null);
