package cn.cr.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private boolean hasErrors = false;
    //存放错误信息的Map
    private Map<String, String> errorMessageMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMessageMap() {
        return errorMessageMap;
    }

    public void setErrorMessageMap(Map<String, String> errorMessageMap) {
        this.errorMessageMap = errorMessageMap;
    }

    //实现通用的通过格式化字符信息获取错误结果的msg方法
    public String getErrorMessage(){
        return StringUtils.join(errorMessageMap.values().toArray(), ",");
    }
}
