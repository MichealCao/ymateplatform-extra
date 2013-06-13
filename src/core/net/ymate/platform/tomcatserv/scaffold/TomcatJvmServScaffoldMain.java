/**
 * <p>文件名:	PluginPrjScaffoldMain.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.tomcatserv.scaffold;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.ymate.platform.commons.util.FileUtils;
import net.ymate.platform.extra.scaffold.GenerateFactory;
import net.ymate.platform.extra.scaffold.template.TemplateFactory;
import net.ymate.platform.tomcatserv.scaffold.generator.CatalinaPolicyGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.CatalinaPropertiesGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.ContextXmlGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.InstallCmdGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.LoggingPropertiesGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.ManagerCmdGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.ServerXmlGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.ShutdownCmdGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.StartupCmdGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.TomcatUsersXmlGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.UninstallCmdGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.VhostConfGenerator;
import net.ymate.platform.tomcatserv.scaffold.generator.WebXmlGenerator;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * PluginPrjScaffoldMain
 * </p>
 * <p>
 * Tomcat独立JVM服务配置生成器；
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
public class TomcatJvmServScaffoldMain {

	protected GenerateFactory __codeGenFactory;

	protected void createTomcatServConfFiles(String outputDir) {
		__codeGenFactory = GenerateFactory.buildFactory(outputDir);
		TemplateFactory.getTemplateHelper().getFreemarkerCfg().setOutputEncoding("GB2312");
		System.out.println(TemplateFactory.getTemplateHelper().getFreemarkerCfg().getDefaultEncoding());
		System.out.println(TemplateFactory.getTemplateHelper().getFreemarkerCfg().getEncoding(Locale.CHINA));
		System.out.println(TemplateFactory.getTemplateHelper().getFreemarkerCfg().getOutputEncoding());
		//
		Map<String, Object> _params = new HashMap<String, Object>();
		__codeGenFactory.execute(new CatalinaPolicyGenerator(), _params);
		__codeGenFactory.execute(new CatalinaPropertiesGenerator(), _params);
		__codeGenFactory.execute(new ContextXmlGenerator(), _params);
		__codeGenFactory.execute(new LoggingPropertiesGenerator(), _params);
		__codeGenFactory.execute(new ServerXmlGenerator(), _params);
		__codeGenFactory.execute(new TomcatUsersXmlGenerator(), _params);
		__codeGenFactory.execute(new WebXmlGenerator(), _params);
		__codeGenFactory.execute(new InstallCmdGenerator(), _params);
		__codeGenFactory.execute(new ManagerCmdGenerator(), _params);
		__codeGenFactory.execute(new UninstallCmdGenerator(), _params);
		__codeGenFactory.execute(new ShutdownCmdGenerator(), _params);
		__codeGenFactory.execute(new StartupCmdGenerator(), _params);
		__codeGenFactory.execute(new VhostConfGenerator(), _params);
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
				String _websiteName = Config.getValue("website_name");
				if (StringUtils.isBlank(_websiteName)) {
					System.err.println("ERROR:website_name config is null.");
				} else {
					_file = new File(_file, _websiteName);
					if (_file.exists()) {
						System.err.println("ERROR:website_name \"" + _websiteName + "\" directory was exists.");
					} else {
						FileUtils.mkdirs(_file.getPath(), false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "logs", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "temp", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "webapps", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "webapps" + File.separator + "ROOT", false);
						FileUtils.mkdirs(_file.getPath() + File.separator + "work", false);
						TomcatJvmServScaffoldMain _main = new TomcatJvmServScaffoldMain();
						_main.createTomcatServConfFiles(_file.getPath());
						//
						System.out.println("INFO:website \"" + _websiteName + "\" config successed.");
					}
				}
			}
		}
	}

	private static void __doShowVersionInfo() {
//		System.out.println("╭═══════════════════════════╮");
		System.out.println("===========================");
		System.out.println(" yMateScaffold_TomcatJvmServ V1");
		System.out.println("---------------------------------------");
		System.out.println(" Powered by suninformation@163.com");
		System.out.println("===========================");
//		System.out.println("╰═══════════════════════════╯");
	}

}
