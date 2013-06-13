/**
 * <p>文件名:	JdbcScaffoldMain.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.persistence.jdbc.extra.scaffold;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.extra.scaffold.GenerateFactory;
import net.ymate.platform.module.base.YMP;
import net.ymate.platform.persistence.jdbc.extra.scaffold.DBUtils.TableMeta;
import net.ymate.platform.persistence.jdbc.extra.scaffold.generator.BaseEntityGenerator;
import net.ymate.platform.persistence.jdbc.extra.scaffold.generator.EntityModelGenerator;
import net.ymate.platform.persistence.jdbc.extra.scaffold.generator.EntityPKGenerator;
import net.ymate.platform.persistence.jdbc.extra.scaffold.generator.RepositoryImplGenerator;
import net.ymate.platform.persistence.jdbc.extra.scaffold.generator.RepositoryInterfaceGenerator;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * JdbcScaffoldMain
 * </p>
 * <p>
 * JDBC持久层脚手架主文件；
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
 *          <td>2011-10-26下午02:34:26</td>
 *          </tr>
 *          </table>
 */
public class JdbcScaffoldMain {

	protected GenerateFactory __codeGenFactory = GenerateFactory.buildFactory(Config.getValue("java.out_path"));

	/**
	 * 根据数据库表定义创建实体类文件
	 */
	protected void createEntityClassFiles() {
		Map<String, Object> _params = new HashMap<String, Object>();
		_params.put("packageName", Config.getValue("java.package"));
		//
		__codeGenFactory.execute(new BaseEntityGenerator(), _params);
		
		List<String> _tableList = null;
		if (StringUtils.isNotBlank(Config.getValue("db.table_gen_list"))) {
			_tableList = Arrays.asList(StringUtils.split(Config.getValue("db.table_gen_list"), "|"));
		} else {
			_tableList = DBUtils.getTableNames(Config.getValue("db.type"));
		}
		List<String> _tableExcludeList = Arrays.asList(StringUtils.split(StringUtils.defaultIfEmpty(Config.getValue("db.table_exclude_list"), "").toLowerCase(), "|"));
		for (String _table : _tableList) {
			// 判断黑名单
			if (_tableExcludeList != null && !_tableExcludeList.isEmpty() && _tableExcludeList.contains(_table.toLowerCase())) {
				continue;
			}
			boolean  _removePrefix = new BlurObject(Config.getValue("db.remove_table_prefix")).toBooleanValue();
			TableMeta _tableMeta = DBUtils.getTableMeta(StringUtils.defaultIfEmpty(Config.getValue("java.data_type"), "0"), Config.getValue("db.type"), Config.getValue("db.name"), Config.getValue("db.username"), _table);
			__codeGenFactory.execute(new EntityModelGenerator(_tableMeta, Config.getValue("db.table_prefix"), _removePrefix), _params);
			if (_tableMeta.getPkSet().size() > 1) {
				__codeGenFactory.execute(new EntityPKGenerator(_tableMeta, Config.getValue("db.table_prefix"), _removePrefix), _params);
			}
		}
	}

	/**
	 * 根据配置文件中的 '预生成的存储器对象名称列表' 生成存储器类文件
	 */
	protected void createRepositoryClassFiles() {
		Map<String, Object> _params = new HashMap<String, Object>();
		_params.put("packageName", Config.getValue("java.package"));

		List<String> _tableList = null;
		if (StringUtils.isNotBlank(Config.getValue("repository.name_list"))) {
			_tableList = Arrays.asList(StringUtils.split(Config.getValue("repository.name_list"), "|"));
			for (String _name : _tableList) {
				__codeGenFactory.execute(new RepositoryInterfaceGenerator(_name), _params);
				__codeGenFactory.execute(new RepositoryImplGenerator(_name), _params);
			}
		}
	}

	/**
	 * Starter
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		YMP.initialize();
		JdbcScaffoldMain _scaffold = new JdbcScaffoldMain();
		_scaffold.createEntityClassFiles();
		_scaffold.createRepositoryClassFiles();
		//
		System.err.println("JdbcScaffoldMain ---------------->> [finished]");
	}

}
