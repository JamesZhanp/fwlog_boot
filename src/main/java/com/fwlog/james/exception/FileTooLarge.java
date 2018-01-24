package com.fwlog.james.exception;

/**
 * Created by 16255 on 2017/7/10.
 * @author jamesZhan
 */
public class FileTooLarge extends Exception{
    public FileTooLarge(){
        super("the log file is so large");
    }
}
