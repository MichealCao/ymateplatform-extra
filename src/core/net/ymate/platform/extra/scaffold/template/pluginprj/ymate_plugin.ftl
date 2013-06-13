<?xml version="1.0" encoding="UTF-8"?>
<plugin id="${plugin_id}" 
		name="${plugin_name}" 
		alias="" 
		class="${plugin_package_name}.${plugin_startup_class}" 
		author="${plugin_author}" 
		email="${plugin_email}" 
		version="${plugin_version}">

	<!-- 是否加载时自动启动运行 -->
	<automatic>true</automatic>

	<!-- 是否禁用当前插件, 禁用的插件将不被加载 -->
	<disabled>false</disabled>

	<!-- 插件扩展内容部分 -->
	<extra-part>
		<!-- 插件首页URL -->
		<home-url></home-url>
		<!-- 插件LOGO图片URL -->
		<logo-url></logo-url>
		<!-- 插件后台管理入口URL，为空则视为无后台管理 -->
		<manage-url></manage-url>
		<!-- 管理菜单项定义 -->
		<manage-menus>
			<!-- 菜单项显示名称和跳转URL -->
			<menuitem name="" value=""></menuitem>
		</manage-menus>
		<webmvc>
			<controllers>
				<controller>${plugin_package_name}.controller.*</controller>
			</controllers>
		</webmvc>
		<persistence>
			<repository>${plugin_package_name}.repository.*</repository>
		</persistence>
		<!--
		<widgets>
			<widget>${plugin_package_name}.widget.*</widget>
		</widgets>
		-->
	</extra-part>

	<!-- 插件描述文字 -->
	<description>${plugin_description}</description>

</plugin>