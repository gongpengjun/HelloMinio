
### 问题现象

2022-10-31 17:09:51.448  INFO 4174 --- [           main] com.gongpengjun.HelloMinioApplication    : Starting HelloMinioApplication on localhost with PID 4174 (/Users/gongpengjun/workspace/java/HelloMinio/target/classes started by gongpengjun in /Users/gongpengjun/workspace/java/HelloMinio)
2022-10-31 17:09:51.451  INFO 4174 --- [           main] com.gongpengjun.HelloMinioApplication    : No active profile set, falling back to default profiles: default
2022-10-31 17:09:52.330  INFO 4174 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-10-31 17:09:52.339  INFO 4174 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-10-31 17:09:52.339  INFO 4174 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.41]
2022-10-31 17:09:52.466  INFO 4174 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-10-31 17:09:52.466  INFO 4174 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 967 ms
2022-10-31 17:09:52.536  WARN 4174 --- [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'minioController': Unsatisfied dependency expressed through field 'minioUtils'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'minioUtils': Unsatisfied dependency expressed through field 'minioClient'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'minioClient' defined in class path resource [com/gongpengjun/MinioConfig.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [io.minio.MinioClient]: Factory method 'minioClient' threw exception; nested exception is java.lang.ExceptionInInitializerError
2022-10-31 17:09:52.539  INFO 4174 --- [           main] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
2022-10-31 17:09:52.548  INFO 4174 --- [           main] ConditionEvaluationReportLoggingListener :

Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2022-10-31 17:09:52.554 ERROR 4174 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   :

***************************
APPLICATION FAILED TO START
***************************

Description:

An attempt was made to call a method that does not exist. The attempt was made from the following location:

    io.minio.S3Base.<clinit>(S3Base.java:104)

The following method did not exist:

    okhttp3.RequestBody.create([BLokhttp3/MediaType;)Lokhttp3/RequestBody;

The method's class, okhttp3.RequestBody, is available from the following locations:

    jar:file:/Users/gongpengjun/.m2/repository/com/squareup/okhttp3/okhttp/3.14.9/okhttp-3.14.9.jar!/okhttp3/RequestBody.class

The class hierarchy was loaded from the following locations:

    okhttp3.RequestBody: file:/Users/gongpengjun/.m2/repository/com/squareup/okhttp3/okhttp/3.14.9/okhttp-3.14.9.jar


Action:

Correct the classpath of your application so that it contains a single, compatible version of okhttp3.RequestBody


Process finished with exit code 1


### 问题根因

https://github.com/minio/minio-java/issues/1222

okhttp3 version is overridden by Spring

```shell
o$ mvn dependency:tree -Dincludes=com.squareup.okhttp3
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------------< com.gongpengjun:HelloMinio >---------------------
[INFO] Building HelloMinio 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ HelloMinio ---
[INFO] com.gongpengjun:HelloMinio:jar:0.0.1-SNAPSHOT
[INFO] \- io.minio:minio:jar:8.4.5:compile
[INFO]    \- com.squareup.okhttp3:okhttp:jar:3.14.9:compile
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.563 s
[INFO] Finished at: 2022-10-31T17:13:12+08:00
[INFO] ------------------------------------------------------------------------
```

### 解决方案

在pom.xml中显式指定okhttp3版本4.9.1依赖

```xml
<!-- fix okhttp version issue https://github.com/minio/minio-java/issues/1222 -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.9.1</version>
</dependency>
```

### 效果验证

```shell
o$ mvn dependency:tree -Dincludes=com.squareup.okhttp3
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------------< com.gongpengjun:HelloMinio >---------------------
[INFO] Building HelloMinio 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ HelloMinio ---
[INFO] com.gongpengjun:HelloMinio:jar:0.0.1-SNAPSHOT
[INFO] \- com.squareup.okhttp3:okhttp:jar:4.9.1:compile
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.426 s
[INFO] Finished at: 2022-10-31T17:14:55+08:00
[INFO] ------------------------------------------------------------------------
```
