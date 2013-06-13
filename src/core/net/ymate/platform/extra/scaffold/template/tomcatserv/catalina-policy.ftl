// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to You under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// ============================================================================
// catalina.corepolicy - Security Policy Permissions for Tomcat 6
//
// This file contains a default set of security policies to be enforced (by the
// JVM) when Catalina is executed with the "-security" option.  In addition
// to the permissions granted here, the following additional permissions are
// granted to the codebase specific to each web application:
//
// * Read access to the document root directory
//
// $Id: catalina.policy 648343 2008-04-15 17:21:29Z markt $
// ============================================================================


// ========== SYSTEM CODE PERMISSIONS =========================================


// These permissions apply to javac
grant codeBase "file:${r"${java.home}"}/lib/-" {
        permission java.security.AllPermission;
};

// These permissions apply to all shared system extensions
grant codeBase "file:${r"${java.home}"}/jre/lib/ext/-" {
        permission java.security.AllPermission;
};

// These permissions apply to javac when ${r"${java.home] points at $JAVA_HOME/jre"}
grant codeBase "file:${r"${java.home}"}/../lib/-" {
        permission java.security.AllPermission;
};

// These permissions apply to all shared system extensions when
// ${r"${java.home} points at $JAVA_HOME/jre"}
grant codeBase "file:${r"${java.home}"}/lib/ext/-" {
        permission java.security.AllPermission;
};


// ========== CATALINA CODE PERMISSIONS =======================================


// These permissions apply to the daemon code
grant codeBase "file:${r"${catalina.home}"}/bin/commons-daemon.jar" {
        permission java.security.AllPermission;
};

// These permissions apply to the logging API
grant codeBase "file:${r"${catalina.home}"}/bin/tomcat-juli.jar" {
        permission java.util.PropertyPermission "java.util.logging.config.class", "read";
        permission java.util.PropertyPermission "java.util.logging.config.file", "read";
        permission java.io.FilePermission "${r"${java.home}"}${r"${file.separator}"}lib${r"${file.separator}"}logging.properties", "read"; 
        permission java.lang.RuntimePermission "shutdownHooks";
        permission java.io.FilePermission "${r"${catalina.base}"}${r"${file.separator}"}conf${r"${file.separator}"}logging.properties", "read";
        permission java.util.PropertyPermission "catalina.base", "read";
        permission java.util.logging.LoggingPermission "control";
        permission java.io.FilePermission "${r"${catalina.base}"}${r"${file.separator}"}logs", "read, write";
        permission java.io.FilePermission "${r"${catalina.base}"}${r"${file.separator}"}logs${r"${file.separator}"}*", "read, write";
        permission java.lang.RuntimePermission "getClassLoader";
        // To enable per context logging configuration, permit read access to the appropriate file.
        // Be sure that the logging configuration is secure before enabling such access
        // eg for the examples web application:
        // permission java.io.FilePermission "${r"${catalina.base}"}${r"${file.separator}"}webapps${r"${file.separator}"}examples${r"${file.separator}"}WEB-INF${r"${file.separator}"}classes${r"${file.separator}"}logging.properties", "read";
};

// These permissions apply to the server startup code
grant codeBase "file:${r"${catalina.home}"}/bin/bootstrap.jar" {
        permission java.security.AllPermission;
};

// These permissions apply to the servlet API classes
// and those that are shared across all class loaders
// located in the "lib" directory
grant codeBase "file:${r"${catalina.home}"}/lib/-" {
        permission java.security.AllPermission;
};


// ========== WEB APPLICATION PERMISSIONS =====================================


// These permissions are granted by default to all web applications
// In addition, a web application will be given a read FilePermission
// and JndiPermission for all files and directories in its document root.
grant { 
    // Required for JNDI lookup of named JDBC DataSource's and
    // javamail named MimePart DataSource used to send mail
    permission java.util.PropertyPermission "java.home", "read";
    permission java.util.PropertyPermission "java.naming.*", "read";
    permission java.util.PropertyPermission "javax.sql.*", "read";

    // OS Specific properties to allow read access
    permission java.util.PropertyPermission "os.name", "read";
    permission java.util.PropertyPermission "os.version", "read";
    permission java.util.PropertyPermission "os.arch", "read";
    permission java.util.PropertyPermission "file.separator", "read";
    permission java.util.PropertyPermission "path.separator", "read";
    permission java.util.PropertyPermission "line.separator", "read";

    // JVM properties to allow read access
    permission java.util.PropertyPermission "java.version", "read";
    permission java.util.PropertyPermission "java.vendor", "read";
    permission java.util.PropertyPermission "java.vendor.url", "read";
    permission java.util.PropertyPermission "java.class.version", "read";
    permission java.util.PropertyPermission "java.specification.version", "read";
    permission java.util.PropertyPermission "java.specification.vendor", "read";
    permission java.util.PropertyPermission "java.specification.name", "read";

    permission java.util.PropertyPermission "java.vm.specification.version", "read";
    permission java.util.PropertyPermission "java.vm.specification.vendor", "read";
    permission java.util.PropertyPermission "java.vm.specification.name", "read";
    permission java.util.PropertyPermission "java.vm.version", "read";
    permission java.util.PropertyPermission "java.vm.vendor", "read";
    permission java.util.PropertyPermission "java.vm.name", "read";

    // Required for OpenJMX
    permission java.lang.RuntimePermission "getAttribute";

    // Allow read of JAXP compliant XML parser debug
    permission java.util.PropertyPermission "jaxp.debug", "read";

    // Precompiled JSPs need access to this package.
    permission java.lang.RuntimePermission "accessClassInPackage.org.apache.jasper.runtime";
    permission java.lang.RuntimePermission "accessClassInPackage.org.apache.jasper.runtime.*";
    
    // Precompiled JSPs need access to this system property.
    permission java.util.PropertyPermission "org.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER", "read";

};


// You can assign additional permissions to particular web applications by
// adding additional "grant" entries here, based on the code base for that
// application, /WEB-INF/classes/, or /WEB-INF/lib/ jar files.
//
// Different permissions can be granted to JSP pages, classes loaded from
// the /WEB-INF/classes/ directory, all jar files in the /WEB-INF/lib/
// directory, or even to individual jar files in the /WEB-INF/lib/ directory.
//
// For instance, assume that the standard "examples" application
// included a JDBC driver that needed to establish a network connection to the
// corresponding database and used the scrape taglib to get the weather from
// the NOAA web server.  You might create a "grant" entries like this:
//
// The permissions granted to the context root directory apply to JSP pages.
// grant codeBase "file:${r"${catalina.home}"}/webapps/examples/-" {
//      permission java.net.SocketPermission "dbhost.mycompany.com:5432", "connect";
//      permission java.net.SocketPermission "*.noaa.gov:80", "connect";
// };
//
// The permissions granted to the context WEB-INF/classes directory
// grant codeBase "file:${r"${catalina.home}"}/webapps/examples/WEB-INF/classes/-" {
// };
//
// The permission granted to your JDBC driver
// grant codeBase "jar:file:${r"${catalina.home}"}/webapps/examples/WEB-INF/lib/driver.jar!/-" {
//      permission java.net.SocketPermission "dbhost.mycompany.com:5432", "connect";
// };
// The permission granted to the scrape taglib
// grant codeBase "jar:file:${r"${catalina.home}"}/webapps/examples/WEB-INF/lib/scrape.jar!/-" {
//      permission java.net.SocketPermission "*.noaa.gov:80", "connect";
// };

