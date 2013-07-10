<?xml version="1.0" encoding="UTF-8"?>
<project name="${project_name}" basedir="." default="sync">
	<property name="CONFIGURATION_HOME" value="../ymateweb-base" />

	<property name="DIST_DIR" value="${r"${CONFIGURATION_HOME}"}/WebContent/WEB-INF/plugins/${r"${ant.project.name}"}"/>
	<property name="SRC_DIR" value="src"/>
	<property name="VERSION" value="${plugin_version}"/>
	<property name="TEMP_BUILD_DIR" value="${r"${CONFIGURATION_HOME}"}/WebContent/WEB-INF/plugins/temp/${r"${ant.project.name}"}_temp"/>
	<property name="TARGET_VERSION" value="1.5"/>

	<!-- Init -->
	<target name="init">
		<delete dir="${r"${DIST_DIR}"}"/>
		<mkdir dir="${r"${DIST_DIR}"}"/>
		<mkdir dir="${r"${DIST_DIR}"}/lib"/>
	</target>

	<!-- Build -->
	<target name="build-jar" depends="init">
		<echo message="${r"${ant.project.name}"}: ${r"${ant.file}"}"/>
		<property name="version.num" value="${r"${VERSION}"}" />
		<buildnumber file="build.num" />
		<tstamp>
			<format property="TODAY" pattern="yyyy-MM-dd HH:mm:ss" />
		</tstamp>
		<manifest file="MANIFEST.MF">
			<!--
			<attribute name="Built-By" value="${r"${user.name}"}" />
			-->
			<attribute name="Built-By" value="${plugin_email}" />
			<attribute name="Main-Class" value="" />
			<attribute name="Implementation-Version" value="${r"${version.num}"}-b${r"${build.number}"}" />
			<attribute name="Built-Date" value="${r"${TODAY}"}" />
		</manifest>
		<jar destfile="${r"${DIST_DIR}"}/lib/${jar_name}-${r"${version.num}"}.jar" manifest="MANIFEST.MF">
			<fileset dir="bin">
				<exclude name="**/*.properties"/>
				<exclude name="**/*.html"/>
				<!--exclude name="**/*.xml"/-->
			</fileset>
		</jar>
		<copy todir="${r"${DIST_DIR}"}" file="${r"${SRC_DIR}"}/conf/ymate_plugin.xml" overwrite="true"></copy>
		<copy todir="${r"${DIST_DIR}"}/lib">
				<fileset dir="lib" />
			</copy>
		</target>

		<!-- Sync -->
		<target name="sync">
			<copy todir="${r"${CONFIGURATION_HOME}"}/WebContent">
				<fileset dir="resources" />
			</copy>
		</target>

</project>