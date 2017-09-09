package net.scaliby.ceidgcaptcha.downloader.common;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface CaptchaLabeler {
    Optional<String> getLabel(BufferedImage bufferedImage);
}
