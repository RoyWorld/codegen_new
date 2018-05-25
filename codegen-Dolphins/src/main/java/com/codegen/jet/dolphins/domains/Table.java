package com.codegen.jet.dolphins.domains;


import com.codegen.jet.dolphins.TableFactory;
import com.codegen.jet.dolphins.tools.StringHelper;
import com.codegen.jet.dolphins.util.ReadProperties;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * 用于生成代码的Table对象.对应数据库的table
 *
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class Table implements java.io.Serializable, Cloneable {

    public static final String PKTABLE_NAME = "PKTABLE_NAME";
    public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
    public static final String FKTABLE_NAME = "FKTABLE_NAME";
    public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
    public static final String KEY_SEQ = "KEY_SEQ";
    protected static final Set<String> ORACLE_KEY_COLUMN_SETS = new HashSet<String>();

    static {
        ORACLE_KEY_COLUMN_SETS.add("name");
        ORACLE_KEY_COLUMN_SETS.add("type");
        ORACLE_KEY_COLUMN_SETS.add("sequence");
        ORACLE_KEY_COLUMN_SETS.add("start");
    }

    //分表 变量名称
    protected String submeterTablePrefixName;
    protected boolean submeterTable = false;
    protected String subSqlName;
    protected String sqlName;
    protected String remarks;
    protected String className;
    protected String instanceName;
    /**
     * 序列   生成的自增id值
     */
    int seq;
    /**
     * 编码   有些是树形结构需要确定上下级关系
     */
    String number;
    String longnumber;
    /**
     * 业务模型名称
     */
    String name;
    /**
     * 菜单名称
     */
    String resName;
    /**
     * 父菜单名称
     */
    String parentResName;
    /**
     * 继承父类名称
     */
    String parentClassName;
    String description;
    int parentId;
    LinkedHashSet<Column> columns = new LinkedHashSet<Column>();
    List<Column> primaryKeyColumns = new ArrayList<Column>();
    protected String catalog = TableFactory.getInstance().getCatalog();
    protected String schema = TableFactory.getInstance().getSchema();
    /**
     * the name of the owner of the synonym if this table is a synonym
     */
    protected String ownerSynonymName = null;
    /**
     * real table name for oracle SYNONYM
     */
    protected String tableSynonymName = null;
    protected String tableAlias;
    protected ForeignKeys exportedKeys;
    protected ForeignKeys importedKeys;

    public Table() {
    }

    public Table(Table t) {
        setSqlName(t.getSqlName());
        this.remarks = t.getRemarks();
        this.className = t.getClassName();
        this.ownerSynonymName = t.getOwnerSynonymName();
        this.columns = t.getColumns();
        this.primaryKeyColumns = t.getPrimaryKeyColumns();
        this.tableAlias = t.getTableAlias();
        this.exportedKeys = t.exportedKeys;
        this.importedKeys = t.importedKeys;
    }

    public static String removeTableSqlNamePrefix(String sqlName) {
        String prefixs = ReadProperties.getProperty("tableRemovePrefixes", "");
        for (String prefix : prefixs.split(",")) {
            String removedPrefixSqlName = StringHelper.removePrefix(sqlName, prefix, true);
            if (!removedPrefixSqlName.equals(sqlName)) {
                return removedPrefixSqlName;
            }
        }
        return sqlName;
    }

    protected static String getDbType(String url) {
        if (url.toLowerCase().indexOf("mysql") != -1) {
            return "mysql";
        } else if (url.toLowerCase().indexOf("oracle") != -1) {
            return "oracle";
        }
        return "mysql";
    }

    public String getSubmeterTablePrefixName() {
        return submeterTablePrefixName;
    }

    public void setSubmeterTablePrefixName(String submeterTablePrefixName) {
        this.submeterTablePrefixName = submeterTablePrefixName;
    }

    public boolean isSubmeterTable() {
        return submeterTable;
    }

    public void setSubmeterTable(boolean submeterTable) {
        this.submeterTable = submeterTable;
    }

    public String getSubSqlName() {
        return subSqlName;
    }

    public void setSubSqlName(String subSqlName) {
        this.subSqlName = subSqlName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLongnumber() {
        return longnumber;
    }

    public void setLongnumber(String longnumber) {
        this.longnumber = longnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getParentResName() {
        return parentResName;
    }

    public void setParentResName(String parentResName) {
        this.parentResName = parentResName;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedHashSet<Column> getColumns() {
        //针对 oracle 做特殊处理    name,type,sequence,start
        String url = ReadProperties.getRequiredProperty("jdbc.url");
        String dbType = getDbType(url);
        if (JdbcConstants.ORACLE.equals(dbType)) { //oracle
            LinkedHashSet<Column> newColumns = new LinkedHashSet<Column>();
            for (Column column : columns) {
                if (ORACLE_KEY_COLUMN_SETS.contains(column.getName())) {
                    column.setName("\"" + column.getName().toUpperCase() + "\"");
                }
                newColumns.add(column);
            }
            return newColumns;
        } else {
            return columns;
        }
    }

    public void setColumns(LinkedHashSet<Column> columns) {
        this.columns = columns;
    }

    public String getOwnerSynonymName() {
        return ownerSynonymName;
    }

    public void setOwnerSynonymName(String ownerSynonymName) {
        this.ownerSynonymName = ownerSynonymName;
    }

    public String getTableSynonymName() {
        return tableSynonymName;
    }

    public void setTableSynonymName(String tableSynonymName) {
        this.tableSynonymName = tableSynonymName;
    }

    /**
     * 使用 getPkColumns() 替换
     */
    @Deprecated
    public List<Column> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    /**
     * 使用 setPkColumns() 替换
     */
    @Deprecated
    public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    /**
     * 数据库中表的表名称,其它属性很多都是根据此属性派生
     */
    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    /**
     * 数据库中表的表备注
     */
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    /**
     * 根据sqlName得到的类名称，示例值: UserInfo
     *
     * @return
     */
    public String getClassName() {
        if (StringHelper.isBlank(className)) {
            String removedPrefixSqlName = removeTableSqlNamePrefix(sqlName);
            return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixSqlName));
        } else {
            return className;
        }
    }

    public void setClassName(String customClassName) {
        this.className = customClassName;
    }

    /**
     * 数据库中表的别名，等价于:  getRemarks().isEmpty() ? getClassName() : getRemarks()
     */
    public String getTableAlias() {
        if (StringHelper.isNotBlank(tableAlias)) return tableAlias;
        return StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getClassName()));
    }

    public void setTableAlias(String v) {
        this.tableAlias = v;
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
        for (Column c : columns) {
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

        // get imported keys a

        ResultSet fkeys = dbmd.getImportedKeys(catalog, schema, this.sqlName);

        while (fkeys.next()) {
            String pktable = fkeys.getString(PKTABLE_NAME);
            String pkcol = fkeys.getString(PKCOLUMN_NAME);
            String fktable = fkeys.getString(FKTABLE_NAME);
            String fkcol = fkeys.getString(FKCOLUMN_NAME);
            String seq = fkeys.getString(KEY_SEQ);
            Integer iseq = new Integer(seq);
            getImportedKeys().addForeignKey(pktable, pkcol, fkcol, iseq);
        }
        fkeys.close();
    }

    /**
     * This method was created in VisualAge.
     */
    public void initExportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {
        // get Exported keys
        // 取外键关系，没什么用，因为太慢了
//			   ResultSet fkeys = dbmd.getExportedKeys(catalog,schema,this.sqlName);
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
        if (exportedKeys == null) {
            exportedKeys = new ForeignKeys(this);
        }
        return exportedKeys;
    }

    /**
     * @return Returns the importedKeys.
     */
    public ForeignKeys getImportedKeys() {
        if (importedKeys == null) {
            importedKeys = new ForeignKeys(this);
        }
        return importedKeys;
    }

    public String toString() {
        return "Database Table:" + getSqlName() + " to ClassName:" + getClassName();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            //ignore
            return null;
        }
    }
}
