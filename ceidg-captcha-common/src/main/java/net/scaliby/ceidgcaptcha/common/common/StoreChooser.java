package net.scaliby.ceidgcaptcha.common.common;

import java.io.File;
import java.util.Optional;

public interface StoreChooser {
    Optional<File> getDirectory();
}
