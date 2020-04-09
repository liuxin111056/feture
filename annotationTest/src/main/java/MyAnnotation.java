
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: 定义一个MyAnnotation类，其中定义validate方法，用来实现校验的处理逻辑
 * @description:
 * @author:
 * @create: 2019-01-16 14:25
 **/
public class MyAnnotation {

    public static <T> List<ValidateResult> validate(T t){
        List<ValidateResult> validateResults = new ArrayList<>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field:fields) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value==null) {
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    ValidateResult validateResult = new ValidateResult();
                    validateResult.setMessage(notNull.fileName()+"不能为空");
                    validateResults.add(validateResult);
                }
            }

        }
        return validateResults;
    }
}
