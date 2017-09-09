package net.scaliby.ceidgcaptcha.downloader;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import net.scaliby.ceidgcaptcha.downloader.common.*;
import net.scaliby.ceidgcaptcha.downloader.common.impl.*;
import net.scaliby.ceidgcaptcha.downloader.factory.ImageStoreFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.JFileChooserFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.JsoupDocumentFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.RequestFactory;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.ImageStoreFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.JFileChooserFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.JsoupDocumentFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.factory.impl.RequestFactoryImpl;
import net.scaliby.ceidgcaptcha.downloader.service.CaptchaDownloaderService;
import net.scaliby.ceidgcaptcha.downloader.service.impl.CaptchaDownloaderServiceImpl;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Properties;

public class CEIDGCaptchaDownloaderModule extends AbstractModule {
    @Override
    protected void configure() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bind(CEIDGClient.class).to(CEIDGClientImpl.class);
        bind(CookieParser.class).to(CookieParserImpl.class);
        bind(CaptchaStoreChooser.class).to(SwingCaptchaStoreChooser.class);
        bind(CaptchaLabeler.class).to(SwingCaptchaLabeler.class);
        bind(CaptchaDownloaderService.class).to(CaptchaDownloaderServiceImpl.class);
        bind(CloseableHttpClient.class).toInstance(HttpClientBuilder.create().disableCookieManagement().build());
        bind(CaptchaIdParser.class).to(CaptchaIdParserImpl.class);
        bind(ImageParser.class).to(ImageParserImpl.class);
        bind(ImageWriter.class).to(ImageWriterImpl.class);
        bind(SessionIdParser.class).to(SessionIdParserImpl.class);
        bind(ImageStoreFactory.class).to(ImageStoreFactoryImpl.class);
        bind(JFileChooserFactory.class).to(JFileChooserFactoryImpl.class);
        bind(RequestFactory.class).to(RequestFactoryImpl.class);
        bind(JsoupDocumentFactory.class).to(JsoupDocumentFactoryImpl.class);
    }
}
