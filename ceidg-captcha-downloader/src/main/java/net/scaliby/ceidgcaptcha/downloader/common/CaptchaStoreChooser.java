package net.scaliby.ceidgcaptcha.downloader.common;

import java.io.File;
import java.util.Optional;

public interface CaptchaStoreChooser {
    Optional<File> getDirectory();
}
