rm -rf target
rm -f src/main/docker/DTMJ.war
mvn clean package
cp target/DTMJ.war src/main/docker/
docker build src/main/docker/
# TO DO: tag and push the docker image
rm -rf target
