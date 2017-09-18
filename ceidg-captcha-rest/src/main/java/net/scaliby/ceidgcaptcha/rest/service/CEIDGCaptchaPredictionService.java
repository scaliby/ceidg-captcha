package net.scaliby.ceidgcaptcha.rest.service;

import java.awt.image.BufferedImage;

public interface CEIDGCaptchaPredictionService {

    String predict(BufferedImage bufferedImage);

}
