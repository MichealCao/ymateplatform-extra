/**
 * <p>文件名:	PropertiesHelper.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Commons</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.extra.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * PropertiesHelper
 * </p>
 * <p>
 * 处理.properties属性文件的工具类；
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
 *          <td>2010-9-5下午06:37:36</td>
 *          </tr>
 *          </table>
 */
public class PropertiesHelper {

	/**
	 * 当前Properties类实例对象
	 */
	private Properties __properties = null;

	/**
	 * 属性文件名称
	 */
	private URL __fileName = null;

	/**
	 * 构造器
	 * 
	 * @param fileName 属性文件路径及名称
	 * @throws IOException 文件加载异常
	 */
	public PropertiesHelper(URL fileName) throws IOException {
		this.__properties = new Properties();
		this.__fileName = fileName;
		InputStream _fileInputStream = fileName.openStream();
		this.__properties.load(_fileInputStream);
		_fileInputStream.close();
	}

	/**
	 * 获取当前Properties类实例对象
	 * 
	 * @return Properties类实例对象
	 */
	public Properties getProperties() {
		return this.__properties;
	}

	/**
	 * 设置当前Properties类实例对象
	 * 
	 * @param properties Properties类实例对象
	 */
	public void setProperties(Properties properties) {
		this.__properties = properties;
	}

	/**
	 * 获取由key指定的属性值
	 * 
	 * @param key 属性键值
	 * @return 属性值
	 */
	public String getValue(String key) {
		return this.__properties.getProperty(key);
	}

	/**
	 * 获取由key指定的属性值，若属性值为null则采用defaultValue作为默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getValue(String key, String defaultValue) {
		String _value = getValue(key);
		if (StringUtils.isBlank(_value)) {
			_value = defaultValue;
		}
		return _value;
	}

	/**
	 * 设置（或添加）属性值
	 * 
	 * @param key 属性键值
	 * @param value 属性值
	 */
	public void setValue(String key, String value) {
		this.__properties.setProperty(key, value);
	}

	/**
	 * 保存或更新属性文件
	 * 
	 * @throws IOException 文件写入异常
	 */
	public void saveOrUpdateProperties() throws IOException {
		// 首先读取原属性文件的属性数据，按行读取，每一行为一条记录
		FileInputStream _fileInputStream = new FileInputStream(__fileName.getFile());
		InputStreamReader _fileInputStreamReader = new InputStreamReader(_fileInputStream);
		BufferedReader _bufferedReader = new BufferedReader(_fileInputStreamReader);
		List<String> _datas = new ArrayList<String>();
		int index = -1;
		while (_bufferedReader.ready()) {
			index++;
			_datas.add(_bufferedReader.readLine());
		}
		_bufferedReader.close();
		_fileInputStreamReader.close();
		_fileInputStream.close();
		// 开始遍历当前Properties属性对象
		for (Object key : __properties.keySet()) {
			boolean isNew = true;
			for (int i = 0; i <= index; i++) {
				// 若原属性数据中存在当前属性，则更新它
				if (_datas.get(i).matches("^\\s*" + key + "\\s*\\=.*")) {
					_datas.set(i, key + "=" + __properties.getProperty(key.toString()).replaceAll("\n", "\\\\n"));
					isNew = false;
					break;
				}
			}
			// 否则新增加之
			if (isNew) {
				index++;
				_datas.add(index, key + "=" + __properties.getProperty(key.toString()).replaceAll("\n", "\\\\n"));
			}
		}
		// 重新写入文件
		FileOutputStream _fileOutputStream = new FileOutputStream(__fileName.getFile());
		OutputStreamWriter _outputStreamWriter = new OutputStreamWriter(_fileOutputStream);
		BufferedWriter _bufferedWriter = new BufferedWriter(_outputStreamWriter);
		for (String data : _datas) {
			_bufferedWriter.write(data);
			_bufferedWriter.newLine();
		}
		_bufferedWriter.flush();
		_outputStreamWriter.flush();
		_fileOutputStream.flush();
		_bufferedWriter.close();
		_outputStreamWriter.close();
		_fileOutputStream.close();
	}

	/**
	 * 销毁PropertiesHelper内部对象
	 */
	public void destroy() {
		this.__properties.clear();
		this.__properties = null;
	}

}
