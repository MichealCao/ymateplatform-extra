/**
 * <p>文件名:	TemplateFactory.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.extra.scaffold.template;

import net.ymate.platform.extra.util.FreeMarkerHelper;


/**
 * <p>
 * TemplateFactory
 * </p>
 * <p>
 * 模板工厂类；
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
 *          <td>2011-4-14下午03:00:06</td>
 *          </tr>
 *          </table>
 */
public class TemplateFactory {

	/**
	 * 模板根路径
	 */
	public static final String TEMPLATE_ROOT_PATH = TemplateFactory.class.getPackage().getName().replace(".", "/");

	/**
	 * 缓存模板助手对象
	 */
	private static FreeMarkerHelper __instanceObj = null;

	/**
	 * 构造器
	 */
	private TemplateFactory() {
	}

	/**
	 * 获取应用模块模板对象
	 * 
	 * @return
	 */
	public static FreeMarkerHelper getTemplateHelper() {
		if (__instanceObj == null) {
			__instanceObj = new FreeMarkerHelper(TemplateFactory.class);
		}
		return __instanceObj;
	}

}
