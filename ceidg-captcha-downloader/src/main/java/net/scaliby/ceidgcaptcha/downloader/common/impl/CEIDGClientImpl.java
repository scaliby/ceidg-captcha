package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CEIDGClient;
import net.scaliby.ceidgcaptcha.downloader.common.CaptchaIdParser;
import net.scaliby.ceidgcaptcha.downloader.common.ImageParser;
import net.scaliby.ceidgcaptcha.downloader.common.SessionIdParser;
import net.scaliby.ceidgcaptcha.downloader.exception.CEIDGHttpException;
import net.scaliby.ceidgcaptcha.downloader.factory.RequestFactory;
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CEIDGClientImpl implements CEIDGClient {

    private final CloseableHttpClient closeableHttpClient;
    private final CaptchaIdParser captchaIdParser;
    private final SessionIdParser sessionIdParser;
    private final RequestFactory requestFactory;
    private final ImageParser imageParser;

    @Inject
    public CEIDGClientImpl(CloseableHttpClient closeableHttpClient, CaptchaIdParser captchaIdParser,
                           SessionIdParser sessionIdParser, ImageParser imageParser, RequestFactory requestFactory) {
        this.closeableHttpClient = closeableHttpClient;
        this.captchaIdParser = captchaIdParser;
        this.sessionIdParser = sessionIdParser;
        this.requestFactory = requestFactory;
        this.imageParser = imageParser;
    }

    @Override
    public CEIDGCaptchaSessionResource generateSession() {
        HttpUriRequest httpUriRequest = requestFactory.createGenerateSessionRequest();
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpUriRequest)) {
            return generateSessionInternal(response);
        } catch (IOException e) {
            throw new CEIDGHttpException(e);
        }
    }

    private CEIDGCaptchaSessionResource generateSessionInternal(CloseableHttpResponse response) {
        CEIDGCaptchaSessionResource result = new CEIDGCaptchaSessionResource();

        captchaIdParser.parseCaptchaId(response)
                .ifPresent(result::setCaptchaId);
        sessionIdParser.parseSessionId(response)
                .ifPresent(result::setSessionId);

        return result;
    }

    @Override
    public BufferedImage getCaptchaImage(CEIDGCaptchaSessionResource ceidgCaptchaSessionResource) {
        HttpUriRequest httpUriRequest = requestFactory.createGetCaptchaImageRequest(ceidgCaptchaSessionResource);
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpUriRequest)) {
            return imageParser.parseImage(response);
        } catch (IOException e) {
            throw new CEIDGHttpException(e);
        }
    }


}
