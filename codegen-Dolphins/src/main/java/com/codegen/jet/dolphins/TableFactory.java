package com.codegen.jet.dolphins;


import com.codegen.jet.dolphins.domains.Column;
import com.codegen.jet.dolphins.domains.ParentRes;
import com.codegen.jet.dolphins.domains.Table;
import com.codegen.jet.dolphins.domains.NumGen;
import com.codegen.jet.dolphins.util.DataSourceProvider;
import com.codegen.jet.dolphins.util.ReadProperties;
import com.codegen.jet.dolphins.domains.SeqGen;
import com.codegen.jet.dolphins.domains.JdbcConstants;
import com.codegen.jet.dolphins.tools.*;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * 根据数据库表的元数据(metadata)创建Table对象
 * <p>
 * <pre>
 * getTable(sqlName) : 根据数据库表名,得到table对象
 * getAllTable() : 搜索数据库的所有表,并得到table对象列表
 * </pre>
 *
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class TableFactory {

    private static boolean isStandard = true;

    private static TableFactory instance = null;
    private static String[] alreadyTbls = new String[]{
            "_data_model", "_model", "_resource", "_resource_action", "_resource_grid",
            "_role", "_role_resource", "_role_user", "_user_data", "_datagroup", "_datagroup_data", "_user_datagroup"};
    private static String[] defaultColumns = new String[]{"createDate", "lastModifier", "lastModDate",
            "dataModelId", "dataId", "groupId", "tblName",
            "isAdmin", "datagroupId", "bizDimension", "employeeId",
            "modelId", "assignUrl", "whereSql",
            "resourceId", "resourceActionId", "roleId", "userId",
            "orderNum", "parentId", "longNumber", "bizModelName",
            "resId", "displayName", "colId", "orderNum", "moduleName",
            "resourceId", "divId", "actionScript", "actionAlias"};
    private DbHelper dbHelper = new DbHelper();
    private List<Table> tables = new ArrayList<Table>();
    private Map<String, ParentRes> parentResMap = new HashMap<String, ParentRes>();
    private Map<String, String> tabException = new HashMap<String, String>();
    private String dbType = "mysql";

    private TableFactory() {
        String url = ReadProperties.getRequiredProperty("jdbc.url");
        setDbType(url);
    }

    public synchronized static TableFactory getInstance() {
        if (instance == null) instance = new TableFactory();
        return instance;
    }

    private void setDbType(String url) {
        if (url.toLowerCase().indexOf("mysql") != -1) {
            this.dbType = "mysql";
        } else if (url.toLowerCase().indexOf("oracle") != -1) {
            this.dbType = "oracle";
        }

    }

    public String getCatalog() {
        return ReadProperties.getNullIfBlank("jdbc.catalog");
    }

    public String getSchema() {
        return ReadProperties.getNullIfBlank("jdbc.schema");
    }

    private Connection getConnection() {
        return DataSourceProvider.getConnection();
    }


    public List getAllTables() {
        try {
            Connection conn = getConnection();
            if (JdbcConstants.ORACLE.equals(this.dbType)) {
//				tabException.put("CAS_USER2","");
                return getAllOracleTables(conn);
            } else
                return getAllTables(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Table getTable(String tableName) {
        return getTable(getSchema(), tableName);
    }

    private Table getTable(String schema, String tableName) {
        return getTable(getCatalog(), schema, tableName);
    }

    private Table getTable(String catalog, String schema, String tableName) {
        Table t = null;
        try {
            t = _getTable(catalog, schema, tableName);
            if (t == null && !tableName.equals(tableName.toUpperCase())) {
                t = _getTable(catalog, schema, tableName.toUpperCase());
            }
            if (t == null && !tableName.equals(tableName.toLowerCase())) {
                t = _getTable(catalog, schema, tableName.toLowerCase());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (t == null) {
            throw new NotFoundTableException("not found table with give name:" + tableName + (dbHelper.isOracleDataBase() ? " \n databaseStructureInfo:" + getDatabaseStructureInfo() : ""));
        }
        return t;
    }

    private Table _getTable(String catalog, String schema, String tableName) throws SQLException {
        if (tableName == null || tableName.trim().length() == 0)
            throw new IllegalArgumentException("tableName must be not empty");
        catalog = StringHelper.defaultIfEmpty(catalog, null);
        schema = StringHelper.defaultIfEmpty(schema, null);

        Connection conn = getConnection();
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(catalog, schema, tableName, null);
        while (rs.next()) {
            Table table = createTable(conn, rs);
            return table;
        }
        return null;
    }

    private Table createTable(Connection conn, ResultSet rs) throws SQLException {
        String realTableName = null;
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            //String schemaName = rs.getString("TABLE_SCHEM") == null ? "" : rs.getString("TABLE_SCHEM");
            realTableName = rs.getString("TABLE_NAME");
            String tableType = rs.getString("TABLE_TYPE");
            String remarks = rs.getString("TABLE_COMMENT");
            if (remarks == null && dbHelper.isOracleDataBase()) {
                remarks = getOracleTableComments(realTableName);
            }

            Table table = new Table();
            table.setSqlName(realTableName);
            table.setRemarks(remarks);

            ParentRes parentRes = null;
            if (remarks != null) {
                String[] comments = null;
                if (isStandard) {
                    String[] lines = remarks.split("\\n");
                    if (lines.length > 1)
                        table.setDescription(lines[1]);

                    comments = lines[0].split("\\|");
                    table.setName(comments[0]);
                    if (comments.length > 1) {
//                        table.setResName(comments[1]);
//                        table.setParentResName(comments[2]);
//                        if (comments[3] != null) {
//                            table.setParentClassName(comments[3]);
//                        } else {
//                            table.setParentClassName("BaseDomain");
//                        }
                        if (comments[1] != null) {
                            table.setParentClassName(comments[1]);
                        } else {
                            table.setParentClassName("BaseDomain");
                        }
                    } else {
                        table.setParentClassName("BaseDomain");
                    }
                } else {
                    table.setParentClassName("BaseDomain");
                }

//                if (comments != null && comments.length > 1 && parentResMap.get(comments[2]) == null) {
//                    parentRes = new ParentRes();
//                    parentRes.setName(comments[2]);
//                    parentRes.setSeq(SeqGen.incr(SeqGen.MODEL));
//
//                    parentRes.setNumber(NumGen.genNum(parentRes.getSeq()));
//
//                    parentResMap.put(comments[2], parentRes);
//                } else {
//                    parentRes = new ParentRes();
//                    parentRes.setName("");
//                    parentRes.setSeq(SeqGen.incr(SeqGen.MODEL));
//
//                    parentRes.setNumber(NumGen.genNum(parentRes.getSeq()));
//
//                    parentResMap.put("", parentRes);
//                }
            } else {
                throw new RuntimeException("table comments is not empty");
            }

//            table.setSeq(SeqGen.incr(SeqGen.MODEL));
//            String number = NumGen.genNum(table.getSeq());
//            table.setNumber(number);
//            if(parentRes != null) {
//                table.setLongnumber(parentRes.getNumber() + "_" + table.getNumber());
//            }

            if ("SYNONYM".equals(tableType) && dbHelper.isOracleDataBase()) {
                table.setOwnerSynonymName(getSynonymOwner(realTableName));
            }

            retriveTableColumns(table);

            table.initExportedKeys(conn.getMetaData());
            table.initImportedKeys(conn.getMetaData());
            BeanHelper.copyProperties(table, TableOverrideValuesProvider.getTableOverrideValues(table.getSqlName()));
            return table;
        } catch (SQLException e) {
            throw new RuntimeException("create table object error,tableName:" + realTableName, e);
        }
    }

    public Map<String, ParentRes> getAllParentRes() {
        return parentResMap;
    }

    private List getAllTables(Connection conn) throws SQLException {
        //按新的方式处理
        Connection schemaConn = DataSourceProvider.getSchemaConnection();
        DatabaseMetaData dbMetaData = conn.getMetaData();
        //ResultSet rs = dbMetaData.getTables(getCatalog(), getSchema(), null, null);
        ResultSet rs = dbMetaData.getTables(conn.getCatalog(), getSchema(), null, null);
        String pre = "";
        try {
            pre = ReadProperties.getRequiredProperty("table.pre");
            if (!pre.isEmpty())
                pre = "TABLE_NAME  like '" + pre + "%' and ";

        } catch (Exception ex) {

        }
        try {

            String notpre = ReadProperties.getRequiredProperty("table.notpre");

            if (!notpre.isEmpty()) {
                pre = "TABLE_NAME  not like '" + notpre + "%' and ";
            }
        } catch (Exception ex) {

        }
//		String sql = "select * from TABLES where  " + pre +"TABLE_SCHEMA = '" + ReadProperties.getRequiredProperty("jdbc.databasename") + "'" + " and table_name in('tb_exam_issue_class','tb_exam_issue_school','tb_exam_marking','tb_exam_markingdetail','tb_exam_marking_custom','tb_exam_markrule','tb_exam_mark_log','tb_exam_obj_que','tb_exam_obj_score','tb_exam_paper','tb_exam_paperimport','tb_exam_paperstudent','tb_exam_paper_exam_question_opt','tb_exam_paper_mark','tb_exam_paper_mark_detail','tb_exam_paper_mark_quality','tb_exam_paper_subject','tb_exam_paper_subject_question','tb_exam_postil','tb_exam_questionknowledge','tb_exam_question_subject_group','tb_exam_ques_mark_setting','tb_exam_scorepoint','tb_exam_studentpaperabsent','tb_exam_stu_subject','tb_exam_stu_subject_score','tb_exam_sub_que','tb_exam_sub_score','tb_exam_temp_seq','tb_exam_temp_seq_batch','tb_exam_upload_progress','tb_exam_upload_server')";
//		String sql = "select * from TABLES where  " + pre + "TABLE_SCHEMA = '" + ReadProperties.getRequiredProperty("jdbc.databasename") +
//				"'" + " and table_name in('tb_auth_menu','tb_auth_menu_role'," +
//				"'tb_auth_role','tb_cas_area','tb_cas_city','tb_cas_class'," +
//				"'tb_cas_email_log','tb_cas_examtable_pegging','tb_cas_family','tb_cas_grade'," +
//				"'tb_cas_grade_group','tb_cas_issue_school','tb_cas_knowledgepoint','tb_cas_login_log'," +
//				"'tb_cas_parent','tb_cas_province','tb_cas_school','tb_cas_sms_log','tb_cas_student'," +
//				"'tb_cas_subject','tb_cas_teacher_class','tb_cas_teacher','tb_cas_teaching_material'," +
//				"'tb_cas_teaching_publish','tb_cas_town','tb_cas_upload_server','tb_cas_user','tb_cas_user_group'," +
//				"'tb_cas_user_permission','tb_exam_template')";

        String sql = "select * from TABLES where  " + pre + "TABLE_SCHEMA = '" + ReadProperties.getRequiredProperty("jdbc.databasename") + "'";
//				 + " and table_name in('tb_exam_sub_task')";
        System.out.println(sql);
        rs = schemaConn.createStatement().executeQuery(sql);
        //List tables = new ArrayList();
        if (tables.size() == 0) {
            while (rs.next()) {
                tables.add(createTable(conn, rs));
            }

            //handle number and parent props
            String[] splits = null;
            String className = "I";
            String instanceName = "";
            for (Table table : tables) {
                table.setSeq(SeqGen.incr(SeqGen.MODEL));
                table.setNumber(NumGen.genNum(table.getSeq()));
                if (isStandard && parentResMap.get(table.getParentResName()) != null) {
                    table.setLongnumber(parentResMap.get(table.getParentResName()).getNumber() + "_" + table.getNumber());
                    table.setParentId(parentResMap.get(table.getParentResName()).getSeq());
                }
                splits = table.getSqlName().split("_");
                for (int i = 1; i < splits.length; i++) {
                    className += splits[i].replaceFirst(String.valueOf(splits[i].charAt(0)), String.valueOf(splits[i].charAt(0)).toUpperCase());
                    instanceName += splits[i];
                }

                table.setClassName(className);
                className = "I";
                table.setInstanceName(instanceName);
                instanceName = "";
            }
        }

        return tables;
    }

    private List getAllOracleTables(Connection conn) throws SQLException {
        String sql = "select a.TABLE_NAME as TABLE_NAME, b.COMMENTS  from USER_TABLES a left join  USER_TAB_COMMENTS b on a.TABLE_NAME=b.TABLE_NAME";
//		String sql = "select a.TABLE_NAME as TABLE_NAME, b.COMMENTS  from USER_TABLES a left join  USER_TAB_COMMENTS b on a.TABLE_NAME=b.TABLE_NAME where a.TABLE_NAME like upper('%order%')  ";
//		String sql = "select a.TABLE_NAME as TABLE_NAME, b.COMMENTS  from USER_TABLES a left join  USER_TAB_COMMENTS b on a.TABLE_NAME=b.TABLE_NAME  where b.TABLE_NAME not  LIKE upper('zyj%')  ";
//		String sql = "select a.TABLE_NAME as TABLE_NAME, b.COMMENTS  from USER_TABLES a left join  USER_TAB_COMMENTS b on a.TABLE_NAME=b.TABLE_NAME  where b.TABLE_NAME   LIKE upper('zyj%')  ";
//		String sql = "select a.TABLE_NAME, b.COMMENTS  from USER_TABLES a left join  USER_TAB_COMMENTS b on a.TABLE_NAME=b.TABLE_NAME where a.TABLE_NAME = 'CAS_CLASSES'";
        PreparedStatement pre = conn.prepareStatement(sql);// 实例化预编译语句
        ResultSet rs = pre.executeQuery();


        String submeterTables = "";
        String[] submeterTableArray = null;
        String submeterTablePrefixName = "";
        try {
            submeterTables = ReadProperties.getRequiredProperty("jdbc.submeterTables");
            submeterTableArray = submeterTables.split(",");

        } catch (Exception ex) {

        }


        try {
            submeterTablePrefixName = ReadProperties.getRequiredProperty("jdbc.submeterTablePrefixName");
        } catch (Exception ex) {

        }


        if (tables.size() == 0) {
            while (rs.next()) {
                if (tabException.containsKey(rs.getString("TABLE_NAME")))
                    continue;
                tables.add(createOracleTable(conn, rs));
            }


            //handle number and parent props
            String[] splits = null;
            String className = "I";
            String instanceName = "";
            for (Table table : tables) {
                boolean issubmeter = isSubmeterTable(submeterTableArray, table.getSqlName());
                table.setSubmeterTable(issubmeter);
                if (issubmeter) {
                    table.setSubmeterTablePrefixName(submeterTablePrefixName);
                    String subSqlName = table.getSqlName();
//					subSqlName = subSqlName.substring(subSqlName.indexOf("_")+1);
                    table.setSubSqlName(subSqlName);
                }
                table.setSeq(SeqGen.incr(SeqGen.MODEL));
                table.setNumber(NumGen.genNum(table.getSeq()));
                if (isStandard) {
                    table.setLongnumber(parentResMap.get(table.getParentResName()).getNumber() + "_" + table.getNumber());
                    table.setParentId(parentResMap.get(table.getParentResName()).getSeq());
                }
                splits = table.getSqlName().toLowerCase().split("_");
                int index = 1;
//				if(splits.length==1)
//					index = 0;
                for (int i = index; i < splits.length; i++) {
                    className += splits[i].replaceFirst(String.valueOf(splits[i].charAt(0)), String.valueOf(splits[i].charAt(0)).toUpperCase());
                    instanceName += splits[i];
                }

                table.setClassName(className);
                className = "I";
                table.setInstanceName(instanceName);
                instanceName = "";
            }
        }

        return tables;
    }

    private boolean isSubmeterTable(String[] submeterTableArray, String TableName) {

        if (submeterTableArray == null) {
            return false;
        }

        for (String submeterTable : submeterTableArray) {
            if (submeterTable.toUpperCase().equals(TableName.toUpperCase()))
                return true;
        }

        return false;
    }

    private Table createOracleTable(Connection conn, ResultSet rs) throws SQLException {
        String realTableName = null;
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            //String schemaName = rs.getString("TABLE_SCHEM") == null ? "" : rs.getString("TABLE_SCHEM");
            realTableName = rs.getString("TABLE_NAME");
            String remarks = rs.getString("COMMENTS");//"REMARKS");
            if (remarks != null && remarks.length() > 0) {
                String[] lines = remarks.split("\\n");
                if (lines.length != 2) {
                    remarks = "app|app管理|基础管理|CreateBaseDomain\n" +
                            "产品app";
                } else {
                    String[] comments = null;
                    comments = lines[0].split("\\|");
                    if (lines.length < 3) {
                        remarks = "app|app管理|基础管理|CreateBaseDomain\n" +
                                "产品app";
                    }

                }
            } else {
                remarks = "app|app管理|基础管理|CreateBaseDomain\n" +
                        "产品app";

            }

//			if(remarks == null && dbHelper.isOracleDataBase()) {
//				remarks = getOracleTableComments(realTableName);
//			}

            Table table = new Table();
            table.setSqlName(realTableName);
            table.setRemarks(remarks);

            ParentRes parentRes = null;
            if (remarks != null && remarks.length() > 0) {
                String[] comments = null;
                if (isStandard) {
                    String[] lines = remarks.split("\\n");
                    table.setDescription(lines[1]);
                    comments = lines[0].split("\\|");
                    table.setName(comments[0]);
//                    table.setResName(comments[1]);
//                    table.setParentResName(comments[2]);
//                    if (comments[3] != null) {
//                        table.setParentClassName(comments[3]);
//                    } else {
//                        table.setParentClassName("BaseDomain");
//                    }
                    if (comments[1] != null) {
                        table.setParentClassName(comments[3]);
                    } else {
                        table.setParentClassName("BaseDomain");
                    }
                } else {
                    table.setParentClassName("BaseDomain");
                }

//                if (comments != null && parentResMap.get(comments[2]) == null) {
//                    parentRes = new ParentRes();
//                    parentRes.setName(comments[2]);
//                    parentRes.setSeq(SeqGen.incr(SeqGen.MODEL));
//
//                    parentRes.setNumber(NumGen.genNum(parentRes.getSeq()));
//
//                    parentResMap.put(comments[2], parentRes);
//                }
            } else {
                throw new RuntimeException("table comments is not empty");
            }

//            table.setSeq(SeqGen.incr(SeqGen.MODEL));
//            String number = NumGen.genNum(table.getSeq());
//            table.setNumber(number);
//            if(parentRes != null) {
//                table.setLongnumber(parentRes.getNumber() + "_" + table.getNumber());
//            }

//			if ("SYNONYM".equals(tableType) && dbHelper.isOracleDataBase()) {
//				table.setOwnerSynonymName(getSynonymOwner(realTableName));
//			}

            retriveTableColumns(table);

            table.initExportedKeys(conn.getMetaData());
            table.initImportedKeys(conn.getMetaData());
            BeanHelper.copyProperties(table, TableOverrideValuesProvider.getTableOverrideValues(table.getSqlName()));
            return table;
        } catch (SQLException e) {
            throw new RuntimeException("create table object error,tableName:" + realTableName, e);
        }
    }

    private String getSynonymOwner(String synonymName) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ret = null;
        try {
            ps = getConnection().prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
            ps.setString(1, synonymName);
            ps.setString(2, getSchema());
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = rs.getString(1);
            } else {
                String databaseStructure = getDatabaseStructureInfo();
                throw new RuntimeException("Wow! Synonym " + synonymName + " not found. How can it happen? " + databaseStructure);
            }
        } catch (SQLException e) {
            String databaseStructure = getDatabaseStructureInfo();
            GLogger.error(e.getMessage(), e);
            throw new RuntimeException("Exception in getting synonym owner " + databaseStructure);
        } finally {
            dbHelper.close(rs, ps);
        }
        return ret;
    }

    private String getDatabaseStructureInfo() {
        ResultSet schemaRs = null;
        ResultSet catalogRs = null;
        String nl = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer(nl);
        // Let's give the user some feedback. The exception
        // is probably related to incorrect schema configuration.
        sb.append("Configured schema:").append(getSchema()).append(nl);
        sb.append("Configured catalog:").append(getCatalog()).append(nl);

        try {
            schemaRs = getMetaData().getSchemas();
            sb.append("Available schemas:").append(nl);
            while (schemaRs.next()) {
                sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
            }
        } catch (SQLException e2) {
            GLogger.warn("Couldn't get schemas", e2);
            sb.append("  ?? Couldn't get schemas ??").append(nl);
        } finally {
            dbHelper.close(schemaRs, null);
        }

        try {
            catalogRs = getMetaData().getCatalogs();
            sb.append("Available catalogs:").append(nl);
            while (catalogRs.next()) {
                sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
            }
        } catch (SQLException e2) {
            GLogger.warn("Couldn't get catalogs", e2);
            sb.append("  ?? Couldn't get catalogs ??").append(nl);
        } finally {
            dbHelper.close(catalogRs, null);
        }
        return sb.toString();
    }

    private DatabaseMetaData getMetaData() throws SQLException {
        return getConnection().getMetaData();
    }

    private void retriveTableColumns(Table table) throws SQLException {
        GLogger.trace("-------setColumns(" + table.getSqlName() + ")");

        List primaryKeys = getTablePrimaryKeys(table);
        table.setPrimaryKeyColumns(primaryKeys);

        // get the indices and unique columns
        List indices = new LinkedList();
        // maps index names to a list of columns in the index
        Map uniqueIndices = new HashMap();
        // maps column names to the index name.
        Map uniqueColumns = new HashMap();
        ResultSet indexRs = null;

        try {

            if (table.getOwnerSynonymName() != null) {
                indexRs = getMetaData().getIndexInfo(getCatalog(), table.getOwnerSynonymName(), table.getSqlName(), false, true);
            } else {
                indexRs = getMetaData().getIndexInfo(getCatalog(), getSchema(), table.getSqlName(), false, true);
            }
            while (indexRs.next()) {
                String columnName = indexRs.getString("COLUMN_NAME");
                if (columnName != null) {
                    GLogger.trace("index:" + columnName);
                    indices.add(columnName);
                }

                // now look for unique columns
                String indexName = indexRs.getString("INDEX_NAME");
                boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");

                if (!nonUnique && columnName != null && indexName != null) {
                    List l = (List) uniqueColumns.get(indexName);
                    if (l == null) {
                        l = new ArrayList();
                        uniqueColumns.put(indexName, l);
                    }
                    l.add(columnName);
                    uniqueIndices.put(columnName, indexName);
                    GLogger.trace("unique:" + columnName + " (" + indexName + ")");
                }
            }
        } catch (Throwable t) {
            // Bug #604761 Oracle getIndexInfo() needs major grants
            // http://sourceforge.net/tracker/index.php?func=detail&aid=604761&group_id=36044&atid=415990
        } finally {
            dbHelper.close(indexRs, null);
        }

        List columns = getTableColumns(table, primaryKeys, indices, uniqueIndices, uniqueColumns);

        for (Iterator i = columns.iterator(); i.hasNext(); ) {
            Column column = (Column) i.next();
            table.addColumn(column);
        }

        // In case none of the columns were primary keys, issue a warning.
        if (primaryKeys.size() == 0) {
            GLogger.warn("WARNING: The JDBC driver didn't report any primary key columns in " + table.getSqlName());
        }
    }

    private int getSqlTypeByName(String name, int sqlType) {
        if (name.toLowerCase().equals("nvarchar2"))
            return 12;
        return sqlType;
    }

    private boolean isdefaultTable(String tableName) {
        for (String already : alreadyTbls) {
            if (tableName.toLowerCase().endsWith(already)) {
                return true;
            }
        }
        return false;
    }

    private String getdefaultcolumn(String columnName) {
        for (String already : defaultColumns) {
            if (already.toLowerCase().equals(columnName)) {
                return already;
            }
        }
        return columnName;
    }

    private List getTableColumns(Table table, List primaryKeys, List indices, Map uniqueIndices, Map uniqueColumns) throws SQLException {
        // get the columns
        List columns = new LinkedList();
        ResultSet columnRs = getColumnsResultSet(table);

        while (columnRs.next()) {
            int sqlType = columnRs.getInt("DATA_TYPE");
            String sqlTypeName = columnRs.getString("TYPE_NAME");
            sqlType = getSqlTypeByName(sqlTypeName, sqlType);
            String columnName = columnRs.getString("COLUMN_NAME");
            String columnDefaultValue = columnRs.getString("COLUMN_DEF");

            String remarks = columnRs.getString("REMARKS");
            if (remarks == null && dbHelper.isOracleDataBase()) {
                remarks = getOracleColumnComments(table.getSqlName(), columnName);
            }
            if (remarks == null)
                remarks = "";

            // if columnNoNulls or columnNullableUnknown assume "not nullable"
            boolean isNullable = (DatabaseMetaData.columnNullable == columnRs.getInt("NULLABLE"));
            int size = columnRs.getInt("COLUMN_SIZE");
            int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");

            boolean isPk = primaryKeys.contains(columnName);
            boolean isIndexed = indices.contains(columnName);
            String uniqueIndex = (String) uniqueIndices.get(columnName);
            List columnsInUniqueIndex = null;
            if (uniqueIndex != null) {
                columnsInUniqueIndex = (List) uniqueColumns.get(uniqueIndex);
            }

            boolean isUnique = columnsInUniqueIndex != null && columnsInUniqueIndex.size() == 1;
            if (isUnique) {
                GLogger.trace("unique column:" + columnName);
            }
            if (JdbcConstants.ORACLE.equals(this.dbType)) {
                if (isdefaultTable(table.getSqlName())) {
                    columnName = getdefaultcolumn(columnName.toLowerCase());
                } else {
                    columnName = columnName.toLowerCase();
                }
            }
            Column column = new Column(
                    table,
                    sqlType,
                    sqlTypeName,
                    columnName,
                    size,
                    decimalDigits,
                    isPk,
                    isNullable,
                    isIndexed,
                    isUnique,
                    columnDefaultValue,
                    remarks);
            BeanHelper.copyProperties(column, TableOverrideValuesProvider.getColumnOverrideValues(table, column));
            if (JdbcConstants.ORACLE.equals(this.dbType)) {
                if (!isdefaultTable(table.getSqlName())) {
                    String normalJdbcJavaType = setJavaType(column, sqlType, size,
                            decimalDigits);
                    if (normalJdbcJavaType != null) {
                        String javaType = ReadProperties.getProperty("java_typemapping." + normalJdbcJavaType, normalJdbcJavaType).trim();

                        column.setJavaType(javaType);
                    }
                }
            }
            columns.add(column);
        }
        columnRs.close();
        return columns;
    }

    private String setJavaType(Column column, int sqlType, int size, int decimalDigits) {
        if (sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) {
            if (decimalDigits == 0) {
                if (size < 5) {
                    return "java.lang.Short";
                } else if (size < 10) {
                    return "java.lang.Integer";
                } else {
                    return "java.lang.Long";
                }
            } else if (decimalDigits == 2) {
                return "java.math.BigDecimal";
            } else {
                return "java.lang.Float";
            }
        }

        return null;
    }

    private ResultSet getColumnsResultSet(Table table) throws SQLException {
        ResultSet columnRs = null;
        if (table.getOwnerSynonymName() != null) {
            columnRs = getMetaData().getColumns(getCatalog(), table.getOwnerSynonymName(), table.getSqlName(), null);
        } else {
            columnRs = getMetaData().getColumns(getCatalog(), getSchema(), table.getSqlName(), null);
        }
        return columnRs;
    }

    private List<String> getTablePrimaryKeys(Table table) throws SQLException {
        // get the primary keys
        List primaryKeys = new LinkedList();
        ResultSet primaryKeyRs = null;
        if (table.getOwnerSynonymName() != null) {
            primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(), table.getOwnerSynonymName(), table.getSqlName());
        } else {
            primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(), getSchema(), table.getSqlName());
        }
        while (primaryKeyRs.next()) {
            String columnName = primaryKeyRs.getString("COLUMN_NAME");
            GLogger.trace("primary key:" + columnName);
            primaryKeys.add(columnName);
        }
        primaryKeyRs.close();
        return primaryKeys;
    }

    private String getOracleTableComments(String table) {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name='" + table + "'";
        return dbHelper.queryForString(sql);
    }

    private String getOracleColumnComments(String table, String column) {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name='" + table + "' AND column_name = '" + column + "'";
        return dbHelper.queryForString(sql);
    }

    public static class NotFoundTableException extends RuntimeException {
        private static final long serialVersionUID = 5976869128012158628L;

        public NotFoundTableException(String message) {
            super(message);
        }
    }

    /**
     * 得到表的自定义配置信息
     */
    public static class TableOverrideValuesProvider {

        private static Map getTableOverrideValues(String tableSqlName) {
            XMLHelper.NodeData nd = getTableConfigXmlNodeData(tableSqlName);
            if (nd == null) {
                return new HashMap();
            }
            return nd == null ? new HashMap() : nd.attributes;
        }

        private static Map getColumnOverrideValues(Table table, Column column) {
            XMLHelper.NodeData root = getTableConfigXmlNodeData(table.getSqlName());
            if (root != null) {
                for (XMLHelper.NodeData item : root.childs) {
                    if (item.nodeName.equals("column")) {
                        if (column.getSqlName().equalsIgnoreCase(item.attributes.get("sqlName"))) {
                            return item.attributes;
                        }
                    }
                }
            }
            return new HashMap();
        }

        private static XMLHelper.NodeData getTableConfigXmlNodeData(String tableSqlName) {
            XMLHelper.NodeData nd = getTableConfigXmlNodeData0(tableSqlName);
            if (nd == null) {
                nd = getTableConfigXmlNodeData0(tableSqlName.toLowerCase());
                if (nd == null) {
                    nd = getTableConfigXmlNodeData0(tableSqlName.toUpperCase());
                }
            }
            return nd;
        }

        private static XMLHelper.NodeData getTableConfigXmlNodeData0(String tableSqlName) {
            try {
                File file = FileHelper.getFileByClassLoader("generator_config/table/" + tableSqlName + ".xml");
                GLogger.trace("getTableConfigXml() load nodeData by tableSqlName:" + tableSqlName + ".xml");
                return new XMLHelper().parseXML(file);
            } catch (Exception e) {//ignore
                GLogger.trace("not found config xml for table:" + tableSqlName + ", exception:" + e);
                return null;
            }
        }
    }

    public static class DatabaseMetaDataUtils {
        public static boolean isOracleDataBase(DatabaseMetaData metadata) {
            try {
                boolean ret = false;
                ret = (metadata.getDatabaseProductName().toLowerCase()
                        .indexOf("oracle") != -1);
                return ret;
            } catch (SQLException s) {
                return false;
//				throw new RuntimeException(s);
            }
        }

        public static boolean isHsqlDataBase(DatabaseMetaData metadata) {
            try {
                boolean ret = false;
                ret = (metadata.getDatabaseProductName().toLowerCase()
                        .indexOf("hsql") != -1);
                return ret;
            } catch (SQLException s) {
                return false;
//				throw new RuntimeException(s);
            }
        }
    }

    class DbHelper {
        public void close(ResultSet rs, PreparedStatement ps, Statement... statements) {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                for (Statement s : statements) {
                    s.close();
                }
            } catch (Exception e) {
            }
        }

        public boolean isOracleDataBase() {
            try {
                return DatabaseMetaDataUtils.isOracleDataBase(getMetaData());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public String queryForString(String sql) {
            Statement s = null;
            ResultSet rs = null;
            try {
                s = getConnection().createStatement();
                rs = s.executeQuery(sql);
                if (rs.next()) {
                    return rs.getString(1);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                close(rs, null, s);
            }
        }
    }
}
