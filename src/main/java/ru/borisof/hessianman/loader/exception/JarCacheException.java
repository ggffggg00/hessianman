package ru.borisof.hessianman.loader.exception;

public class JarCacheException extends RuntimeException {

    public JarCacheException() {
        super();
    }

    public JarCacheException(final String message) {
        super(message);
    }

    public JarCacheException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JarCacheException(final Throwable cause) {
        super(cause);
    }

    protected JarCacheException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static JarCacheException of(String message) {
        return new JarCacheException(message);
    }

    public static JarCacheException of(String message, Throwable th) {
        return new JarCacheException(message, th);
    }

}
