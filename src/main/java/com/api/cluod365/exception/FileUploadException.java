package com.api.cluod365.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message,Throwable e) {
        super(message,e);
    }
}
