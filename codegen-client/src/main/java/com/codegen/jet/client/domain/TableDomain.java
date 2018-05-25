package com.codegen.jet.client.domain;


import com.codegen.jet.dolphins.domains.Column;
import com.codegen.jet.dolphins.domains.ForeignKeys;
import com.codegen.jet.dolphins.domains.Table;
import com.codegen.jet.dolphins.tools.StringHelper;
import com.codegen.core.jet.BaseDomain;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 用于生成代码的Table对象.对应数据库的table
 *
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class TableDomain extends BaseDomain implements java.io.Serializable, Cloneable {

    private Table table;

    public TableDomain(Table table) {
        this.table = table;
    }

    public String removeTableSqlNamePrefix(String sqlName) {
        return this.table.removeTableSqlNamePrefix(sqlName);
    }

//    private String getDbType(String url) {
//        return this.table.ge
//    }

    public String getSubmeterTablePrefixName() {
        return this.table.getSubmeterTablePrefixName();
    }

    public void setSubmeterTablePrefixName(String submeterTablePrefixName) {
        this.table.setSubmeterTablePrefixName(submeterTablePrefixName);
    }

    public boolean isSubmeterTable() {
        return this.table.isSubmeterTable();
    }

    public void setSubmeterTable(boolean submeterTable) {
        this.table.setSubmeterTable(submeterTable);
    }

    public String getSubSqlName() {
        return this.table.getSubSqlName();
    }

    public void setSubSqlName(String subSqlName) {
        this.table.setSubSqlName(subSqlName);
    }

    public String getInstanceName() {
        return this.table.getInstanceName();
    }

    public void setInstanceName(String instanceName) {
        this.table.setInstanceName(instanceName);
    }

    public int getParentId() {
        return this.table.getParentId();
    }

    public void setParentId(int parentId) {
        this.table.setParentId(parentId);
    }

    public int getSeq() {
        return this.table.getSeq();
    }

    public void setSeq(int seq) {
        this.table.setSeq(seq);
    }

    public String getNumber() {
        return this.table.getNumber();
    }

    public void setNumber(String number) {
        this.table.setNumber(number);
    }

    public String getLongnumber() {
        return this.table.getLongnumber();
    }

    public void setLongnumber(String longnumber) {
        this.table.setLongnumber(longnumber);
    }

    public String getName() {
        return this.table.getName();
    }

    public void setName(String name) {
        this.table.setName(name);
    }

    public String getResName() {
        return this.table.getResName();
    }

    public void setResName(String resName) {
        this.table.setResName(resName);
    }

    public String getParentResName() {
        return this.table.getParentResName();
    }

    public void setParentResName(String parentResName) {
        this.table.setParentResName(parentResName);
    }

    public String getParentClassName() {
        return this.table.getParentClassName();
    }

    public void setParentClassName(String parentClassName) {
        this.table.setParentClassName(parentClassName);
    }

    public String getDescription() {
        return this.table.getDescription();
    }

    public void setDescription(String description) {
        this.table.setDescription(description);
    }

    public LinkedHashSet<Column> getColumns() {
        return this.table.getColumns();
    }

    public void setColumns(LinkedHashSet<Column> columns) {
        this.table.setColumns(columns);
    }

    public String getOwnerSynonymName() {
        return this.table.getOwnerSynonymName();
    }

    public void setOwnerSynonymName(String ownerSynonymName) {
        this.table.setOwnerSynonymName(ownerSynonymName);
    }

    public String getTableSynonymName() {
        return this.table.getTableSynonymName();
    }

    public void setTableSynonymName(String tableSynonymName) {
        this.table.setTableSynonymName(tableSynonymName);
    }

    /**
     * 使用 getPkColumns() 替换
     */
    @Deprecated
    public List<Column> getPrimaryKeyColumns() {
        return this.table.getPrimaryKeyColumns();
    }

    /**
     * 使用 setPkColumns() 替换
     */
    @Deprecated
    public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
        this.table.setPrimaryKeyColumns(primaryKeyColumns);
    }

    /**
     * 数据库中表的表名称,其它属性很多都是根据此属性派生
     */
    public String getSqlName() {
        return this.table.getSqlName();
    }

    public void setSqlName(String sqlName) {
        this.table.setSqlName(sqlName);
    }

    /**
     * 数据库中表的表备注
     */
    public String getRemarks() {
        return this.table.getRemarks();
    }

    public void setRemarks(String remarks) {
        this.table.setRemarks(remarks);
    }

    public void addColumn(Column column) {
        this.table.getColumns().add(column);
    }

    /**
     * 根据sqlName得到的类名称，示例值: UserInfo
     *
     * @return
     */
    public String getClassName() {
        if (StringHelper.isBlank(this.table.getClassName())) {
            String removedPrefixSqlName = removeTableSqlNamePrefix(this.table.getSqlName());
            return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixSqlName));
        } else {
            return this.table.getClassName();
        }
    }

    public void setClassName(String customClassName) {
        this.table.setClassName(customClassName);
    }

    /**
     * 数据库中表的别名，等价于:  getRemarks().isEmpty() ? getClassName() : getRemarks()
     */
    public String getTableAlias() {
        if (StringHelper.isNotBlank(this.table.getTableAlias())) return this.table.getTableAlias();
        return StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getClassName()));
    }

    public void setTableAlias(String v) {
        this.table.setTableAlias(v);
    }

    /**
     * 等价于getClassName().toLowerCase()
     *
     * @return
     */
    public String getClassNameLowerCase() {
        return getClassName().toLowerCase();
    }

    /**
     * 得到用下划线分隔的类名称，如className=UserInfo,则underscoreName=user_info
     *
     * @return
     */
    public String getUnderscoreName() {
        return StringHelper.toUnderscoreName(getClassName()).toLowerCase();
    }

    /**
     * 返回值为getClassName()的第一个字母小写,如className=UserInfo,则ClassNameFirstLower=userInfo
     *
     * @return
     */
    public String getClassNameFirstLower() {
        return StringHelper.uncapitalize(getClassName());
    }

    /**
     * ZhangHuihua@msn.com 根据sqlName得到的类名称，示例值: PO:SysUser则BO:User，去除模块前缀
     *
     * @return
     */
    public String getClassNameBo() {
        String _className = getClassName();

        for (int i = 1; i < _className.length(); i++) {
            char c = _className.charAt(i);
            if (Character.isUpperCase(c)) {
                _className = _className.substring(i);
                System.out.println("############" + _className);
                break;
            }
        }
        return _className;
    }

    public String getClassNameBoLowerCase() {
        return getClassNameBo().toLowerCase();
    }

    /**
     * ZhangHuihua@msn.com 返回值为getClassNameBo()的第一个字母小写,如className=User,则ClassNameFirstLower=user
     *
     * @return
     */
    public String getClassNameFirstLowerBo() {
        return StringHelper.uncapitalize(getClassNameBo());
    }

    /**
     * 根据getClassName()计算而来,用于得到常量名,如className=UserInfo,则constantName=USER_INFO
     *
     * @return
     */
    public String getConstantName() {
        return StringHelper.toUnderscoreName(getClassName()).toUpperCase();
    }

    /**
     * 使用 getPkCount() 替换
     */
    @Deprecated
    public boolean isSingleId() {
        return getPkCount() == 1 ? true : false;
    }

    /**
     * 使用 getPkCount() 替换
     */
    @Deprecated
    public boolean isCompositeId() {
        return getPkCount() > 1 ? true : false;
    }

    /**
     * 使用 getPkCount() 替换
     */
    @Deprecated
    public boolean isNotCompositeId() {
        return !isCompositeId();
    }

    /**
     * 得到主键总数
     *
     * @return
     */
    public int getPkCount() {
        int pkCount = 0;
        for (Column c : this.table.getColumns()) {
            if (c.isPk()) {
                pkCount++;
            }
        }
        return pkCount;
    }

    /**
     * use getPkColumns()
     *
     * @deprecated
     */
    public List getCompositeIdColumns() {
        return getPkColumns();
    }

    /**
     * 得到是主键的全部column
     *
     * @return
     */
    public List<Column> getPkColumns() {
        List results = new ArrayList();
        for (Column c : getColumns()) {
            if (c.isPk())
                results.add(c);
        }
        return results;
    }

    /**
     * 得到不是主键的全部column
     *
     * @return
     */
    public List<Column> getNotPkColumns() {
        List results = new ArrayList();
        for (Column c : getColumns()) {
            if (!c.isPk())
                results.add(c);
        }
        return results;
    }

    /**
     * 得到单主键，等价于getPkColumns().get(0)
     */
    public Column getPkColumn() {
//		if(getPkColumns().isEmpty()) {
//			throw new IllegalStateException("not found primary key on table:"+getSqlName());
//		}
        if (getPkColumns().isEmpty()) {
            return null;
        }
        return getPkColumns().get(0);
    }

    /**
     * 使用 getPkColumn()替换
     */
    @Deprecated
    public Column getIdColumn() {
        return getPkColumn();
    }

    public List<Column> getEnumColumns() {
        List results = new ArrayList();
        for (Column c : getColumns()) {
            if (!c.isEnumColumn())
                results.add(c);
        }
        return results;
    }

    public Column getColumnByName(String name) {
        Column c = getColumnBySqlName(name);
        if (c == null) {
            c = getColumnBySqlName(StringHelper.toUnderscoreName(name));
        }
        return c;
    }

    public Column getColumnBySqlName(String sqlName) {
        for (Column c : getColumns()) {
            if (c.getSqlName().equalsIgnoreCase(sqlName)) {
                return c;
            }
        }
        return null;
    }

    public Column getRequiredColumnBySqlName(String sqlName) {
        if (getColumnBySqlName(sqlName) == null) {
            throw new IllegalArgumentException("not found column with sqlName:" + sqlName + " on table:" + getSqlName());
        }
        return getColumnBySqlName(sqlName);
    }

    /**
     * 忽略过滤掉某些关键字的列,关键字不区分大小写,以逗号分隔
     *
     * @param ignoreKeywords
     * @return
     */
    public List<Column> getIgnoreKeywordsColumns(String ignoreKeywords) {
        List results = new ArrayList();
        for (Column c : getColumns()) {
            String sqlname = c.getSqlName().toLowerCase();
            if (StringHelper.contains(sqlname, ignoreKeywords.split(","))) {
                continue;
            }
            results.add(c);
        }
        return results;
    }

    /**
     * This method was created in VisualAge.
     */
    public void initImportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {
        this.table.initImportedKeys(dbmd);
    }

    /**
     * This method was created in VisualAge.
     */
    public void initExportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {
        // get Exported keys
        // 取外键关系，没什么用，因为太慢了
//			   ResultSet fkeys = dbmd.getExportedKeys(catalog,schema,this.table.sqlName);
//
//			   while ( fkeys.next()) {
//				 String pktable = fkeys.getString(PKTABLE_NAME);
//				 String pkcol   = fkeys.getString(PKCOLUMN_NAME);
//				 String fktable = fkeys.getString(FKTABLE_NAME);
//				 String fkcol   = fkeys.getString(FKCOLUMN_NAME);
//				 String seq     = fkeys.getString(KEY_SEQ);
//				 Integer iseq   = new Integer(seq);
//				 getExportedKeys().addForeignKey(fktable,fkcol,pkcol,iseq);
//			   }
//			   fkeys.close();
    }

    /**
     * @return Returns the exportedKeys.
     */
    public ForeignKeys getExportedKeys() {
        if (this.table.getExportedKeys() == null) {
//            exportedKeys = new ForeignKeys(this);
        }
        return this.table.getExportedKeys();
    }

    /**
     * @return Returns the importedKeys.
     */
    public ForeignKeys getImportedKeys() {
        if (this.table.getImportedKeys() == null) {
//            importedKeys = new ForeignKeys(this);
        }
        return this.table.getImportedKeys();
    }

    public String toString() {
        return "Database TableDomain:" + getSqlName() + " to ClassName:" + getClassName();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            //ignore
            return null;
        }
    }

    public String getFileName() {
        return getClassNameBo();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
