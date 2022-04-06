package ru.borisof.hessianman.utils;

public abstract class ExceptionUtils {

    public static String getRecursiveCauseMessages(Throwable throwable, int depth) {
        if (depth == 0) return "";
        return (throwable.getMessage() != null ? throwable.getMessage() + "; " : "") +
               getRecursiveCauseMessages(throwable.getCause(), depth - 1);
    }

}
