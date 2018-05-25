package template.mybatis.mapper;

<#assign className = table.className>
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.${persistence}.mapper;

import ${basepackage}.dal.BaseMapper;
import ${basepackage}.${persistence}.beans.${className};
import org.springframework.stereotype.Repository;

<#if table.pkCount!=0 >

@Repository
public interface ${className}Mapper extends BaseMapper<${className},${table.idColumn.possibleShortJavaType}>{
<#else>

@Repository
public interface ${className}Mapper extends BaseMapper<${className},String>{
</#if>

	
<#list table.columns as column>
	<#if (column.unique && !column.pk)>
	${className} findBy${column.columnName}(${column.possibleShortJavaType} ${column.columnNameFirstLower});
	</#if>
</#list>

}
