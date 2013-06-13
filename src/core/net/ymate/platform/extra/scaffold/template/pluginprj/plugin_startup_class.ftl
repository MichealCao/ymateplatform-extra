/**
 * <p>文件名:	${plugin_startup_class?cap_first}.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package ${plugin_package_name};

import net.ymate.framework.web.plugin.AbstractWebPlugin;

/**
 * <p>
 * ${plugin_startup_class?cap_first}
 * </p>
 * <p>
 * Code Generator By yMateScaffold
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
 *          <td>${lastUpdateTime?string("yyyy-MM-dd a HH:mm:ss")}</td>
 *          </tr>
 *          </table>
 */
public class ${plugin_startup_class?cap_first} extends AbstractWebPlugin {

	public static final String PLUGIN_SIMPLE_NAME = "${plugin_simple_name}";

	/* (non-Javadoc)
	 * @see net.ymate.framework.web.plugin.IWebPlugin#getPluginSimpleName()
	 */
	public String getPluginSimpleName() {
		return PLUGIN_SIMPLE_NAME;
	}

}
