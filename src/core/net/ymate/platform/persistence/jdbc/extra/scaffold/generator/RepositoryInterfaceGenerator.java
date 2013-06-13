/**
 * <p>文件名:	RepositoryInterfaceGenerator.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.persistence.jdbc.extra.scaffold.generator;

import java.util.HashMap;
import java.util.Map;

import net.ymate.platform.extra.scaffold.AbstractGenerator;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.persistence.jdbc.support.EntityMeta;

/**
 * <p>
 * RepositoryInterfaceGenerator
 * </p>
 * <p>
 * 存储器接口定义类文件生成器接口实现类；
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
 *          <td>2011-10-27下午03:29:34</td>
 *          </tr>
 *          </table>
 */
public class RepositoryInterfaceGenerator extends AbstractGenerator {

	/**
	 * 存储器名称
	 */
	private String repositoryName;

	/** 
	 * 构造器
	 * @param repositoryName
	 */
	public RepositoryInterfaceGenerator(String repositoryName) {
		this.repositoryName = EntityMeta.buildFieldNameToClassAttribute(repositoryName);
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTargetFileName()
	 */
	public String getTargetFileName() {
		return ((String) this.getParamItem("packageName")).replace('.', '/') + "/repository/I" + repositoryName + "Repository.java";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplateFilePath()
	 */
	public String getTemplateFilePath() {
		return TemplateFactory.TEMPLATE_ROOT_PATH +"/jdbc/repository-interface.ftl";
	}

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#getTemplatePropMap()
	 */
	public Map<String, Object> getTemplatePropMap() {
		Map<String, Object> _propMap = new HashMap<String, Object>();
		_propMap.put("packageName", this.getParamItem("packageName"));
		_propMap.put("repositoryName", repositoryName);
		return _propMap;
	}

}
