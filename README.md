# amaya-serialize [![maven-central](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-serialize?color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-serialize/)

A plugin that allows you to easily integrate support for the required serializer.

## Getting Started

To install it, you will need:

* java 8+
* Maven/Gradle
* [classindex](https://github.com/atteo/classindex)
* [amaya-core](https://github.com/AmayaFramework/amaya-core)

## Installing

### Gradle dependency

```Groovy
dependencies {
    implementation group: 'org.atteo.classindex', name: 'classindex', version: '3.11'
    annotationProcessor group: 'org.atteo.classindex', name: 'classindex', version: '3.11'
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '1+'
    implementation group: 'io.github.amayaframework', name: 'amaya-serialize', version: 'LATEST'
}
```

### Maven dependency

```
<dependency>
    <groupId>org.atteo.classindex</groupId>
    <artifactId>classindex</artifactId>
    <version>3.11</version>
</dependency>
<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>amaya-core</artifactId>
    <version>1+</version>
</dependency>
<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>amaya-serialize</artifactId>
    <version>LATEST</version>
</dependency>
```

## Usage example

To use the plugin, just add its pipeline configurator when configuring the framework server.

```Java
public class Server {
    public static void main(String[] args) throws Throwable {
        Amaya<?> amaya = new AmayaBuilder().
                bind(8080).
                addConfigurator(new SerializeConfigurator()).
                build();
        amaya.start();
    }
}
```

If you want the plugin to automatically package the request body into an instance of your object,
annotate the controller with @Entity(BodyObject.class).

```Java

@Endpoint
@Entity(BodyObject.class)
public class MyController {
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
* [amaya-core](https://github.com/AmayaFramework/amaya-core) - Pipeline handlers

## Authors

* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)

See also the list of [contributors](https://github.com/AmayaFramework/amaya-serialize/contributors)
who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details
