package ru.borisof.hessianman.manifest.exception;

public class ManifestReadException extends RuntimeException{
    public ManifestReadException() {
    }

    public ManifestReadException(final String message) {
        super(message);
    }

    public ManifestReadException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ManifestReadException(final Throwable cause) {
        super(cause);
    }

    public ManifestReadException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static void throwError(String message){
        throw of(message);
    }

    public static void throwError(String message, Throwable th){
        throw of(message, th);
    }

    public static ManifestReadException of(String message) {
        return new ManifestReadException(message);
    }

    public static ManifestReadException of(String message, Throwable th) {
        return new ManifestReadException(message, th);
    }

}
