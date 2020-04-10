import lombok.Data;
import lombok.NonNull;

@Data
public class Student {
    private String name;
    private Integer age;
    private Integer id;
    private String major;
    public Student(@NonNull String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Student t=new Student("123");
        t.setAge(10);
        t.setName("112");
        tr(null,11);

    }
    public static void tr(@NonNull String name, int age){
        System.out.println(age);
    }
}
