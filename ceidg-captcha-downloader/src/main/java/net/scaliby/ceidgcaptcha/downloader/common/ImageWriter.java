package net.scaliby.ceidgcaptcha.downloader.common;

import java.awt.image.RenderedImage;
import java.io.File;

public interface ImageWriter {

    void write(RenderedImage image, String format, File path);

}
