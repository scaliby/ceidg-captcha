package net.scaliby.ceidgcaptcha.downloader.factory.impl;

import net.scaliby.ceidgcaptcha.downloader.factory.JFileChooserFactory;

import javax.swing.*;

public class JFileChooserFactoryImpl implements JFileChooserFactory {
    @Override
    public JFileChooser create() {
        return new JFileChooser();
    }
}
