/**
 * <p>文件名:	XMLHelper.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Commons</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.extra.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * <p>
 * XMLHelper
 * </p>
 * <p>
 * 针对以XML文件存储的配置数据读取、写入操作的封装类；
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
 *          <td>2010-10-18上午11:43:00</td>
 *          </tr>
 *          </table>
 */
public class XMLHelper {

	/**
	 * 默认配置元素的标签名称
	 */
	private String __defaultElementName = "property";

	/**
	 * 配置元素的KEY属性名称
	 */
	private String __defaultItemKeyName = "name";

	/**
	 * 配置元素的VALUE属性名称
	 */
	private String __defaultItemValueName = "value";

	private SAXReader __reader = null;
	private Document __xmlDocument;

	private String __charset = "UTF-8";

	/**
	 * 构造器
	 * 
	 * @param xmlFile
	 * @throws DocumentException
	 * @throws IOException
	 */
	public XMLHelper(URL xmlFile) throws DocumentException, IOException {
		this.__loadXmlDocument(xmlFile, false);
	}

	/**
	 * 构造器
	 * 
	 * @param xmlFile
	 * @param notDTD
	 * @throws DocumentException
	 * @throws IOException
	 */
	public XMLHelper(URL xmlFile, boolean notDTD) throws DocumentException, IOException {
		this.__loadXmlDocument(xmlFile, notDTD);
	}

	/**
	 * 构造器
	 * 
	 * @param xmlFile
	 * @param notDTD
	 * @param charset
	 * @throws DocumentException
	 * @throws IOException
	 */
	public XMLHelper(URL xmlFile, boolean notDTD, String charset) throws DocumentException, IOException {
		this.__charset = charset;
		this.__loadXmlDocument(xmlFile, notDTD);
	}

