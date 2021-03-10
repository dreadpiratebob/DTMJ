# DTMJ
This is the configuration needed to run a Java API on an Apache Server in a Docker container that's connected to a MySQL database.  (db not included; set that up separately.  this is just the D, T and J pieces with a connection to the M.)  This is currently configured to use Tomcat 9 and Java 14.  To use this project, it'll be helpful to familiarize yourself with the Docker commands.

TO DO: set up JDBC.

## initial setup

To build and run DTMJ, you'll need some tools:

### Java

You'll need Java 14, which you can get from Oracle's website or from https://adoptopenjdk.net/   Note that Oracle's website is hard to nagivate.  If you want to get Java from Oracle (which you can do for free in spite of them trying to get you to pay for it), use Google to find the correct download page.

### Docker

Since this project is meant to be run in a Docker container, it won't work too well without Docker.  Get it from docker.com.  I've been using Docker commands on the command line, but you can get a GUI, too.

### Bash

Speaking of the command line, you'll need an environment for running bash, because this repo uses a shell script for builds.  If you're on Windows, go get Git Bash so that you can work in a proper Unix-like environment.

### Maven

My scripts also use Maven to package up the code and config here.  Download the binaries from https://maven.apache.org/download.cgi and make sure you can run the `mvn -version` command on the command line.  (You might have to add a directory to your environment variable called `PATH`.)

## building DTMJ

Included is a build script (`build.sh`) that will build and package the java and then build a Docker image with the packaged Java.  Note that nothing included here deletes old Docker images (which take up about half a gigabyte each), so it's good to keep them cleaned up.

TO DO: tag the Docker image.

## running DTMJ

No run script is provided (yet); to run DTMJ after building it, use this command:<br />
`docker run -dp <an available port>:8080 -v "/your/local/log/dir:/usr/local/tomcat/logs/" <docker image id>`<br />
where:<br />
* `<an available port>` is a port on the machine you're running this on that clients can make http calls to
* `/your/local/log/dir` is the path to a local directory (outside the Docker image) where the Tomcat instance can save logs
* `<Docker image id>` is the id of the docker image created by the build script.

Once you've successfully run that command, you can send an HTTP GET request to `http://<host ip>:<available port>/DTMJ/health/` where:
* `<host ip>` is the ip address of the machine hosting the docker image
* `<available port>` is the port from above<br />
Note that, if you copy and rename this project (per the section below), you'll have to change "DTMJ" in the url to whatever name you choose (because Tomcat will automatically use that name in the url for you).

Optionally, you can add `?message=<any url-encoded string>` to the end of the url above, and the url-encoded string will be decoded and included in the response.  If you're not familiar with url-encoding but you're diving into writing up a web app, it's worth reading up on it.  w3schools has some info here: https://www.w3schools.com/tags/ref_urlencode.ASP

## using DTMJ for your own project

If you want to make a copy of DTMJ to use for your own Java-based API, you'll probly want to rename the project.  Here's a list of all references to the name DTMJ:
* the url in the section on running DTMJ
* `/project/artifactId` in `./pom.xml`
* `/project/build/finalName` in `./pom.xml`
* `./build.sh` line 2 (`rm -f src/main/docker/DTMJ.war`)  (Maven gets the name of the packaged `.war` file from the contents of the `/project/build/finalName` node in `./pom.xml`.)
* `./build.sh` line 4 (`cp target/DTMJ.war src/main/docker/`)
* `./src/main/docker/Dockerfile` line 6 (`COPY DTMJ.war $CATALINA_HOME/webapps/`)<br />
Note that, in paths to files, `.` is the directory that you cloned DTMJ to, and the paths in `pom.xml` are XPaths.

If you change the name of the `service` package, you'll also have to change the second condition in the `service.util.ModelSerializer.objectIsModel` method.

After that, read up on the annotations in my sample HealthController to see how relative paths and request variables and bodies are handled.

### Licensing

I deliberately avoided puting any license on this repo.  I don't own most of the software used here (eg Docker and Tomcat), and I don't really care if someone else uses a copy of the code I wrote here (as long as I can use my own copy).

## other notes

In `./.gitignore`, I included IntelliJ's config files (because that's what I've been using for development), but I haven't included anything from Eclipse.  If you use Eclipse, you'll have to add those yourself (and, if you do so and feel particularly generous, you could send those additions to me in the form of a PR - if you don't, i probly won't get around to adding them).