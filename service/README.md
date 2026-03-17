# GraalVM native image
## Setup
First, you need to use the GraalVM JDK version
```shell
sdk list java | grep installed
```
```shell
sdk use java 25.0.2-graal
```
## Compile
```shell
./mvnw -DskipTests -Pnative native:compile
```
Compile time is long: 03:08 min vs. 5.295 s
## Run
```shell
./mvnw clean -DskipTests -Pnative,native-app native:compile
```
0.36 seconds & 142,112 kB vs. 5.182 second & 430,048 kB!
## Issues
Didn't compile:
```java
    // https://github.com/spring-projects/spring-boot/issues/48240
    @Bean
    JdbcPostgresDialect jdbcDialect() {
        return JdbcPostgresDialect.INSTANCE;
    }
```
Didn't run:
```xml
<profiles>
    <!-- JVM run -->
    <profile>
        <id>JVM</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>

        <dependencies>
            <dependency>
                <groupId>org.springframework.modulith</groupId>
                <artifactId>spring-modulith-actuator</artifactId>
                <scope>runtime</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.modulith</groupId>
                <artifactId>spring-modulith-observability</artifactId>
                <scope>runtime</scope>
            </dependency>
        </dependencies>
    </profile>

    <!-- Native build -->
    <profile>
        <id>native-app</id>
        <!-- no modulith actuator / observability here -->
    </profile>
</profiles>
```