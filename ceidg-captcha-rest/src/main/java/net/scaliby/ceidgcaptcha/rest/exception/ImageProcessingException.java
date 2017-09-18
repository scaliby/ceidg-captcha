package net.scaliby.ceidgcaptcha.rest.exception;

public class ImageProcessingException extends RuntimeException {
    public ImageProcessingException() {
    }

    public ImageProcessingException(String message) {
        super(message);
    }

    public ImageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageProcessingException(Throwable cause) {
        super(cause);
    }

    public ImageProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
