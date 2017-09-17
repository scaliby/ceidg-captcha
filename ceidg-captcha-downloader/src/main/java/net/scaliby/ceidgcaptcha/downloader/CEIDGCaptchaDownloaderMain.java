package net.scaliby.ceidgcaptcha.downloader;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.scaliby.ceidgcaptcha.common.CEIDGCaptchaCommonModule;
import net.scaliby.ceidgcaptcha.common.CEIDGCaptchaPropertiesModule;

import java.io.IOException;

public class CEIDGCaptchaDownloaderMain {

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(
                new CEIDGCaptchaPropertiesModule("application.properties"),
                new CEIDGCaptchaCommonModule(),
                new CEIDGCaptchaDownloaderModule()
        );
        injector.getInstance(CEIDGCaptchaDownloaderRunner.class).run();
    }

}
