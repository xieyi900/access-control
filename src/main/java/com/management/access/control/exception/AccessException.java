package com.management.access.control.exception;

public class AccessException extends BaseException{

    public AccessException(int code) {
        this.setErrCode(code);
    }

    public AccessException(String msg) {
        this.setErrMsg(msg);
    }

    public AccessException(int code, String msg){
        super(code,msg);
    }


}
