package cn.cr.controller;

import cn.cr.error.BusinessException;
import cn.cr.error.EnumBusinessError;
import cn.cr.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    //定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception e){
        Map<String, Object> responseData = new HashMap<>();
        if(e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            responseData.put("errorCode", businessException.getErrorCode());
            responseData.put("errorMessage", businessException.getErrorMessage());
        }else {
            responseData.put("errorCode", EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMessage", EnumBusinessError.UNKNOWN_ERROR.getErrorMessage());
        }
        return CommonReturnType.create(responseData, "fail");
    }

}
