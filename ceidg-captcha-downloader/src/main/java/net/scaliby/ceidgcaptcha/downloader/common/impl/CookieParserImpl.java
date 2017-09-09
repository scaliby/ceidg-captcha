package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CookieParser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CookieParserImpl implements CookieParser {

    @Override
    public Map<String, String> parseSetCookieStrings(Collection<String> input) {
        Map<String, String> result = new HashMap<>();
        for (String header : input) {
            String[] headerParts = header.split(";");
            String firstHeaderPart = headerParts[0];
            String[] firstHeaderPartParts = firstHeaderPart.split("=");
            result.put(firstHeaderPartParts[0], firstHeaderPartParts[1]);
        }
        return result;
    }

    @Override
    public String formatCookieString(Map<String, String> cookies) {
        StringBuilder builder = new StringBuilder();
        cookies.forEach((key, value) -> builder.append(key)
                .append("=")
                .append(value)
                .append("; "));
        String result = builder.toString();
        return result.substring(0, result.length() - 2);
    }

}
