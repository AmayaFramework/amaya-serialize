# amaya-gson [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.amayaframework/gson-impl/badge.svg?style=flat)](https://repo1.maven.org/maven2/io/github/amayaframework/gson-impl/)

A plugin that adds features for json using gson.

## Getting Started

To install it, you will need:

* any build of the JDK no older than version 8
* Maven/Gradle
* [classindex](https://github.com/atteo/classindex)
* [amaya-core](https://github.com/AmayaFramework/amaya-core)
* [gson](https://github.com/google/gson) (optionally)

## Installing

### Gradle dependency

```Groovy
dependencies {
    implementation group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    annotationProcessor group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.8.9'
    implementation group: 'io.github.amayaframework', name: 'core', version: 'LATEST'
    implementation group: 'io.github.amayaframework', name: 'gson-impl', version: 'LATEST'
}
```

### Maven dependency

```
<dependency>
    <groupId>org.atteo.classindex</groupId>
    <artifactId>classindex</artifactId>
    <version>3.4</version>
</dependency>
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.9</version>
</dependency>
<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>core</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>gson-impl</artifactId>
    <version>LATEST</version>
</dependency>
```

## Usage example

To use the plugin, just add its pipeline configurator when configuring the framework server.

```Java
public class Server {
    public static void main(String[] args) throws IOException {
        AmayaServer server = new AmayaBuilder().
                addConfigurator(new GsonPipelineConfigurator()).
                build();
        server.start();
    }
}
```

If you want the plugin to automatically package the request body into an instance of your object, 
annotate the controller with @Entity(BodyObject.class).

```Java
@Endpoint("/my-end-point")
@Entity(BodyObject.class)
public class MyController extends AbstractController {
    @Post
    public HttpResponse post(HttpRequest request, @Body BodyObject body) {
        // Process body here
        return ok(body);
    }
}
```

## Built With

* [Gradle](https://gradle.org) - Dependency management
* [classindex](https://github.com/atteo/classindex) - Annotation scanning
* [gson](https://github.com/google/gson) - Working with JSON and dynamic object creation
* [java-utils](https://github.com/RomanQed/java-utils) - Pipelines and other stuff
* [sun-http-server](https://github.com/AmayaFramework/sun-http-server) - Some http stuff
* [amaya-filters](https://github.com/AmayaFramework/amaya-filters) - Body filter
* [amaya-core](https://github.com/AmayaFramework/amaya-core) - Pipeline handlers

## Authors
* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)

See also the list of [contributors](https://github.com/AmayaFramework/amaya-filters/contributors) 
who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to gson developer - this is a great json implementation for java!
