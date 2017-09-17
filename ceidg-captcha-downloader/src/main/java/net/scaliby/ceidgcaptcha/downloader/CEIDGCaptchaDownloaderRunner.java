package net.scaliby.ceidgcaptcha.downloader;

import lombok.Setter;
import net.scaliby.ceidgcaptcha.downloader.service.CaptchaDownloaderService;

import javax.inject.Inject;

public class CEIDGCaptchaDownloaderRunner implements Runnable {

    private static final String IMAGES_FORMAT = "png";
    private static final int IMAGES_COUNT = 1000;

    private final CaptchaDownloaderService captchaDownloaderService;

    @Setter
    private String imagesFormat = IMAGES_FORMAT;
    @Setter
    private int imagesCount = IMAGES_COUNT;

    @Inject
    public CEIDGCaptchaDownloaderRunner(CaptchaDownloaderService captchaDownloaderService) {
        this.captchaDownloaderService = captchaDownloaderService;
    }

    @Override
    public void run() {
        captchaDownloaderService.downloadCaptchaImages(imagesCount, imagesFormat);
    }
}
