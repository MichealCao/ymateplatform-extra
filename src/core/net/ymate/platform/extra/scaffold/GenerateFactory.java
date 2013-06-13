/**
 * <p>文件名:	GenerateFactory.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.extra.scaffold;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.ymate.platform.commons.util.FileUtils;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;

/**
 * <p>
 * GenerateFactory
 * </p>
 * <p>
 * 代码生成器工厂类；
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
 *          <td>2011-10-26上午10:15:34</td>
 *          </tr>
 *          </table>
 */
public class GenerateFactory {

	/**
	 * 目标输出路径
	 */
	private File __targetDir;

	/**
	 * @param targetDir 目标输出路径
	 * @return 代码生成器工厂对象实例
	 */
	public static GenerateFactory buildFactory(String targetDir) {
		return new GenerateFactory(targetDir);
	}

	/**
	 * 构造器
	 * @param targetDir 目标输出路径
	 */
	private GenerateFactory(String targetDir) {
		this.__targetDir = new File(targetDir);
		if (this.__targetDir == null || !this.__targetDir.exists() || this.__targetDir.isFile()) {
			throw new RuntimeException("目标输出路径 \"" + targetDir + "\" 为空或不是目录.");
		}
	}

	/**
	 * 执行
	 * 
	 * @param gen 生成器接口实现类
	 * @param args 参数映射
	 */
	public void execute(IGenerator gen, Map<String, Object> args) {
			gen.initialize(args);
			File _targetFile = new File(FileUtils.fixSeparator(__targetDir.getAbsolutePath()), gen.getTargetFileName());
			FileUtils.mkdirs(_targetFile.getParent(), true);
			Map<String, Object> _propMap = new HashMap<String, Object>(gen.getTemplatePropMap());
			_propMap.put("lastUpdateTime", new Date());
			TemplateFactory.getTemplateHelper().build(gen.getTemplateFilePath(), _propMap, _targetFile);
	}

}
