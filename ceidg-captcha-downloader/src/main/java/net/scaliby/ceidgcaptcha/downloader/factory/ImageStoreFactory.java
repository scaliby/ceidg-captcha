package net.scaliby.ceidgcaptcha.downloader.factory;

import java.io.File;

public interface ImageStoreFactory {

    File createImageStore(File basePath, String label);

}
