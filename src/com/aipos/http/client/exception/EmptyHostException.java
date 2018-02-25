package com.aipos.http.client.exception;

/**
 * @author maksim.stelmachonak
 */
public class EmptyHostException extends Exception {
    public EmptyHostException() {
    }

    public EmptyHostException(String message) {
        super(message);
    }

    public EmptyHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyHostException(Throwable cause) {
        super(cause);
    }

    public EmptyHostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
