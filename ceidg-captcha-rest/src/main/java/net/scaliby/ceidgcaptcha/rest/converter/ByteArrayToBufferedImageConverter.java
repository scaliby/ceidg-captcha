package net.scaliby.ceidgcaptcha.rest.converter;

import java.awt.image.BufferedImage;

public interface ByteArrayToBufferedImageConverter {

    BufferedImage convert(byte[] data);

}
