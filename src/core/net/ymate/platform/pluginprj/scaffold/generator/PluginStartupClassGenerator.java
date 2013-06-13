/**
 * <p>文件名:	PluginStartupClassGenerator.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	ymateplatform-extra</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.pluginprj.scaffold.generator;

import java.util.HashMap;
import java.util.Map;

import net.ymate.platform.extra.scaffold.AbstractGenerator;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.pluginprj.scaffold.Config;

/**
 * <p>
 * PluginStartupClassGenerator
 * </p>
 * <p>
 * 
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
 *          <td>2013-6-9下午7:12:43</td>
 *          </tr>
 *          </table>
 */
public class PluginStartupClassGenerator extends AbstractGenerator {

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplateFilePath()
	 */
	public String getTemplateFilePath() {
		return TemplateFactory.TEMPLATE_ROOT_PATH +"/pluginprj/plugin_startup_class.ftl";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTargetFileName()
	 */
	public String getTargetFileName() {
		return "src/core/" + Config.getValue("plugin_package_name").replace('.', '/') + "/" + Config.getValue("plugin_startup_class");
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplatePropMap()
	 */
	public Map<String, Object> getTemplatePropMap() {
		Map<String, Object> _propMap = new HashMap<String, Object>();
		_propMap.put("plugin_package_name", Config.getValue("plugin_package_name"));
		_propMap.put("plugin_startup_class", Config.getValue("plugin_startup_class"));
		_propMap.put("plugin_simple_name", Config.getValue("plugin_simple_name"));
		return _propMap;
	}

}
