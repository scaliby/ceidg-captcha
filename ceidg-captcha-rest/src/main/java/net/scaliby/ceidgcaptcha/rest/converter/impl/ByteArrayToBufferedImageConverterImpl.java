package net.scaliby.ceidgcaptcha.rest.converter.impl;

import net.scaliby.ceidgcaptcha.rest.exception.ImageProcessingException;
import net.scaliby.ceidgcaptcha.rest.converter.ByteArrayToBufferedImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteArrayToBufferedImageConverterImpl implements ByteArrayToBufferedImageConverter {
    @Override
    public BufferedImage convert(byte[] data) {
        try {
            return ImageIO.read(new ByteArrayInputStream(data));
        } catch (IOException e) {
            throw new ImageProcessingException(e);
        }
    }
}
