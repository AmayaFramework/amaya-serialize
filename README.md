# amaya-serialize [![maven-central](https://img.shields.io/maven-central/v/io.github.amayaframework/gson-impl?color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/gson-impl/)

A plugin that allows you to easily integrate support for the required serializer.

## Getting Started

To install it, you will need:

* any build of the JDK no older than version 8
* Maven/Gradle
* [classindex](https://github.com/atteo/classindex)
* [amaya-core-api](https://github.com/AmayaFramework/amaya-core-api)

## Installing

### Gradle dependency

```Groovy
dependencies {
    implementation group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    annotationProcessor group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    implementation group: 'io.github.amayaframework', name: 'core-api', version: 'LATEST'
    implementation group: 'io.github.amayaframework', name: 'serialize', version: 'LATEST'
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
    <groupId>io.github.amayaframework</groupId>
    <artifactId>core-api</artifactId>
    <version>LATEST</version>
</dependency>
<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>serialize</artifactId>
    <version>LATEST</version>
</dependency>
```

## Usage example

To use the plugin, just add its pipeline configurator when configuring the framework server.

```Java
public class Server {
    public static void main(String[] args) throws Throwable {
        Serializer serializer; // = new YourSerializer();
        Amaya<?> amaya = new TomcatBuilder().
                addConfigurator(new SerializeConfigurator(serializer)).
                build();
        amaya.start();
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
* [java-utils](https://github.com/RomanQed/java-utils) - Pipelines and other stuff
* [amaya-filters](https://github.com/AmayaFramework/amaya-filters) - Body filter
* [amaya-core-api](https://github.com/AmayaFramework/amaya-core-api) - Pipeline handlers

## Authors
* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)

See also the list of [contributors](https://github.com/AmayaFramework/amaya-filters/contributors) 
who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to gson developer - this is a great json implementation for java!
