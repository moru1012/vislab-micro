FROM tomcat:8
MAINTAINER Manuel Vogel

ADD ./target/vislab-webapp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
ADD ./conf/tomcat-users.xml /usr/local/tomcat/conf/

EXPOSE 8080