package net.scaliby.ceidgcaptcha.downloader.common;

import org.apache.http.HttpResponse;

import java.util.Optional;

public interface CaptchaIdParser {

    Optional<String> parseCaptchaId(HttpResponse httpResponse);

}
