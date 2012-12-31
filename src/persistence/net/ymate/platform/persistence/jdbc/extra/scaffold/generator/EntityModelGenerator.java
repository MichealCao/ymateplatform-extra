/**
 * <p>文件名:	EntityModelGenerator.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.persistence.jdbc.extra.scaffold.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ymate.platform.extra.scaffold.AbstractGenerator;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.persistence.jdbc.extra.scaffold.DBUtils.ColumnInfo;
import net.ymate.platform.persistence.jdbc.extra.scaffold.DBUtils.TableMeta;
import net.ymate.platform.persistence.jdbc.support.EntityMeta;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * EntityModelGenerator
 * </p>
 * <p>
 * 实体模型类文件生成器接口实现类；
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
 *          <td>2011-10-26下午03:11:45</td>
 *          </tr>
 *          </table>
 */
public class EntityModelGenerator extends AbstractGenerator {

	/**
	 * 数据表名称
	 */
	private String tableName;

	/**
	 * 类名称
	 */
	private String modelName;

	/**
	 * 数据表元数据描述对象
	 */
	private TableMeta tableMeta;

	/**
	 * 构造器
	 * 
	 * @param tableName 数据表名称
	 * @param tablePrefix 数据表名称前缀
	 */
	public EntityModelGenerator(TableMeta tableMeta, String tablePrefix, boolean removePrefix) {
		this.tableMeta = tableMeta;
		this.tableName = tableMeta.getTableName();
		String[] _prefixs = StringUtils.split(tablePrefix, '|');
		if (_prefixs != null) {
			for (String _prefix : _prefixs) {
				if (tableName.startsWith(_prefix)) {
					if (removePrefix) {
						tableName = tableName.substring(_prefix.length());
					}
					modelName = EntityMeta.buildFieldNameToClassAttribute(tableName);
					break;
				}
			}
		}
		if (StringUtils.isBlank(this.modelName)) {
			this.modelName = EntityMeta.buildFieldNameToClassAttribute(tableName);
		}
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTargetFileName()
	 */
	public String getTargetFileName() {
		return ((String) this.getParamItem("packageName")).replace('.', '/') + "/model/" + this.modelName + "Model.java";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplateFilePath()
	 */
	public String getTemplateFilePath() {
		return TemplateFactory.TEMPLATE_ROOT_PATH +"/jdbc/model-entity.ftl";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplatePropMap()
	 */
	public Map<String, Object> getTemplatePropMap() {
		Map<String, Object> _propMap = new HashMap<String, Object>();
		_propMap.put("packageName", this.getParamItem("packageName"));
		_propMap.put("tableName", tableName);
		_propMap.put("modelName", modelName);
		List<Attr> _fieldList = new ArrayList<Attr>();
		List<Attr> _allFieldList = new ArrayList<Attr>(); // 用于生成字段名称常量
		if (tableMeta.getPkSet().size() > 1) {
			_propMap.put("primaryKeyType", modelName + "PK");
			_propMap.put("primaryKeyName", StringUtils.uncapitalize((String) _propMap.get("primaryKeyType")));
			List<Attr> _primaryKeyList = new ArrayList<Attr>();
			_propMap.put("primaryKeyList", _primaryKeyList);
			_fieldList.add(new Attr((String) _propMap.get("primaryKeyType"), (String) _propMap.get("primaryKeyName"), null, false));
			//
			for (String pkey : tableMeta.getPkSet()) {
				ColumnInfo _ci = tableMeta.getFieldMap().get(pkey);
				_primaryKeyList.add(new Attr(_ci.getColumnType(), StringUtils.uncapitalize(EntityMeta.buildFieldNameToClassAttribute(pkey.toLowerCase())), pkey, _ci.isAutoIncrement()));
				_allFieldList.add(new Attr("String", _ci.getColumnName().toUpperCase(), _ci.getColumnName(), false));
			}
			for (String key : tableMeta.getFieldMap().keySet()) {
				if (tableMeta.getPkSet().contains(key)) {
					continue;
				}
				ColumnInfo _ci = tableMeta.getFieldMap().get(key);
				_fieldList.add(new Attr(_ci.getColumnType(), StringUtils.uncapitalize(EntityMeta.buildFieldNameToClassAttribute(key.toLowerCase())), key, _ci.isAutoIncrement()));
				_allFieldList.add(new Attr("String", _ci.getColumnName().toUpperCase(), _ci.getColumnName(), false));
			}
		} else {
			_propMap.put("primaryKeyType", tableMeta.getFieldMap().get(tableMeta.getPkSet().get(0)).getColumnType());
			_propMap.put("primaryKeyName", StringUtils.uncapitalize(EntityMeta.buildFieldNameToClassAttribute(tableMeta.getPkSet().get(0))));
			for (String key : tableMeta.getFieldMap().keySet()) {
				ColumnInfo _ci = tableMeta.getFieldMap().get(key);
				_fieldList.add(new Attr(_ci.getColumnType(), StringUtils.uncapitalize(EntityMeta.buildFieldNameToClassAttribute(key.toLowerCase())), key, _ci.isAutoIncrement()));
				_allFieldList.add(new Attr("String", _ci.getColumnName().toUpperCase(), _ci.getColumnName(), false));
			}
		}
		_propMap.put("fieldList", _fieldList);
		_propMap.put("allFieldList", _allFieldList);
		return _propMap;
	}

	public static class Attr {
		String varType;
		String varName;
		String columnName;
		boolean autoIncrement;
		
		public Attr(String varType, String varName, String columnName, boolean autoIncrement) {
			this.varName = varName;
			this.varType = varType;
			this.columnName = columnName;
			this.autoIncrement = autoIncrement;
		}
		/**
		 * @return the varType
		 */
		public String getVarType() {
			return varType;
		}
		/**
		 * @param varType the varType to set
		 */
		public void setVarType(String varType) {
			this.varType = varType;
		}
		/**
		 * @return the varName
		 */
		public String getVarName() {
			return varName;
		}
		/**
		 * @param varName the varName to set
		 */
		public void setVarName(String varName) {
			this.varName = varName;
		}

		/**
		 * @return the columnName
		 */
		public String getColumnName() {
			return columnName;
		}
		/**
		 * @param columnName the columnName to set
		 */
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
	
		/**
		 * @return the autoIncrement
		 */
		public boolean isAutoIncrement() {
			return autoIncrement;
		}
		/**
		 * @param autoIncrement the autoIncrement to set
		 */
		public void setAutoIncrement(boolean autoIncrement) {
			this.autoIncrement = autoIncrement;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.getVarName();
		}

	}

}
