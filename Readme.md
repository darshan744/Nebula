# 🌀 Nebula
A minimal and lightweight Java-based HTTP framework built from scratch using Java Sockets — giving you full control over the request/response cycle, with routing and middleware support.


## 📚 Table of Contents
- [Features](#-features)
- [Requirements](#-requirements)
- [Getting Started](#️-getting-started)
- [Examples](#example)
- [Roadmap](#-roadmap)
- [Author](#-author)


## 🚀 Features
- HTTP Parsing
- Request/Response Lifecycle
- Routing Support
- Middleware Support
- Logging Support using Java Util Logging (JUL)


## 📦 Requirements
- Java 17 or above
- No external dependencies


## ⚙️ Getting Started
ADD this in your pom.xml
```
<dependency>
    <groupId>io.github.darshan744</groupId>
    <artifactId>nebula</artifactId>
    <version>1.0.0</version>
</dependency>
```
### 🔧 How to Use

In your `main` method, call `Nebula.start()` to start the server:

```java
public static void main(String[] args) {
    Nebula.start();//Default: 7090  can also run on a custom port
    //ex : Nebula.start(port);
}
```
## Example

### Hello world

Declare your `class` and Implement `Request Handler Interface` and override `handleRequest()`

```java
class HelloWorldController implements RequestHandler {
    @Override
    public void handleRequest(Request req , Response res) {
        res.addBody(new String("Hello world")).setContentType(ContentType.TEXT_PLAIN);
    }
}
```

### Register Route

In your main application get Router Object and add the Class object to register

```java
    public static void main(String[] args) {
        HelloWorldController controller = new HelloWorldController()
        Router router = Router.getRouter();
        router.registerRoute(HttpMethod.GET , "/hello-world" ,controller);
    }
```

### Register Middleware

Create A class for your middleware and Implement `Middleware` Interface
```java
public class CustomMiddleware implements Middleware{
    @Override
    public void middlewareHandler(Request req, Response res, MiddlewareChain next) {
        System.out.println("HI");
        next.next(req, res, next);
    }
}

public static void main(String[] args) {
    CustomMiddleware middlware = new CustomMiddleware();
    MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();
    registry.registerMiddleware(middlware);
}

```
## 📈Roadmap
- [x] Request/Response cycle
- [x] Routing support 
- [x] Middleware support
- [ ] Support for path variable And Request Param
- [ ] Global Exception
- [ ] Easier Response handlers
- [ ] Default Database Integeration
- [ ] Static file handling
- [ ] Authentication 
- [ ] File logger
- [ ] Custom Configuration



## 📝Author
 Built with ❣️by [@darshan744](http://github.com/darshan744)

 ## 🤝 Contributing
Found a bug or want to suggest a feature? Feel free to [open an issue](https://github.com/darshan744/nebula/issues).
