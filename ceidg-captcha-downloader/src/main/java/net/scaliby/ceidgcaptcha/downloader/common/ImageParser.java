package net.scaliby.ceidgcaptcha.downloader.common;

import org.apache.http.HttpResponse;

import java.awt.image.BufferedImage;

public interface ImageParser {

    BufferedImage parseImage(HttpResponse httpResponse);

}
