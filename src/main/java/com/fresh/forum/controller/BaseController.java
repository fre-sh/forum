package com.fresh.forum.controller;


import com.fresh.forum.dto.ResponseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 捕捉所有未处理的异常
     * Error 不会进行捕获，需要在filter or aspect 中进行捕获处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseTO handleException(Exception e) {
        logger.info("errorLog", e);
        String errMsg="";
        if(StringUtils.isEmpty(e.getMessage())) {
            errMsg=e.getClass().getSimpleName();
        }else {
            errMsg=e.getMessage();
        }
        return ResponseTO.failed(errMsg);
    }

    protected ResponseTO success() {
        return ResponseTO.success();
    }

    protected ResponseTO success(Object o) {
        return ResponseTO.success(o);
    }

    protected ResponseTO error(String message) {
        logger.info("errorLog{}", message);
        return ResponseTO.failed(message);
    }

}
