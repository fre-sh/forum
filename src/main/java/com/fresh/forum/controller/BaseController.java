package com.fresh.forum.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fresh.forum.dto.ResponseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

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

    protected ResponseTO success(Object o){
        ResponseTO success = ResponseTO.success(o);
        try {
            logger.info("response：{}", objectMapper.writeValueAsString(success));
        } catch (Exception e) {
            logger.error("" , e);
        }
        return success;
    }

    protected ResponseTO error(String message) {
        logger.info("errorLog{}", message);
        return ResponseTO.failed(message);
    }

}
