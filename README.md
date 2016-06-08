# Domain Specific Language connectors

## Purpose

The purpose of the DSL connectors is to understand OCCI request and to transform them into a Cloud Automation Service request entry.

## Building and running

You can start the service as a standalone application:
```
$ gradlew clean build bootRun
```

You can build a WAR file as follows:

```
$ gradlew clean build war
```

Then, you can directly deploy the service with embedded Tomcat:

```
$ java -jar build/libs/microservice-template-X.Y.Z-SNAPSHOT.war
```

The WAR file produced by Gradle can also be deployed in the embedded Jetty container started by an instance of [ProActive Server](https://github.com/ow2-proactive/scheduling).

Sometimes the gradle processes are not killing properly when you stop the running application. If you receive the message "the port is already in use" on starting microservice, then kill all suspending gradle processes for previous task. You can do it manually or use in IntelliJ IDEA Gradle killer plugin.

## Example
The service is organized with a complete RESTful example. The example follows MVC packaging structure and covered by tests.
The purpose of example to make more easy developing of new service.
To try it out use Swagger or (http://localhost:8080/compute).<br>

## Swagger

Available resources can be listed and tested with Swagger. The associated code is in the **Application.java** file:
Modify the name of microservice-template in title, description, licenseUrl, groupName sections. Put right allowedPaths.<br>
To access Swagger API:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)