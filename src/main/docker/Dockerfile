FROM tomcat:9-jdk16-openjdk-buster

# Enable this and rebuild to refresh certs from the docker container.
# ENV container docker
# ENV PATH /snap/bin:$PATH
# RUN mkdir /usr/local/bin/snap
# RUN apt-get update
# RUN apt-get upgrade -y
# RUN apt-get install -y snapd squashfuse fuse
# RUN systemctl enable snapd


RUN mkdir -p /mnt/libraries/
RUN mkdir -p /mnt/certs/
RUN mkdir -p $CATALINA_HOME/bin/

RUN rm $CATALINA_HOME/conf/server.xml

COPY DTMJ.war $CATALINA_HOME/webapps/
COPY setenv.sh $CATALINA_HOME/bin/
COPY server.xml $CATALINA_HOME/conf/

EXPOSE 8443

CMD ["catalina.sh", "run"]