# struts2-freeroute-plugin

自由的映射路由，像 Spring MVC 那样

## 引用

```xml
<dependency>
    <groupId>org.apache.struts</groupId>
    <artifactId>struts2-freeroute-plugin</artifactId>
    <version>${version}</version>
</dependency>
```

## 例子

struts.xml
```xml
<struts>
    <constant name="struts.freeroute.controllerPackage" value="com.example"/>
</struts>
```

com.example.BookController.java
```java
public class BookController{
    private int id;
    private Book book;

    @Route("/books/{id}")
    public String show(){
        book = bookDao.find(id);
        return "dispatcher:/book.jsp"
    }

    //setter, getter
}
```

webapp/book.jsp
```jsp
<!DOCTYPE html>
<html>
    <head>
        <title>show book</title>
    </head>
    <body>
        <h1>Hello book</h1>
    </body>
</html>
```

访问 "/books/13"， 则会显示 book.jsp 的内容

## 配置

* struts.freeroute.controllerPackage

配置 Controller 所在的包(必须)

struts.xml
```xml
<constant name="struts.freeroute.controllerPackage" value="com.example"/>
```

* struts.freeroute.defaultParentPackage

配置默认的父包(可选)

struts.xml
```xml
<struts>
    <constant name="struts.freeroute.defaultParentPackage" value="my-struts"/>

    <package name="my-struts" extends="struts-default">
    </package>
</struts>
```

## 说明

配置`struts.freeroute.controllerPackage`后, 会在此包中搜索以`Controller`结尾的类做为 Controller,
并在其中查找所有`@Route` 注解的方法做为路由映射.

### 路由映射

在`Controller`任意方法上加`@Route`注解则表示一条路由.

例如：
```java
public MyController{
    @Route("/helloworld")
    public String hello(){
        return "dispatcher:/world.html";
    }
}
```
#### 路径

`http://HOST:PORT/helloworld` => `/helloworld`
```java
@Route("/helloworld")
```

`http://HOST:PORT/nested/path/mapping` => `/nested/path/mapping`
```java
@Route("/nested/path/mapping")
```

`http://HOST:PORT/users/1013` => `/users/{id}`
```java
// {id} 变量值将会赋
private id;

@Route("/users/{id}")
public String user(){
    //...
}

// setter, getter
```

#### HTTP method

#### HTTP 参数

### 返回页面

`Controller.routeMethod` 方法的返回值将决定返回的结果类型和页面路径, 如`dispatcher:/example.html`.

目前支持三种类型:

+ dispatcher
    * html
    * jsp
+ velocity
+ freemarker

free mapping route just like Spring MVC

## Use

```xml
<dependency>
    <groupId>org.apache.struts</groupId>
    <artifactId>struts2-freeroute-plugin</artifactId>
    <version>${version}</version>
</dependency>
```