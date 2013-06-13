/**
 * <p>文件名:	ServerXmlGenerator.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.tomcatserv.scaffold.generator;

import java.util.HashMap;
import java.util.Map;

import net.ymate.platform.extra.scaffold.AbstractGenerator;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.tomcatserv.scaffold.Config;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * ServerXmlGenerator
 * </p>
 * <p>
 * server.xml配置文件生成器；
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
 *          <td>2012-1-4下午5:52:29</td>
 *          </tr>
 *          </table>
 */
public class ServerXmlGenerator extends AbstractGenerator {

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplateFilePath()
	 */
	public String getTemplateFilePath() {
		return TemplateFactory.TEMPLATE_ROOT_PATH +"/tomcatserv/server-xml.ftl";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTargetFileName()
	 */
	public String getTargetFileName() {
		return "conf/server.xml";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplatePropMap()
	 */
	public Map<String, Object> getTemplatePropMap() {
		Map<String, Object> _propMap = new HashMap<String, Object>();
		_propMap.put("server_port", Config.getValue("server_port"));
		_propMap.put("connector_port", Config.getValue("connector_port"));
		_propMap.put("redirect_port", Config.getValue("redirect_port"));
		_propMap.put("ajp_port", Config.getValue("ajp_port"));
		_propMap.put("host_name", StringUtils.defaultIfEmpty((String) Config.getValue("host_name"), "localhost"));
		_propMap.put("host_alias", StringUtils.defaultIfEmpty((String) Config.getValue("host_alias"), ""));
		return _propMap;
	}

}
