package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.ImageParser;
import net.scaliby.ceidgcaptcha.downloader.exception.CEIDGHttpException;
import org.apache.http.HttpResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageParserImpl implements ImageParser {
    @Override
    public BufferedImage parseImage(HttpResponse httpResponse) {
        try {
            return ImageIO.read(httpResponse.getEntity().getContent());
        } catch (IOException e) {
            throw new CEIDGHttpException(e);
        }
    }

}
