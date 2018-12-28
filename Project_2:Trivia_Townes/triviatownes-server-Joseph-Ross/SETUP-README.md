# Author: Joseph Ross
# TriviaTownesServer

## Setup
- Open Eclipse
- Go to Project -> Open Projects From File System
- Click directory and select the TriviaTownesServer directory
- (This does not need to be in your current workspace)
- Note: Eclipse will not copy the project, it will just modify it in another directory.
- Click finish
- Set up a Tomcat 9 server if you haven't already in the workspace
- Run the server and navigate to /example in the browser
- If all goes well "testing" will show up on the window
- Let me know if you have any issues getting the server to run
- Follow the steps provided in the CORS section of this document
- 

## EnviromentVariables
- Everyone will have a different path to the database properties file, and possibly other resources
- The problem is we can't push our ConnectionFactory file if the database.properties file path is different for all of us.
- Add the database.properties file in (src/main/resources.database.properties)
- Add this file: (src/main/java/com.ex.env.EnviromentVariables.java)
- Make sure you put these files in the correct place!
```
package com.ex.env;
public class EnviromentVariables {
	
	//Example: You will need to change this to match your own system path to the database.properties file
	static String dbPropertiesPath = "..../database.properties";

}
```


## Gitingore
I created a .gitignore file that should allow us to all work on
different Operating systems (Windows, Linux, Mac) without breaking each others environments. If you pull code down and everything breaks, this is probably because you pulled down files from someone else's environment. If this happens it's my fault (Joe) and we can fix it together. But I'm fairly confident this will not be an issue.

## CORS
I showed you all the filter that needs to be added to the TOMCAT 9 server configuration.
This is something that you will all need to do yourself after you create the tomcat server
in eclipse.
##### INSTRUCTIONS:
- Add this code snippet to your 'Servers/Tomcat/web.xml' file
- Make a request from the Angular application to check and see if it is working. If you get a CORS error in the browser console then you haven't done it correctly.
- **Note:** *If you are not hosting your angular server on port 4200 (the default) you will need to change the port number to match your configuration.*
- **Note:** *Do not push your server to github, this is configured a little differently on all your computers. That is why you need to add this code snippet yourself.*

```
<filter>
    <filter-name>CorsFilter</filter-name>
    <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
    <init-param>
        <param-name>cors.allowed.origins</param-name>
        <param-value>http://localhost:4200</param-value>
    </init-param>
    <init-param>
        <param-name>cors.allowed.methods</param-name>
        <param-value>GET,POST,PUT,DELETE,HEAD,OPTIONS</param-value>
    </init-param>
    <init-param>
        <param-name>cors.allowed.headers</param-name>
        <param-value>Content-Type,X-Requested-With,Accept,Authorization,Origin,Access-Control-Request-Method,Access-Control-Request-Headers</param-value>
    </init-param>
    <init-param>
        <param-name>cors.exposed.headers</param-name>
        <param-value>Access-Control-Allow-Origin,Access-Control-Allow-Credentials</param-value>
    </init-param>
    <init-param>
    	<param-name>cors.support.credentials</param-name>
    	<param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CorsFilter</filter-name>
    <url-pattern> /* </url-pattern>
</filter-mapping>
```
