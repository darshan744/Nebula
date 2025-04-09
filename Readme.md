# 🌀 Nebula
A minimal and lightweight Java-based HTTP framework built from scratch using Java Sockets — giving you full control over the request/response cycle, with routing and middleware support.


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

### 🔧 How to Use

In your `main` method, call `Nebula.start()` to start the server:

```java
public static void main(String[] args) {
    Nebula.start();//Default 7090  can also run in custom port
    //ex : Nebula.start(port);
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