
import java.util.List;

/**
 * @program: demo
 * @description:
 * @author: wwh
 * @create: 2019-01-16 15:32
 **/
public class MyTest {
    public static void main(String[] args) {
        Person person = new Person();
      //  person.setName("张飞");
     //   person.setAge("128");
        StringBuilder validate = MyAnnotation.validate(person);

        System.err.println(validate.toString());
    }
}

