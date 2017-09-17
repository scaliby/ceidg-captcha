package net.scaliby.ceidgcaptcha.downloader.service;

public interface CaptchaDownloaderService {
    void downloadCaptchaImages(int imagesCount, String format);
}
