package net.scaliby.ceidgcaptcha.downloader.resource;

import lombok.Data;

@Data
public class CEIDGCaptchaSessionResource {
    private String sessionId;
    private String captchaId;
}
