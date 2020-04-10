
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义属性非数字校验注解
 * @Author:
 * @Date: 2019/1/16 15:19:10
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNum {

    /**
     * 获取参数名
     * @return
     */
    String fileName();
}
