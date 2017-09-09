package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CookieParser;
import net.scaliby.ceidgcaptcha.downloader.common.SessionIdParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionIdParserImpl implements SessionIdParser {

    private final CookieParser cookieParser;

    @Inject
    public SessionIdParserImpl(CookieParser cookieParser) {
        this.cookieParser = cookieParser;
    }

    @Override
    public Optional<String> parseSessionId(HttpResponse httpResponse) {
        List<String> cookies = Arrays.stream(httpResponse.getHeaders("Set-Cookie"))
                .map(Header::getValue)
                .collect(Collectors.toList());
        Map<String, String> parsedCookies = cookieParser.parseSetCookieStrings(cookies);
        return Optional.ofNullable(parsedCookies.get("ASP.NET_SessionId"));
    }
}
