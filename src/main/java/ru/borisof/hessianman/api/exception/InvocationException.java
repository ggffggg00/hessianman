package ru.borisof.hessianman.api.exception;

public class InvocationException extends RuntimeException{

    public InvocationException(final String message) {
        super(message);
    }

    public InvocationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvocationException(final Throwable cause) {
        super(cause);
    }

}
