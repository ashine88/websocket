package com.zank.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zank.rest.response.Result;


public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(StatusCode.class);

    public static Result error(int errorCode) {
        Result result = new Result();
        result.setCode(errorCode).setMessage(StatusCode.codeMap.get(errorCode));
        return result;
    }

    public static Result error(int errorCode,String parameter) {
        Result result = new Result()
        					.setCode(errorCode)
        					.setMessage(String.format(StatusCode.codeMap.get(errorCode), parameter));
        return result;
    }
}
