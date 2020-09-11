

/**
 * @program: demo
 * @description:
 * @author: wwh
 * @create: 2019-01-16 15:32
 **/
public class MyTest {
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        //person.setName("张飞");
        person.setAge("128");
        StringBuilder validate = MyAnnotation.validate(person);

        if (validate.toString().contains(",")){
            throw new Exception(validate.toString()+"123");
        }
        System.out.println("12345");
    }
}

