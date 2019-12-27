package com.itheima.travel.exception;

/**
 * 用户自定义的异常，起到语义的作用
 */
public class CustomerMsgException extends Exception{
    public CustomerMsgException(String message) {
        super(message);
    }
}
