package net.scaliby.ceidgcaptcha.downloader.common;

import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource;

import java.awt.image.BufferedImage;

public interface CEIDGClient {
    CEIDGCaptchaSessionResource generateSession();

    BufferedImage getCaptchaImage(CEIDGCaptchaSessionResource ceidgCaptchaSessionResource);
}
