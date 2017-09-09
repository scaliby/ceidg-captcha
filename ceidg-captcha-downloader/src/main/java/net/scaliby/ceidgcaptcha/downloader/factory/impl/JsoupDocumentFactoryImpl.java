package net.scaliby.ceidgcaptcha.downloader.factory.impl;

import net.scaliby.ceidgcaptcha.downloader.factory.JsoupDocumentFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

public class JsoupDocumentFactoryImpl implements JsoupDocumentFactory {
    @Override
    public Document create(InputStream inputStream) throws IOException {
        return Jsoup.parse(inputStream, "UTF-8", "");
    }
}
