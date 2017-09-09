package net.scaliby.ceidgcaptcha.downloader.factory.impl;

import net.scaliby.ceidgcaptcha.downloader.exception.ImageStoreException;
import net.scaliby.ceidgcaptcha.downloader.factory.ImageStoreFactory;

import java.io.File;

public class ImageStoreFactoryImpl implements ImageStoreFactory {
    @Override
    public File createImageStore(File basePath, String label) {
        File imageStore = new File(basePath, label);
        if (!imageStore.mkdir()) {
            throw new ImageStoreException("Can't create labelled store");
        }
        return imageStore;
    }
}
