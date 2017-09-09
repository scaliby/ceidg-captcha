package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CaptchaIdParser;
import net.scaliby.ceidgcaptcha.downloader.factory.JsoupDocumentFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;

public class CaptchaIdParserImpl implements CaptchaIdParser {

    private final JsoupDocumentFactory jsoupDocumentFactory;

    @Inject
    public CaptchaIdParserImpl(JsoupDocumentFactory jsoupDocumentFactory) {
        this.jsoupDocumentFactory = jsoupDocumentFactory;
    }

    @Override
    public Optional<String> parseCaptchaId(HttpResponse httpResponse) {
        try {
            InputStream content = httpResponse.getEntity().getContent();
            Document document = jsoupDocumentFactory.create(content);
            Element captchaImg = document.getElementById("MainContent_ctrlCaptcha2");
            String url = captchaImg.attr("src").replace("\r\n", "");
            URIBuilder uriBuilder = new URIBuilder(url);

            return uriBuilder.getQueryParams().stream()
                    .filter(nameValuePair -> nameValuePair.getName().equals("id"))
                    .map(NameValuePair::getValue)
                    .findAny();
        } catch (IOException | URISyntaxException ex) {
            return Optional.empty();
        }
    }
}
