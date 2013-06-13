/**
 * <p>文件名:	AbstractGenerator.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package net.ymate.platform.extra.scaffold;

import java.util.Map;

/**
 * <p>
 * AbstractGenerator
 * </p>
 * <p>
 * 生成器接口抽象实现类；
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
 *          <td>2011-10-26下午03:01:19</td>
 *          </tr>
 *          </table>
 */
public abstract class AbstractGenerator implements IGenerator {

	private Map<String, Object> __params;

	/* (non-Javadoc)
	 * @see com.ymatesoft.scaffold.IGenerator#initialize(java.util.Map)
	 */
	public void initialize(Map<String, Object> args) {
		this.__params = args;
	}

	/**
	 * 获取指定KEY的参数值对象
	 */
	public Object getParamItem(String key) {
		return this.__params.get(key);
	}

}
