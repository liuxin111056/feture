import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Persion {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        List<Persion> p=new ArrayList<Persion>();
        Persion p1=new Persion();
        p1.setName("p1");
        p1.setAge(4);

        Persion p2=new Persion();
        p2.setName("p2");
        p2.setAge(3);

        Persion p3=new Persion();
        p3.setName("p3");
        p3.setAge(6);

        Persion p4=new Persion();
        p4.setName("p4");
        p4.setAge(9);
        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        //升序
        p.sort((t1,t2) -> Integer.valueOf(t1.getAge()).compareTo(Integer.valueOf(t2.getAge())));
        for (int i=0;i<p.size();i++){
            System.out.println(p.get(i).getAge());
        }
    }
}
