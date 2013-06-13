/**
 * <p>文件名:	Config.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.pluginprj.scaffold;

import java.io.IOException;

import net.ymate.platform.commons.util.ResourceUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.extra.util.PropertiesHelper;

/**
 * <p>
 * Config
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
 *          <td>2011-10-26上午12:18:34</td>
 *          </tr>
 *          </table>
 */
public class Config {

	private static PropertiesHelper _cfgs;

	static {
		try {
			_cfgs = new PropertiesHelper(ResourceUtils.getResource("pluginprj_conf.properties", Config.class));
		} catch (IOException e) {
			throw new Error(RuntimeUtils.unwrapThrow(e));
		}
	}

	public static String getValue(String key) {
		return _cfgs.getValue(key);
	}
		
}
