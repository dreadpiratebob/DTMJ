rm -rf target
rm -f src/main/docker/DTMJ.war
mvn clean package
cp target/DTMJ.war src/main/docker/
docker build src/main/docker/ --tag dtmj:latest
# TO DO: push the docker image?
rm -rf target
