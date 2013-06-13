/**
 * <p>文件名:	FreeMarkerHelper.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Commons</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.extra.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import net.ymate.platform.commons.logger.Logs;

import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <p>
 * FreeMarkerHelper
 * </p>
 * <p>
 * FreeMarker模板生成工具帮助类；
 * </p>
 * 
 * @author 刘镇(suninformation@163.com)
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th>
 *          <th width="100px">动作</th>
 *          <th * width="100px">修改人</th>
 *          <th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>刘镇</td>
 *          <td>2010-8-25下午06:00:01</td>
 *          </tr>
 *          </table>
 */
public class FreeMarkerHelper {

	private Configuration freemarkerCfg;

	private File templatePath;

	/**
	 * 构造器
	 * 
	 * @param loaderClass
	 */
	public FreeMarkerHelper(Class<?> loaderClass) {
		// 初始化模板配置对象
		freemarkerCfg = new Configuration();
		// 设置FreeMarker的参数
		freemarkerCfg.setClassForTemplateLoading(loaderClass, "/");
//		freemarkerCfg.setLocalizedLookup(false);
		freemarkerCfg.setObjectWrapper(new DefaultObjectWrapper());
		freemarkerCfg.setDefaultEncoding("UTF-8");
	}

	/**
	 * 构造器
	 * 
	 * @param templatePath 模板文件加载路径
	 * @throws IOException
	 */
	public FreeMarkerHelper(File templatePath) throws IOException {
		// 初始化模板配置对象
		this.templatePath = templatePath;
		freemarkerCfg = new Configuration();
		// 设置FreeMarker的参数
		freemarkerCfg.setDirectoryForTemplateLoading(this.templatePath);
		freemarkerCfg.setObjectWrapper(new DefaultObjectWrapper());
		freemarkerCfg.setDefaultEncoding("UTF-8");
	}

	/**
	 * 生成文件
	 * 
	 * @param templateFileName 模版名称eg:(biz/order.ftl)
	 * @param propMap 用于处理模板的属性Object映射
	 * @param targetFile 要生成的静态文件的路径和文件名,例如 "y:/abc/a/123.htm"
	 * @return
	 */
	public boolean build(String templateFileName, Object propMap, File targetFile) {
		Writer out = null;
		try {
			Template template = freemarkerCfg.getTemplate(templateFileName);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), StringUtils.defaultIfEmpty(freemarkerCfg.getOutputEncoding(), freemarkerCfg.getDefaultEncoding())));
			template.process(propMap, out);
			return true;
		} catch (TemplateException e) {
			Logs.debug("", e);
			return false;
		} catch (IOException e) {
			Logs.debug("", e);
			return false;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Logs.debug("", e);
				}
			}
		}
	}

	/**
	 * 生成代码
	 * 
	 * @param templateFileName
	 * @param propMap
	 * @return
	 */
	public String build(String templateFileName, Object propMap) {
		Writer out = null;
		try {
			Template template = freemarkerCfg.getTemplate(templateFileName);
			StringWriter stringWriter = new StringWriter();
			out = new BufferedWriter(stringWriter);
			template.process(propMap, out);
			return stringWriter.toString();
		} catch (TemplateException e) {
			Logs.debug("", e);
			return "";
		} catch (IOException e) {
			Logs.debug("", e);
			return "";
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Logs.debug("", e);
				}
			}
		}
	}

	/**
	 * @return the freemarkerCfg
	 */
	public Configuration getFreemarkerCfg() {
		return freemarkerCfg;
	}

	/**
	 * @return the templatePath
	 */
	public File getTemplatePath() {
		return templatePath;
	}

	

//	/**
//	 * 加载模板文件
//	 * 
//	 * @param templateName 模板文件名称
//	 * @return
//	 */
//	public String loadTemplate(String templateName) {
//		StringBuffer sb = new StringBuffer();
//		try {
//			File file = new File(templatePath.getAbsoluteFile() + "/" + templateName);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//			String line = reader.readLine();
//			while (line != null) {
//				sb.append(line);
//				sb.append("\r\n");
//				line = reader.readLine();
//			}
//			reader.close();
//		} catch (IOException e) {
//			throw new RuntimeException("Loading template Error:", e);
//		}
//		return sb.toString();
//	}

//	/**
//	 * 保存模板
//	 * 
//	 * @param templateName
//	 * @param templateContent
//	 */
//	public void saveTemplate(String templateName, String templateContent) {
//		try {
//			File file = new File(templatePath + "/" + templateName);
//			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//			out.write(templateContent);
//			out.flush();
//			// 扔出templatesave事件
//			TemplateSaveEvent evt = new TemplateSaveEvent();
//			evt.setTemplateName(templateName);
//			dispatchTemplateEvent(evt);
//		} catch (IOException e) {
//			throw new SystemException("Write template Error", e);
//		}
//	}
	
//	/** 
//     * 创建多级目录 
//     * 
//     * @param aParentDir String 
//     * @param aSubDir  以/开头 
//     * @return boolean 是否成功 
//     */ 
//	public static boolean creatDirs(String aParentDir, String aSubDir) {
//		File aFile = new File(aParentDir);
//		if (aFile.exists()) {
//			File aSubFile = new File(aParentDir + aSubDir);
//			if (!aSubFile.exists()) {
//				return aSubFile.mkdirs();
//			} else {
//				return true;
//			}
//		} else {
//			return false;
//		}
//	}

//	/**
//	 * TEST
//	 * 
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception {
//		FreeMarkerHelper _temp = FreeMarkerHelper.createNew(new File("Y:/ymatesoft_projects/yMateScaffold/templates/module_app"));
//		Map<String, String> root = new HashMap<String, String>();
//		root.put("projectName", "TestMkPrj");
//		_temp.build("project.ftl", root, "c:/temp/", ".project");
//		System.out.println(_temp.build("project.ftl", root));
//	}


}