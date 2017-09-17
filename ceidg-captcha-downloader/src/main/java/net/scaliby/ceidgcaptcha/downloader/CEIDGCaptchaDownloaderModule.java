package net.scaliby.ceidgcaptcha.downloader;

import com.google.inject.AbstractModule;
import net.scaliby.ceidgcaptcha.downloader.common.ImageWriter;
import net.scaliby.ceidgcaptcha.downloader.common.impl.ImageWriterImpl;
import net.scaliby.ceidgcaptcha.downloader.common.*;
import net.scaliby.ceidgcaptcha.downloader.common.impl.*;
import net.scaliby.ceidgcaptcha.downloader.factory.ImageStoreFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.JsoupDocumentFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.RequestFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.ImageStoreFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.JsoupDocumentFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.RequestFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.service.CaptchaDownloaderService;
import net.scaliby.ceidgcaptcha.downloader.service.impl.CaptchaDownloaderServiceImpl;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class CEIDGCaptchaDownloaderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CEIDGClient.class).to(CEIDGClientImpl.class);
        bind(CookieParser.class).to(CookieParserImpl.class);
        bind(CaptchaLabeler.class).to(SwingCaptchaLabeler.class);
        bind(CaptchaDownloaderService.class).to(CaptchaDownloaderServiceImpl.class);
        bind(CloseableHttpClient.class).toInstance(HttpClientBuilder.create().disableCookieManagement().build());
        bind(CaptchaIdParser.class).to(CaptchaIdParserImpl.class);
        bind(ImageParser.class).to(ImageParserImpl.class);
        bind(ImageWriter.class).to(ImageWriterImpl.class);
        bind(SessionIdParser.class).to(SessionIdParserImpl.class);
        bind(ImageStoreFactory.class).to(ImageStoreFactoryImpl.class);
        bind(RequestFactory.class).to(RequestFactoryImpl.class);
        bind(JsoupDocumentFactory.class).to(JsoupDocumentFactoryImpl.class);
    }
}
