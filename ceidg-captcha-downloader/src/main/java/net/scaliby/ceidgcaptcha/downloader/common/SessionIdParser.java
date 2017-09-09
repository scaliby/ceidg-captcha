package net.scaliby.ceidgcaptcha.downloader.common;

import org.apache.http.HttpResponse;

import java.util.Optional;

public interface SessionIdParser {

    Optional<String> parseSessionId(HttpResponse httpResponse);

}
