package net.scaliby.ceidgcaptcha.downloader.factory.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CookieParser;
import net.scaliby.ceidgcaptcha.downloader.exception.CEIDGHttpException;
import net.scaliby.ceidgcaptcha.downloader.factory.RequestFactory;
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource;
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGClientEndpointConfigurationResource;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RequestFactoryImpl implements RequestFactory {

    private final CEIDGClientEndpointConfigurationResource ceidgClientEndpointConfigurationResource;
    private final CookieParser cookieParser;

    @Inject
    public RequestFactoryImpl(CEIDGClientEndpointConfigurationResource ceidgClientEndpointConfigurationResource, CookieParser cookieParser) {
        this.ceidgClientEndpointConfigurationResource = ceidgClientEndpointConfigurationResource;
        this.cookieParser = cookieParser;
    }

    @Override
    public HttpUriRequest createGenerateSessionRequest() {
        return new HttpGet(ceidgClientEndpointConfigurationResource.getSessionGenerateUrl());
    }

    @Override
    public HttpUriRequest createGetCaptchaImageRequest(CEIDGCaptchaSessionResource ceidgCaptchaSessionResource) {
        URI uri = buildURI(ceidgCaptchaSessionResource);
        String cookieString = getCookieString(ceidgCaptchaSessionResource);
        HttpUriRequest httpUriRequest = new HttpGet(uri);
        httpUriRequest.setHeader("Cookie", cookieString);
        return httpUriRequest;
    }

    private String getCookieString(CEIDGCaptchaSessionResource ceidgCaptchaSessionResource) {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("ASP.NET_SessionId", ceidgCaptchaSessionResource.getSessionId());
        return cookieParser.formatCookieString(cookies);
    }

    private URI buildURI(CEIDGCaptchaSessionResource ceidgCaptchaSessionResource) {
        try {
            URIBuilder uriBuilder = new URIBuilder(ceidgClientEndpointConfigurationResource.getImageDownloadUrl())
                    .addParameter("part", "0")
                    .addParameter("id", ceidgCaptchaSessionResource.getCaptchaId());
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new CEIDGHttpException(e);
        }
    }

}
