package ru.borisof.hessianman.loader.exception;

public class StartingUpException extends RuntimeException{
    public StartingUpException(final String message) {
        super(message);
    }

    public static StartingUpException of(String message) {
        return new StartingUpException(message);
    }

    public static StartingUpException defaultMessage() {
        return of("Application is starting up");
    }

}
