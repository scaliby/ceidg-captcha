package net.scaliby.ceidgcaptcha.common.factory.impl;

import net.scaliby.ceidgcaptcha.common.factory.JFileChooserFactory;

import javax.swing.*;

public class JFileChooserFactoryImpl implements JFileChooserFactory {
    @Override
    public JFileChooser create() {
        return new JFileChooser();
    }
}
