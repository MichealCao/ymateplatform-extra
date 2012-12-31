/**
 * <p>文件名:	DBUtils.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.persistence.jdbc.extra.scaffold;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.operator.impl.ArrayResultSetHandler;
import net.ymate.platform.persistence.jdbc.support.ResultSetHelper;

/**
 * <p>
 * DBUtils
 * </p>
 * <p>
 * 数据库工具类；
 * </p>
 * 
 * @author 刘镇(suninformation@163.com)
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th><th width="100px">动作</th><th
 *          width="100px">修改人</th><th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>刘镇</td>
 *          <td>2011-10-26上午12:44:56</td>
 *          </tr>
 *          </table>
 */
public class DBUtils {

	/**
	 * 构造器
	 */
	private DBUtils() {
	}

	/**
	 * @return 获取数据库表名称集合
	 */
	public static List<String> getTableNames(String dbType) {
		List<String> _tableNames = new ArrayList<String>();
		ISession _session = null;
		try {
			_session = JDBC.openSession();
			String _sql = null;
			if ("mysql".equalsIgnoreCase(dbType)) {
				_sql = "show tables";
			} else if ("oracle".equalsIgnoreCase(dbType)) {
				_sql = "select t.table_name from user_tables t";
			} else if ("mssql".equalsIgnoreCase(dbType)) {
				_sql = "select name from sysobjects where xtype='U'";
			} else {
				throw new Error("当前数据库类型 \"" + dbType + "\" 不支持");
			}
			List<Object[]> _results = _session.findAll(_sql, new ArrayResultSetHandler());
			ResultSetHelper _helper = ResultSetHelper.bind(_results);
			for (int idx = 0; idx < _helper.getRowCount(); idx++) {
				_helper.move(idx);
				_tableNames.add(_helper.getAsString(0));
			}
		} catch (Exception e) {
			throw new Error(RuntimeUtils.unwrapThrow(e));
		} finally {
			if (_session != null) _session.close();
		}
		return _tableNames;
	}

