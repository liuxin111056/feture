我们在刚开始学习 Java 语言的时候讲过，面向对象的三大特征就是封装，继承，和多态。在 Java 中，要保证封装性，需要将成员变量私有化，对外提供 set/get 方法来访问，虽然现在的 IDE，像 eclipse，IDEA都提供了快捷键，来生成 set/get 方法。

但是在做项目的时候，一个 JavaBean 往往会有很多的成员变量，一个变量对应两个方法，如果有10几个成员变量，那么会对应20多个方法，也许还要去写构造器、equals 等方法，而且需要维护。这样一来，会使代码变得非常冗余，这些显得很冗长也没有太多技术含量，一旦修改属性，就容易出现忘记修改对应方法的失误。

我在看大佬的项目的源码的时候，看到他们的代码中都没有 set/get 方法，取而代之的是在 JavaBean 上标注的注解，我感到非常的好奇，原来他们是用了一种叫做 Lombok 的插件，便去详细了解了这个插件。

Lombok 背景介绍

官方介绍如下：

Project Lombok makes java a spicier language by adding 'handlers' that know how to build and compile simple, boilerplate-free, not-quite-java code.
大致意思是 Lombok 通过增加一些“处理程序”，可以让 Java 变得简洁、快速。

Lombok 使用方法

Lombok 能通过注解的方式，在编译时自动为属性生成构造器、getter/setter、equals、hashcode、toString 方法。出现的神奇就是在源码中没有 getter 和 setter 方法，但是在编译生成的字节码文件中有 getter 和 setter 方法。这样就省去了手动重建这些代码的麻烦，使代码看起来更简洁些。

Lombok 的使用跟引用 jar 包一样，可以在官网（https://projectlombok.org/download）下载 jar 包，也可以使用 maven 添加依赖：

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.10</version>
    <scope>provided</scope>
</dependency>
注意：
第一次使用 Lombok 插件需要做如下几步配置
1.将 Lombok 插件安装到 IDEA

file -> setting


选中 Plugins，搜索 Lombok，点击 Install



2.选择默认的编译方式为 javac，因为 eclipse 是不支持 Lombok 的编译方式的，javac 支持 Lombok 的编译方式。



3.打开注解生成器 Enable annotation processing 



再次注意：

IntelliJ IDEA 2019.2（也就是我用的版本）默认是不支持 Lombok 插件的，需要去

https://plugins.jetbrains.com/plugin/6317-lombok/versions
下载对应版本的插件，然后手动引入，在 IDEA 中选择 File -> Setting -> plugins 找到 Install Plugin from Disk…（注意版本不同位置可能有所差异）



接下来我们来分析 Lombok 中注解的具体用法

@Data

@Data 注解在类上，会为类的所有属性自动生成 setter/getter、equals、canEqual、hashCode、toString 方法，如为 final 属性，则不会为该属性生成 setter 方法。

比如我们写一个学生类

@Data
public class Student {
    private String name;
    private Integer age;
    private Integer id;
    private String major;
}


这样就可以调用 set/get 方法了。

@Getter/@Setter

如果觉得@Data 太过残暴（因为@Data 集合了@ToString、@EqualsAndHashCode、@Getter/@Setter、@RequiredArgsConstructor 的所有特性）不够精细，可以使用@Getter/@Setter 注解，此注解在属性上，可以为相应的属性自动生成 set/get 方法。

public class Student {
    @Setter private String name;
    private Integer age;
    private Integer id;
    private String major;

    public static void main(String[] args) {
        Student stu = new Student();
        stu.setName("Mr.ml");
    }
}
@NonNull

该注解用在属性或构造器上，Lombok 会生成一个非空的声明，可用于校验参数，能帮助避免空指针。

public class Student {
    @Setter private String name;
    private Integer age;
    private Integer id;
    private String major;

    public Student(@NonNull String name) {
        this.name = name;
    }
}
@Cleanup

该注解能帮助我们自动调用 close() 方法，很大的简化了代码。

public class CleanupExample {
    public static void main(String[] args) throws IOException {
        @Cleanup InputStream in = new FileInputStream(args[0]);
        @Cleanup OutputStream out = new FileOutputStream(args[1]);
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
              if (r == -1) break;
              out.write(b, 0, r);
        }
      }
}
@EqualsAndHashCode

默认情况下，会使用所有非静态（non-static）和非瞬态（non-transient）属性来生成 equals 和 hashCode，也能通过 exclude 注解来排除一些属性。

@EqualsAndHashCode(exclude={"id", "shape"})
public class EqualsAndHashCodeExample {
    private transient int transientVar = 10;
    private String name;
    private double score;
    private Shape shape = new Square(5, 10);
    private String[] tags;
    private int id;

    public String getName() {
        return this.name;
    }

    @EqualsAndHashCode(callSuper=true)
    public static class Square extends Shape {
        private final int width, height;

        public Square(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
@ToString

类使用@ToString 注解，Lombok 会生成一个 toString() 方法，默认情况下，会输出类名、所有属性（会按照属性定义顺序），用逗号来分割。

通过将 includeFieldNames 参数设为 true，就能明确的输出 toString() 属性。这一点是不是有点绕口，通过代码来看会更清晰些。

@ToString(exclude="id")
public class ToStringExample {
    private static final int STATIC_VAR = 10;
    private String name;
    private Shape shape = new Square(5, 10);
    private String[] tags;
    private int id;

    public String getName() {
        return this.getName();
    }

    @ToString(callSuper=true, includeFieldNames=true)
    public static class Square extends Shape {
        private final int width, height;

        public Square(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor
无参构造器、部分参数构造器、全参构造器。Lombok没法实现多种参数构造器的重载。

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructorExample<T> {
    private int x, y;
    @NonNull private T description;

    @NoArgsConstructor
    public static class NoArgsExample {
        @NonNull private String field;
    }
}
