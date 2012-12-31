/**
 * <p>文件名:	${modelName?cap_first}PK.java</p>
 * <p>版权:		详见产品版权说明书</p>
 * <p>公司:		YMateSoft Co., Ltd.</p>
 * <p>项目名：	yMatePlatform-Scaffold</p>
 * <p>作者：		刘镇(suninformation@163.com)</p>
 */
package ${packageName}.model;

import net.ymate.platform.persistence.jdbc.IEntityPK;
import net.ymate.platform.persistence.jdbc.annotation.Column;
import net.ymate.platform.persistence.jdbc.annotation.PK;

/**
 * <p>
 * ${modelName?cap_first}PK
 * </p>
 * <p>
 * Code Generator By yMateScaffold
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
 *          <td>${lastUpdateTime?string("yyyy-MM-dd a HH:mm:ss")}</td>
 *          </tr>
 *          </table>
 */
@PK
public class ${modelName?cap_first}PK implements IEntityPk {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	<#list primaryKeyList as field>
	@Column(name = "${field.columnName}"<#if (field.autoIncrement)>, isAutoIncrement=true</#if>)
	private ${field.varType} ${field.varName};
	</#list>

	/**
	 * 构造器
	 */
	public ${modelName?cap_first}PK() {
	}

	/**
	 * 构造器
	 <#list primaryKeyList as field>
	 *	@param ${field.varName}
	</#list>
	 */
	public ${modelName?cap_first}PK(<#list primaryKeyList as field>${field.varType} ${field.varName}<#if field_has_next>, </#if></#list>) {
		<#list primaryKeyList as field>
		this.${field.varName} = ${field.varName};
		</#list>
	}

	<#list primaryKeyList as field>

	/**
	 * @return the ${field.varName}
	 */
	public ${field.varType} get${field.varName?cap_first}() {
		return ${field.varName};
	}

	/**
	 * @param ${field.varName} the ${field.varName} to set
	 */
	public void set${field.varName?cap_first}(${field.varType} ${field.varName}) {
		this.${field.varName} = ${field.varName};
	}

	</#list>

}