	/**
	 * @param tableName 表名称
	 * @return 获取表元数据描述对象
	 */
	public static TableMeta getTableMeta(String javaDataType, String dbType, String dbName, String dbUserName, String tableName) {
		IConnectionHolder conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, ColumnInfo> tbFields = new LinkedHashMap<String, ColumnInfo>();
		List<String> pkFields = new LinkedList<String>();
		TableMeta _meta = new TableMeta(tableName, pkFields, tbFields);
		try {
			conn = JDBC.getConnectionHolder();
			rs = conn.getConnection().getMetaData().getPrimaryKeys(dbName, dbType.equalsIgnoreCase("oracle") ? dbUserName.toUpperCase() : dbUserName, tableName);
			if (rs == null) {
				System.err.println("Error: PrimaryKey ResultSet Is Null");
			}
			while (rs.next()) {
				pkFields.add(rs.getString(4).toLowerCase());
			}
			stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery("select * from " + tableName);
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if (pkFields.size() == 0) {
					if ((rsmd.getColumnName(i).toLowerCase()).indexOf("id") >= 0) {
						pkFields.add(rsmd.getColumnName(i).toLowerCase());
					} else {
						if (i == rsmd.getColumnCount()) {
							System.err.println("Error: No Primarykey Defined");
						}
					}
				}
				if (javaDataType.equals("0")) {
					tbFields.put(rsmd.getColumnName(i).toLowerCase(), new ColumnInfo(rsmd.getColumnName(i).toLowerCase(), compressType(rsmd.getColumnClassName(i)), rsmd.isAutoIncrement(i)));
				} else if (javaDataType.equals("1")) {
					System.out.println("ColumnTypeName:" + rsmd.getColumnTypeName(i) + " ColumnClassName:" + rsmd.getColumnClassName(i) + " ColumnType:" + rsmd.getColumnType(i));
					if (rsmd.getColumnType(i) == Types.FLOAT || rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.NUMERIC || rsmd.getColumnType(i) == Types.DECIMAL) {
						if (rsmd.getScale(i) > 0) {
							if (rsmd.getPrecision(i) >= 18) {
								tbFields.put(rsmd.getColumnName(i).toLowerCase(), new ColumnInfo(rsmd.getColumnName(i).toLowerCase(), "Double", rsmd.isAutoIncrement(i)));
							} else {
								tbFields.put(rsmd.getColumnName(i).toLowerCase(), new ColumnInfo(rsmd.getColumnName(i).toLowerCase(), "Float", rsmd.isAutoIncrement(i)));
							}
						} else {
							if (rsmd.getPrecision(i) >= 18) {
								tbFields.put(rsmd.getColumnName(i).toLowerCase(), new ColumnInfo(rsmd.getColumnName(i).toLowerCase(), "Long", rsmd.isAutoIncrement(i)));
							} else {
								tbFields.put(rsmd.getColumnName(i).toLowerCase(), new ColumnInfo(rsmd.getColumnName(i).toLowerCase(), "Integer", rsmd.isAutoIncrement(i)));
							}
						}
					} else {
						tbFields.put(rsmd.getColumnName(i).toLowerCase(), new ColumnInfo(rsmd.getColumnName(i).toLowerCase(), Type.getInstance(dbType).getValue(rsmd.getColumnTypeName(i)), rsmd.isAutoIncrement(i)));
					}
				}
			}
			System.err.println("TABLE_NAME: " + _meta.getTableName() + " ---------------->>");
			System.err.println("COLUMN_NAME\tCOLUMN_TYPE\tIS_AUTOINCREMENT");
			for (ColumnInfo _cInfo : tbFields.values()) {
				System.err.println(_cInfo.getColumnName() + "\t" + _cInfo.getColumnType() + "\t" + _cInfo.isAutoIncrement());
			}
		} catch (Exception e) {
			throw new Error(RuntimeUtils.unwrapThrow(e));
		} finally {
			conn.release();
			stmt = null;
			rs = null;
		}
		return _meta;
	}

	public static String compressType(String javaType) {
		return javaType.substring(javaType.lastIndexOf(".") + 1);
	}

	/**
	 * <p>
	 * TableMeta
	 * </p>
	 * <p>
	 * 数据表元数据描述类；
	 * </p>
	 * 
	 * @author 刘镇(suninformation@163.com)
	 * @version 0.0.0
	 *          <table style="border:1px solid gray;">
	 *          <tr>
	 *          <th width="100px">版本号</th><th width="100px">动作</th><th
	 *          width="100px">修改人</th><th width="100px">修改时间</th>
	 *          </tr>
	 *          <!-- 以 Table 方式书写修改历史 -->
	 *          <tr>
	 *          <td>0.0.0</td>
	 *          <td>创建类</td>
	 *          <td>刘镇</td>
	 *          <td>2011-10-26上午01:33:57</td>
	 *          </tr>
	 *          </table>
	 */
	public static class TableMeta {
		private String tableName;
		private List<String> pkSet;
		private Map<String, ColumnInfo> fieldMap;

		public TableMeta(String tableName, List<String> pkSet, Map<String, ColumnInfo> fieldMap) {
			this.tableName = tableName;
			this.pkSet = pkSet;
			this.fieldMap = fieldMap;
		}

		public String getTableName() {
			return tableName;
		}

		public List<String> getPkSet() {
			return pkSet;
		}

		public Map<String, ColumnInfo> getFieldMap() {
			return fieldMap;
		}
	}

	/**
	 * <p>
	 * ColumnInfo
	 * </p>
	 * <p>
	 * 表字段信息类；
	 * </p>
	 * 
	 * @author 刘镇(suninformation@163.com)
	 * @version 0.0.0
	 *          <table style="border:1px solid gray;">
	 *          <tr>
	 *          <th width="100px">版本号</th><th width="100px">动作</th><th
	 *          width="100px">修改人</th><th width="100px">修改时间</th>
	 *          </tr>
	 *          <!-- 以 Table 方式书写修改历史 -->
	 *          <tr>
	 *          <td>0.0.0</td>
	 *          <td>创建类</td>
	 *          <td>刘镇</td>
	 *          <td>2011-10-8下午11:19:57</td>
	 *          </tr>
	 *          </table>
	 */
	public static class ColumnInfo {
	
		private String columnName;
		private String columnType;
		private boolean autoIncrement;

		/**
		 * 构造器
		 */
		public ColumnInfo() {
		}

		/**
		 * 构造器
		 * @param columnName
		 * @param columnType
		 * @param autoIncrement
		 */
		public ColumnInfo(String columnName, String columnType, boolean autoIncrement) {
			this.columnName = columnName;
			this.autoIncrement = autoIncrement;
			this.columnType = columnType;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public boolean isAutoIncrement() {
			return autoIncrement;
		}

		public void setAutoIncrement(boolean autoIncrement) {
			this.autoIncrement = autoIncrement;
		}

		public String getColumnType() {
			return columnType;
		}

		public void setColumnType(String columnType) {
			this.columnType = columnType;
		}

	}

}
