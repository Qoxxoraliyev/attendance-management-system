package com.muhammadali.attendance_management_system.exceptions;


public class AuthenticationFailedException extends RuntimeException{

    private final String errorCode;
    private final String details;

    public AuthenticationFailedException(String message){
        super(message);
        this.errorCode="AUTH_FAILED";
        this.details=null;
    }

    public AuthenticationFailedException(String message,String details){
        super(message);
        this.errorCode="AUTH_FAILED";
        this.details=details;
    }

    public AuthenticationFailedException(String message,Throwable cause){
        super(message);
        this.errorCode="AUTH_FAILED";
        this.details=null;
    }

    public AuthenticationFailedException(String message,String errorCode,String details,Throwable throwable){
        super(message);
        this.errorCode=errorCode;
        this.details=details;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }


}
