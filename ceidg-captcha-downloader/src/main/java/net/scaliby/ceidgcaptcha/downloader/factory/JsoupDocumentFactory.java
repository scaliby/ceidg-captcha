package net.scaliby.ceidgcaptcha.downloader.factory;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

public interface JsoupDocumentFactory {

    Document create(InputStream inputStream) throws IOException;

}
