package net.scaliby.ceidgcaptcha.common.common.impl;

import net.scaliby.ceidgcaptcha.common.common.StoreChooser;
import net.scaliby.ceidgcaptcha.common.factory.JFileChooserFactory;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;
import java.util.Optional;

public class SwingStoreChooser implements StoreChooser {

    private final JFileChooserFactory jFileChooserFactory;

    @Inject
    public SwingStoreChooser(JFileChooserFactory jFileChooserFactory) {
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
