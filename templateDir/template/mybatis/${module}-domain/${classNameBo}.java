<#assign className = table.classNameBo>
/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: ${module}
 * $Id:  ${className}.java ${now?string('yyyy-MM-dd HH:mm:ss')} $
 */
<#include "/macro.include"/>
<#assign classNameLower = className?uncap_first>

<#assign isCbd = false>

<#list table.columns as column>
    <#if column=='creator'>
        <#assign isCbd = true>
    </#if>
</#list>
<#assign pkType = "Long">

<#if table.getPkColumn()?exists>

	<#if table.getPkColumn().sqlType==4>
		<#assign pkType = "Integer">
	<#elseif table.getPkColumn().sqlType==12>
		<#assign pkType = "String">
	<#elseif table.getPkColumn().sqlType==-5>
		<#assign pkType = "Long">
	</#if>

</#if>

package ${basepackage}.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import cn.qtone.zyj.MyBaseDomain;


import java.util.*;

public class ${className} extends MyBaseDomain<${pkType}>{
<#list table.columns as column>

    /** ${column.remarks} */
    private ${column.possibleShortJavaType} ${column.columnNameFirstLower};

</#list>

<@generateConstructor className/>
<@generateJavaColumns/>

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.columnName}",get${column.columnName}())
		</#list>
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof ${className} == false) return false;
		if(this == obj) return true;
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
}

<#macro generateJavaColumns>
<#list table.columns as column>
    <#if column.isDateTimeColumn>

    </#if>
	/**
	 *${column.remarks}
	 * @param value
	 */
	public void set${column.columnName}(${column.possibleShortJavaType} value) {
        this.${column.columnNameFirstLower} = value;
    }
	/**
	 *${column.remarks}
	 * @return
	 */
    public ${column.possibleShortJavaType} get${column.columnName}() {
        return this.${column.columnNameFirstLower};
    }

</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>

	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}

	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>

	private ${fkPojoClass} ${fkPojoClassVar};

	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}

	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>
