package net.scaliby.ceidgcaptcha.downloader.common;

import java.util.Collection;
import java.util.Map;

public interface CookieParser {
    Map<String, String> parseSetCookieStrings(Collection<String> input);

    String formatCookieString(Map<String, String> cookies);
}
