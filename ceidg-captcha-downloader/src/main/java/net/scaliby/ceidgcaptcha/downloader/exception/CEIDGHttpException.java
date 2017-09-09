package net.scaliby.ceidgcaptcha.downloader.exception;

public class CEIDGHttpException extends RuntimeException {
    public CEIDGHttpException() {
    }

    public CEIDGHttpException(String message) {
        super(message);
    }

    public CEIDGHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public CEIDGHttpException(Throwable cause) {
        super(cause);
    }

    public CEIDGHttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