	/**
	 * 加载XML文件
	 * 
	 * @param xmlFile
	 * @param notDTD 是否验证DTD
	 * @throws DocumentException
	 * @throws IOException
	 */
	private synchronized void __loadXmlDocument(URL xmlFile, boolean notDTD) throws DocumentException, IOException {
		__reader = new SAXReader();
		if (notDTD) {
			__reader.setValidation(false);
			// 取消DTD文件的验证
			__reader.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) {
					return new InputSource(new StringReader(""));
				}
			});
		}
		InputStream _fileInputStream = xmlFile.openStream();
		InputStreamReader _fileInputStreamReader = new InputStreamReader(_fileInputStream, __charset);
		this.__xmlDocument = __reader.read(_fileInputStreamReader);
	}

	/**
	 * @return Document实类对象，方便自行编写扩展代码
	 */
	public Document getDocument() {
		return this.__xmlDocument;
	}

	/**
	 * 转换XML元素路径字符中的'.'为'/'
	 * 
	 * @param pathStr
	 * @return
	 */
	public String convertPath(String pathStr) {
		return "//".concat(pathStr.replace('.', '/'));
	}

	/**
	 * 设置默认配置元素的标签名称
	 * 
	 * @param elementName
	 * @return
	 */
	public XMLHelper setDefaultElementName(String elementName) {
		this.__defaultElementName = elementName;
		return this;
	}

	public XMLHelper setDefaultItemKeyName(String itemKeyName) {
		this.__defaultItemKeyName = itemKeyName;
		return this;
	}

	public XMLHelper setDefaultItemValueName(String itemValueName) {
		this.__defaultItemValueName = itemValueName;
		return this;
	}

	/**
	 * 关闭文档资源
	 */
	public void closeDocument() {
		try {
			this.__xmlDocument.clearContent();
		} catch (Exception e) {
			// Nothing.
		}
		this.__reader = null;
		this.__xmlDocument = null;
	}

	/**
	 * 获取由xmlItemPath参数指定的配置分类标签的分类名称列表
	 * <p>
	 * 如：参数设置为properties.category，则此方法将返回所有category标签的name属性值列表
	 * </p>
	 * 
	 * @param xmlItemPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getCategoryTagNodeNames(String xmlItemPath) {
		List<String> _returnValue = new ArrayList<String>();
		List<Node> nodes = this.__xmlDocument.selectNodes(convertPath(xmlItemPath));
		if (nodes != null && !nodes.isEmpty()) {
			String _categoryName = null;
			for (Node node : nodes) {
				_categoryName = node.valueOf("@name");
				if (StringUtils.isNotBlank(_categoryName)) {
					_returnValue.add(_categoryName);
				}
			}
		}
		return _returnValue;
	}

	@SuppressWarnings("unchecked")
	public Node getNodeElementByCategoryName(String xmlItemPath, String categoryName) {
		Node _returnNode = null;
		List<Node> nodes = this.__xmlDocument.selectNodes(convertPath(xmlItemPath));
		if (nodes != null && !nodes.isEmpty()) {
			for (Node node : nodes) {
				String _categoryName = node.valueOf("@name");
				if (categoryName.equals(_categoryName)) {
					_returnNode = node;
					break;
				}
			}
		}
		return _returnNode;
	}

	public Map<String, String> getPropertiesByNode(Node node) {
		return this.getPropertiesByNode(node, __defaultElementName, __defaultItemKeyName, __defaultItemValueName);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getPropertiesByNode(Node node, String elementName, String itemKeyName, String itemValueName) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<Element> it = node.selectNodes(elementName).iterator();
		while (it.hasNext()) {
			Element e = it.next();
			String _value = null;
			String _key = e.valueOf("@" + itemKeyName);
			if (StringUtils.isNotBlank(_key)) {
				_value = e.valueOf("@" + itemValueName);
				if (StringUtils.isBlank(_value)) {
					// 若属性值为空时，则再取一次标签开始与结束之间的文字内容
					_value =  e.getText();
				}
				map.put(_key, _value);
			}
		}
		return map;
	}

	/**
	 * 获取由categoryName参数所指定的分类的属性映射
	 * 
	 * @param xmlItemPath
	 * @param categoryName
	 * @param elementName
	 * @param itemKeyName
	 * @param itemValueName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getPropertiesByCategoryName(String xmlItemPath, String categoryName, String elementName, String itemKeyName, String itemValueName) {
		Map<String, String> map = new HashMap<String, String>();
		List<Node> nodes = this.__xmlDocument.selectNodes(convertPath(xmlItemPath));
		if (nodes != null && !nodes.isEmpty()) {
			for (Node node : nodes) {
				String _categoryName = node.valueOf("@name");
				if (categoryName.equals(_categoryName)) {
					Iterator<Element> it = node.selectNodes(elementName).iterator();
					while (it.hasNext()) {
						Element e = it.next();
						String _value = null;
						String _key = e.valueOf("@" + itemKeyName);
						if (StringUtils.isNotBlank(_key)) {
							_value = e.valueOf("@" + itemValueName);
							if (StringUtils.isBlank(_value)) {
								// 若属性值为空时，则再取一次标签开始与结束之间的文字内容
								_value =  e.getText();
							}
							map.put(_key, _value);
						}
					}
					break;
				}
			}
		}
		return map;
	}

	/**
	 * 获取由categoryName参数所指定的分类的属性映射
	 * 
	 * @param xmlItemPath
	 * @param categoryName
	 * @param itemKeyName
	 * @param itemValueName
	 * @return
	 */
	public Map<String, String> getPropertiesByCategoryName(String xmlItemPath, String categoryName, String itemKeyName, String itemValueName) {
		return this.getPropertiesByCategoryName(xmlItemPath, categoryName, __defaultElementName, itemKeyName, itemValueName);
	}

	public Map<String, String> getPropertiesByCategoryName(String xmlItemPath, String categoryName) {
		return this.getPropertiesByCategoryName(xmlItemPath, categoryName, __defaultElementName, __defaultItemKeyName, __defaultItemValueName);
	}

	/**
	 * 获取属性映射
	 * 
	 * @param xmlItemPath
	 * @param elementName
	 * @param itemKeyName
	 * @param itemValueName
	 * @return
	 */
	public Map<String, String> getProperties(String xmlItemPath, String elementName, String itemKeyName, String itemValueName) {
		Map<String, String> map = new HashMap<String, String>();
		Node node = this.__xmlDocument.selectSingleNode(convertPath(xmlItemPath));
		if (node != null) {
			Iterator<?> it = node.selectNodes(elementName).iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String _value = null;
				String _key = e.valueOf("@" + itemKeyName);
				if (StringUtils.isNotBlank(_key)) {
					_value = e.valueOf("@" + itemValueName);
					if (StringUtils.isBlank(_value)) {
						// 若属性值为空时，则再取一次标签开始与结束之间的文字内容
						_value =  e.getText();
					}
					map.put(_key, _value);
				}
			}
		}
		return map;
	}

	/**
	 * 获取属性映射（默认子元素的标签名称为"item"）
	 * 
	 * @param xmlItemPath
	 * @param itemKeyName
	 * @param itemValueName
	 * @return
	 */
	public Map<String, String> getProperties(String xmlItemPath,  String itemKeyName, String itemValueName) {
		return this.getProperties(xmlItemPath, __defaultElementName, itemKeyName, itemValueName);
	}

	public Map<String, String> getProperties(String xmlItemPath) {
		return this.getProperties(xmlItemPath, __defaultElementName, __defaultItemKeyName, __defaultItemValueName);
	}

	public String getProperty(String xmlItemPath, String categoryName, String elementName, String equalValue, String itemKeyName, String itemValueName) {
		String _returnValue = null;
		Map<String, String> _properties = this.getPropertiesByCategoryName(xmlItemPath, categoryName, elementName, itemKeyName, itemValueName);
		if (_properties.containsKey(equalValue)) {
			_returnValue = _properties.get(equalValue);
		}
		return _returnValue;
	}

	public String getProperty(String xmlItemPath, String categoryName, String equalValue, String itemKeyName, String itemValueName) {
		return this.getProperty(xmlItemPath, categoryName, __defaultElementName, equalValue, itemKeyName, itemValueName);
	}

	public String getProperty(String xmlItemPath, String categoryName, String equalValue) {
		return this.getProperty(xmlItemPath, categoryName, __defaultElementName, equalValue, __defaultItemKeyName, __defaultItemValueName);
	}

}
