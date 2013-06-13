/**
 * <p>文件名:	EntityPKGenerator.java</p>
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
import net.ymate.platform.persistence.jdbc.extra.scaffold.generator.EntityModelGenerator.Attr;
import net.ymate.platform.persistence.jdbc.support.EntityMeta;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * EntityPKGenerator
 * </p>
 * <p>
 * 实体模型主键类文件生成器接口实现类；
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
public class EntityPKGenerator extends AbstractGenerator {

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
	public EntityPKGenerator(TableMeta tableMeta, String tablePrefix, boolean removePrefix) {
		this.tableMeta = tableMeta;
		String[] _prefixs = StringUtils.split(tablePrefix, '|');
		if (_prefixs != null) {
			for (String _prefix : _prefixs) {
				if (tableMeta.getTableName().startsWith(_prefix)) {
					String _tableName = null;
					if (removePrefix) {
						_tableName = tableMeta.getTableName().substring(_prefix.length());
					}
					this.modelName = EntityMeta.buildFieldNameToClassAttribute(_tableName);
					break;
				}
			}
		}
		if (StringUtils.isBlank(this.modelName)) {
			this.modelName = EntityMeta.buildFieldNameToClassAttribute(tableMeta.getTableName());
		}
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTargetFileName()
	 */
	public String getTargetFileName() {
		return ((String) this.getParamItem("packageName")).replace('.', '/') + "/model/" + this.modelName + "PK.java";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplateFilePath()
	 */
	public String getTemplateFilePath() {
		return TemplateFactory.TEMPLATE_ROOT_PATH +"/jdbc/model-pk.ftl";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplatePropMap()
	 */
	public Map<String, Object> getTemplatePropMap() {
		Map<String, Object> _propMap = new HashMap<String, Object>();
		_propMap.put("packageName", this.getParamItem("packageName"));
		_propMap.put("modelName", modelName);
		if (tableMeta.getPkSet().size() > 1) {
			List<Attr> _primaryKeyList = new ArrayList<Attr>();
			_propMap.put("primaryKeyList", _primaryKeyList);
			//
			for (String pkey : tableMeta.getPkSet()) {
				ColumnInfo _ci = tableMeta.getFieldMap().get(pkey);
				_primaryKeyList.add(new Attr(_ci.getColumnType(), StringUtils.uncapitalize(EntityMeta.buildFieldNameToClassAttribute(pkey.toLowerCase())), pkey, _ci.isAutoIncrement()));
			}
		}
		return _propMap;
	}

}
