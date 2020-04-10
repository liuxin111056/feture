
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: 定义一个MyAnnotation类，其中定义validate方法，用来实现校验的处理逻辑
 * @description:
 * @author:
 * @create: 2019-01-16 14:25
 **/
public class MyAnnotation {

    public static <T> StringBuilder validate(T t){
        List<ValidateResult> validateResults = new ArrayList<>();
        Field[] fields = t.getClass().getDeclaredFields();

        for (Field field:fields) {
            //非空校验
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

            //非数字校验
            if (field.isAnnotationPresent(NotNum.class)) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (!isNumeric(String.valueOf(value))) {
                    NotNum notNull = field.getAnnotation(NotNum.class);
                    ValidateResult validateResult = new ValidateResult();
                    validateResult.setMessage(notNull.fileName()+"必须为数字");
                    validateResults.add(validateResult);
                }
            }

        }


        StringBuilder str = new StringBuilder();
        for (ValidateResult va : validateResults) {
            if (!va.isValid()) {
                str.append(va.getMessage()).append(",");
            }
        }
        return str;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
