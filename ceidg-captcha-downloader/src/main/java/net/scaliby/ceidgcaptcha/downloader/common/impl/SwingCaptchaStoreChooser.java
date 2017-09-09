package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CaptchaStoreChooser;
import net.scaliby.ceidgcaptcha.downloader.factory.JFileChooserFactory;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;
import java.util.Optional;

public class SwingCaptchaStoreChooser implements CaptchaStoreChooser {

    private final JFileChooserFactory jFileChooserFactory;

    @Inject
    public SwingCaptchaStoreChooser(JFileChooserFactory jFileChooserFactory) {
        this.jFileChooserFactory = jFileChooserFactory;
    }

    @Override
    public Optional<File> getDirectory() {
        JFileChooser fileChooser = jFileChooserFactory.create();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return Optional.of(fileChooser.getSelectedFile());
        }
        return Optional.empty();
    }

}
