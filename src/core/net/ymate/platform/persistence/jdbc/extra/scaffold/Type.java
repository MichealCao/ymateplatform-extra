/**
 * <p>文件名:	Type.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.persistence.jdbc.extra.scaffold;

import java.io.IOException;
import java.util.Map;

import net.ymate.platform.commons.util.ResourceUtils;
import net.ymate.platform.extra.util.XMLHelper;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

/**
 * <p>
 * Type
 * </p>
 * <p>
 * 数据类型映射配置类；
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
 *          <td>2011-10-26上午12:31:28</td>
 *          </tr>
 *          </table>
 */
public class Type {

	private static Type __instanceObj;

	private Map<String, String> __typeMap;

	private Map<String, String> __standardTypeMap;

	/**
	 * 构造器
	 */
	public Type(String dbType) {
		XMLHelper _helper = null;
		try {
			_helper = new XMLHelper(ResourceUtils.getResource("type.xconf", Type.class));
			__typeMap = _helper.getProperties("mappings." + dbType, "item", "type", "java");
			__standardTypeMap = _helper.getProperties("mappings.standard", "item", "type", "java");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (_helper != null) _helper.closeDocument();
		}
	}

	public static Type getInstance(String dbType) {
		if (__instanceObj == null) {
			__instanceObj = new Type(dbType);
		}
		return __instanceObj;
	}

	public String getValue(String key) {
		String a = __typeMap.get(key);
		return StringUtils.defaultIfEmpty(a, "String");
	}

	public String getSqlType(String javaType) throws DocumentException, IOException {
		String r = __standardTypeMap.get(javaType);
		return StringUtils.defaultIfEmpty(r, "unknown_type");
	}

}
