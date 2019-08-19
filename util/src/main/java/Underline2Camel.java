import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * liuxin
 * 2019/8/19 0019
 */
public class Underline2Camel {
    /**
     * 把带下划线的字符串转为驼峰命名式
     *    不包含下划线不做处理
     * @param str 要转换的字符串
     * @param smallCamel 是否为小驼峰
     * @return
     */
    private static String underline2Camel(String str, boolean smallCamel)  {
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find() && str.contains("_")) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character
                    .toLowerCase(word.charAt(0)) : Character.toUpperCase(word
                    .charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return "".equals(sb.toString()) ? str : sb.toString();
    }
    @Test
    public void t(){
        String result=underline2Camel("aD_bDc",false);
        System.out.println(result);
    }
}
