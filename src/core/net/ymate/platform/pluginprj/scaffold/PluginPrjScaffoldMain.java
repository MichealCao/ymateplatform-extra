/**
 * <p>文件名:	PluginProjectScaffoldMain.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.pluginprj.scaffold;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.ymate.platform.commons.util.FileUtils;
import net.ymate.platform.extra.scaffold.GenerateFactory;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.pluginprj.scaffold.generator.BuildGenerator;
import net.ymate.platform.pluginprj.scaffold.generator.ClasspathGenerator;
import net.ymate.platform.pluginprj.scaffold.generator.PluginStartupClassGenerator;
import net.ymate.platform.pluginprj.scaffold.generator.ProjectGenerator;
import net.ymate.platform.pluginprj.scaffold.generator.YmatePluginGenerator;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * PluginProjectScaffoldMain
 * </p>
 * <p>
 * 插件Eclipse工程脚手架主文件；
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
 *          <td>2012-1-4下午2:04:10</td>
 *          </tr>
 *          </table>
 */
public class PluginPrjScaffoldMain {

	protected GenerateFactory __codeGenFactory;

	protected void createTomcatServConfFiles(String outputDir) {
		__codeGenFactory = GenerateFactory.buildFactory(outputDir);
		TemplateFactory.getTemplateHelper().getFreemarkerCfg().setOutputEncoding("UTF-8");
		System.out.println(TemplateFactory.getTemplateHelper().getFreemarkerCfg().getDefaultEncoding());
		System.out.println(TemplateFactory.getTemplateHelper().getFreemarkerCfg().getEncoding(Locale.CHINA));
		System.out.println(TemplateFactory.getTemplateHelper().getFreemarkerCfg().getOutputEncoding());
		//
		Map<String, Object> _params = new HashMap<String, Object>();
		__codeGenFactory.execute(new ProjectGenerator(), _params);
		__codeGenFactory.execute(new ClasspathGenerator(), _params);
		__codeGenFactory.execute(new BuildGenerator(), _params);
		__codeGenFactory.execute(new PluginStartupClassGenerator(), _params);
		__codeGenFactory.execute(new YmatePluginGenerator(), _params);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		__doShowVersionInfo();
		String _outpath = Config.getValue("output_path");
		if (StringUtils.isBlank(_outpath)) {
			System.err.println("ERROR:File output path is null.");
		} else {
			File _file = new File(_outpath);
			if (!_file.exists() || !_file.isDirectory() || !_file.canWrite()) {
				System.err.println("ERROR:File output path not exists or not directory or cannot write.");
			} else {
				String _plugin_project_name = Config.getValue("plugin_project_name");
				if (StringUtils.isBlank(_plugin_project_name)) {
					System.err.println("ERROR:plugin_project_name config is null.");
				} else {
					_file = new File(_file, _plugin_project_name);
					if (_file.exists()) {
						System.err.println("ERROR:plugin \"" + _plugin_project_name + "\" directory was exists.");
					} else {
						FileUtils.mkdirs(_file.getPath(), false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "bin", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "lib", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "resources", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "src", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "src" + File.separator + "conf", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "src" + File.separator + "core", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "src" + File.separator + "core/" + Config.getValue("plugin_package_name").replace('.', '/') + "/controller", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "src" + File.separator + "core/" + Config.getValue("plugin_package_name").replace('.', '/') + "/repository", false);
						PluginPrjScaffoldMain _main = new PluginPrjScaffoldMain();
						_main.createTomcatServConfFiles(_file.getPath());
						//
						System.out.println("INFO:plugin \"" + _plugin_project_name + "\" config successed.");
					}
				}
			}
		}
	}

	private static void __doShowVersionInfo() {
//		System.out.println("╭═══════════════════════════╮");
		System.out.println("===========================");
		System.out.println(" yMateScaffold_PluginEclipseProject V1");
		System.out.println("---------------------------------------");
		System.out.println(" Powered by suninformation@163.com");
		System.out.println("===========================");
//		System.out.println("╰═══════════════════════════╯");
	}

}
