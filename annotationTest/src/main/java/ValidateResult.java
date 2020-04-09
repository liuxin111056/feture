

/**
 * @program: 定义一个通用校验返回结果类
 * @description:
 * @author: wwh
 * @create: 2019-01-16 14:28
 **/
public class ValidateResult {
    private boolean isValid = true;
    private String message;

    public ValidateResult() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.isValid = false;
        this.message = message;
    }

    public boolean isValid() {
        return this.isValid;
    }
}


