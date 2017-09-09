package net.scaliby.ceidgcaptcha.downloader.service.impl;

import lombok.extern.log4j.Log4j;
import net.scaliby.ceidgcaptcha.downloader.common.CEIDGClient;
import net.scaliby.ceidgcaptcha.downloader.common.CaptchaLabeler;
import net.scaliby.ceidgcaptcha.downloader.common.CaptchaStoreChooser;
import net.scaliby.ceidgcaptcha.downloader.common.ImageWriter;
import net.scaliby.ceidgcaptcha.downloader.exception.CaptchaLabelingException;
import net.scaliby.ceidgcaptcha.downloader.exception.ImageStoreException;
import net.scaliby.ceidgcaptcha.downloader.factory.ImageStoreFactory;
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource;
import net.scaliby.ceidgcaptcha.downloader.service.CaptchaDownloaderService;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;

@Log4j
public class CaptchaDownloaderServiceImpl implements CaptchaDownloaderService {

    private final CaptchaLabeler captchaLabeler;
    private final CaptchaStoreChooser captchaStoreChooser;
    private final CEIDGClient ceidgClient;
    private final ImageStoreFactory imageStoreFactory;
    private final ImageWriter imageWriter;

    @Inject
    public CaptchaDownloaderServiceImpl(CaptchaLabeler captchaLabeler, CaptchaStoreChooser captchaStoreChooser, CEIDGClient ceidgClient,
                                        ImageStoreFactory imageStoreFactory, ImageWriter imageWriter) {
        this.captchaLabeler = captchaLabeler;
        this.captchaStoreChooser = captchaStoreChooser;
        this.ceidgClient = ceidgClient;
        this.imageStoreFactory = imageStoreFactory;
        this.imageWriter = imageWriter;
    }

    public void downloadCaptchaImages(int imagesCount, String format) {
        CEIDGCaptchaSessionResource session = ceidgClient.generateSession();
        File imageStore = getStore(session);
        downloadImages(imagesCount, format, imageStore, session);
    }

    private File getStore(CEIDGCaptchaSessionResource session) {
        File storeBasePath = captchaStoreChooser.getDirectory()
                .orElseThrow(() -> new ImageStoreException("No store selected"));

        BufferedImage imageToLabel = ceidgClient.getCaptchaImage(session);
        String label = captchaLabeler.getLabel(imageToLabel)
                .orElseThrow(() -> new CaptchaLabelingException("No label provided"));
        return imageStoreFactory.createImageStore(storeBasePath, label);
    }

    private void downloadImages(int imagesCount, String format, File imageStore, CEIDGCaptchaSessionResource session) {
        for (int i = 0; i < imagesCount; i++) {
            log.info("Downloading image " + (i + 1) + " of " + imagesCount);
            BufferedImage downloadedImage = ceidgClient.getCaptchaImage(session);
            File imagePath = new File(imageStore, String.valueOf(i) + "." + format);
            imageWriter.write(downloadedImage, format, imagePath);
        }
    }

}
