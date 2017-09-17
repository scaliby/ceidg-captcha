package net.scaliby.ceidgcaptcha.common.exception;

public class ImageStoreException extends RuntimeException {

    public ImageStoreException() {
    }

    public ImageStoreException(String message) {
        super(message);
    }

    public ImageStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageStoreException(Throwable cause) {
        super(cause);
    }

    public ImageStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
