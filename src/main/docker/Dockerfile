FROM tomcat:9-jdk14-openjdk-oracle

RUN mkdir -p /mnt/libraries/
RUN mkdir -p $CATALINA_HOME/bin/

COPY DTMJ.war $CATALINA_HOME/webapps/
COPY setenv.sh $CATALINA_HOME/bin/

CMD ["catalina.sh", "run"]