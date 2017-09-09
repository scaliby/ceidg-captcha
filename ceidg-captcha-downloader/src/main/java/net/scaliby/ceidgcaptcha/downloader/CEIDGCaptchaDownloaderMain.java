package net.scaliby.ceidgcaptcha.downloader;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.scaliby.ceidgcaptcha.downloader.service.impl.CaptchaDownloaderServiceImpl;

import java.io.IOException;

public class CEIDGCaptchaDownloaderMain {

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new CEIDGCaptchaDownloaderModule());
        CaptchaDownloaderServiceImpl captchaDownloaderService = injector.getInstance(CaptchaDownloaderServiceImpl.class);

        captchaDownloaderService.downloadCaptchaImages(1000, "png");
    }

}
