# Maven Parameter注解说明
## 注解说明
```java
public @interface Parameter {

    String name() default "";

    String alias() default "";

    String property() default "";

    String defaultValue() default "";

    boolean required() default false;

    boolean readonly() default false;
}
```
默认值(defaultValue)可以通过${}从属性中取值,配置中的值,大于属性中指定的值,优先级关系为
execution configuration > configurationn > property > defaultValue
## 测试用例
插件代码
```java
@Mojo( name = "echo", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class EchoMojo extends AbstractMojo {

    @Parameter( defaultValue = "${alias}", property = "alias1", required = true )
    private String alias;

    public void execute() throws MojoExecutionException {
        getLog().info("echo:"+alias);
    }
}
```
测试项目配置
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>cn.vanillazi.learn</groupId>
                <artifactId>echo-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <alias>alias-in-configuration</alias>
                </configuration>
                <executions>
                    <execution>
                        <id>echo-1</id>
                        <goals>
                            <goal>echo</goal>
                        </goals>
                        <configuration>
                            <alias>alias-in-execution-configuration</alias>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
测试
```shell
 mvn process-sources
 [INFO] --- echo-maven-plugin:1.0-SNAPSHOT:echo (echo-1) @ example ---
[INFO] echo:alias-in-execution-configuration

```
```shell
mvn echo-maven-plugin:echo -Dalias1=vanillazi-in-d
[INFO] --- echo-maven-plugin:1.0-SNAPSHOT:echo (default-cli) @ example ---
[INFO] echo:alias-in-configuration
```
当不存在配置时
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>cn.vanillazi.learn</groupId>
                <artifactId>echo-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <id>echo-1</id>
                        <goals>
                            <goal>echo</goal>
                        </goals>
                        <configuration>
                            <alias>alias-in-execution-configuration</alias>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
```shell
mvn echo-maven-plugin:echo -Dalias1=vanillazi-in-d
[INFO] --- echo-maven-plugin:1.0-SNAPSHOT:echo (default-cli) @ example ---
[INFO] echo:vanillazi-in-d

```
存在properties配置时,-D指定的属性会覆盖Pom中的属性
```xml
<properties>
        <alias>vanillazi-in-properties</alias>
    </properties>
    <build>
        <plugins>
            <plugin>
                <groupId>cn.vanillazi.learn</groupId>
                <artifactId>echo-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <id>echo-1</id>
                        <goals>
                            <goal>echo</goal>
                        </goals>
                        <configuration>
                            <alias>alias-in-execution-configuration</alias>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
测试
```shell
 mvn echo-maven-plugin:echo                        
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< cn.vanillazi.learn:example >---------------------
[INFO] Building example 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- echo-maven-plugin:1.0-SNAPSHOT:echo (default-cli) @ example ---
[INFO] echo:vanillazi-in-properties
[INFO] ------------------------------------------------------------------------

```
```shell
mvn echo-maven-plugin:echo -Dalias=alias-in-d
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< cn.vanillazi.learn:example >---------------------
[INFO] Building example 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- echo-maven-plugin:1.0-SNAPSHOT:echo (default-cli) @ example ---
[INFO] echo:alias-in-d
[INFO] ------------------------------------------------------------------------

```
```shell
 mvn echo-maven-plugin:echo -Dalias1=alias-in-d
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< cn.vanillazi.learn:example >---------------------
[INFO] Building example 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- echo-maven-plugin:1.0-SNAPSHOT:echo (default-cli) @ example ---
[INFO] echo:alias-in-d
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS

```