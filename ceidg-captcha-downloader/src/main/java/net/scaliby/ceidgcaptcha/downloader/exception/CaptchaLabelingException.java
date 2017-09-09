package net.scaliby.ceidgcaptcha.downloader.exception;

public class CaptchaLabelingException extends RuntimeException {
    public CaptchaLabelingException() {
    }

    public CaptchaLabelingException(String message) {
        super(message);
    }

    public CaptchaLabelingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaLabelingException(Throwable cause) {
        super(cause);
    }

    public CaptchaLabelingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
