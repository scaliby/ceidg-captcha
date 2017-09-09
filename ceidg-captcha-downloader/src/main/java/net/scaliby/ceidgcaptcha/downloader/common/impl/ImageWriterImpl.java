package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.ImageWriter;
import net.scaliby.ceidgcaptcha.downloader.exception.ImageStoreException;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriterImpl implements ImageWriter {
    @Override
    public void write(RenderedImage image, String format, File path) {
        try {
            ImageIO.write(image, format, path);
        } catch (IOException e) {
            throw new ImageStoreException(e);
        }
    }
}
