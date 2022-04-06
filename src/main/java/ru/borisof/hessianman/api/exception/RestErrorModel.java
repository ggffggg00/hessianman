package ru.borisof.hessianman.api.exception;

import java.util.Date;

public class RestErrorModel {

    private long timestamp;
    private String message;
    private String cause;

    public RestErrorModel(final long timestamp, final String message, final String cause) {
        this.timestamp = timestamp;
        this.message = message;
        this.cause = cause;
    }

    public static RestErrorModel of(String message, String cause) {
        return new RestErrorModel(new Date().getTime(), message, cause);
    }

}
