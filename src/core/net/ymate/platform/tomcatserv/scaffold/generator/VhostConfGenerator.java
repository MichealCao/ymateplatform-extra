/**
 * <p>文件名:	VhostConfGenerator.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.tomcatserv.scaffold.generator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.ymate.platform.extra.scaffold.AbstractGenerator;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.tomcatserv.scaffold.Config;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * VhostConfGenerator
 * </p>
 * <p>
 * vhost-conf.txt配置描述文件生成器；
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
 *          <td>2012-1-4下午6:33:54</td>
 *          </tr>
 *          </table>
 */
public class VhostConfGenerator extends AbstractGenerator {

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplateFilePath()
	 */
	public String getTemplateFilePath() {
		return TemplateFactory.TEMPLATE_ROOT_PATH +"/tomcatserv/vhost-conf.ftl";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTargetFileName()
	 */
	public String getTargetFileName() {
		return "vhost-conf.txt";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplatePropMap()
	 */
	public Map<String, Object> getTemplatePropMap() {
		Map<String, Object> _propMap = new HashMap<String, Object>();
		_propMap.put("service_name", Config.getValue("service_name"));
		_propMap.put("host_name", Config.getValue("host_name"));
		_propMap.put("host_alias", StringUtils.defaultIfEmpty((String) Config.getValue("host_alias"), ""));
		_propMap.put("ajp_port", Config.getValue("ajp_port"));
		_propMap.put("ajp_host", StringUtils.defaultIfEmpty(Config.getValue("ajp_host"), "localhost"));
		_propMap.put("website_root_path", Config.getValue("output_path") + File.separator + Config.getValue("website_name") + File.separator + "webapps" + File.separator + "ROOT");
		return _propMap;
	}

}
