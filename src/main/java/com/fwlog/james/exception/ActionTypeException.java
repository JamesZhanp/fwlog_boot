package com.fwlog.james.exception;

/**
 * Created by 16255 on 2017/07/11.
 * @author ujamesZhan
 */
public class ActionTypeException extends RuntimeException{
    public ActionTypeException(){
        super("log's action is 0 or 1");
    }
}
