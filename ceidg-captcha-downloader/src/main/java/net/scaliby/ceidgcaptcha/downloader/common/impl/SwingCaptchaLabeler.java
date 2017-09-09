package net.scaliby.ceidgcaptcha.downloader.common.impl;

import net.scaliby.ceidgcaptcha.downloader.common.CaptchaLabeler;
import net.scaliby.ceidgcaptcha.downloader.exception.CaptchaLabelingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SwingCaptchaLabeler implements CaptchaLabeler {

    private final Object lock = new Object();

    @Override
    public Optional<String> getLabel(BufferedImage bufferedImage) {
        try {
            return getLabelInternal(bufferedImage);
        } catch (InterruptedException e) {
            throw new CaptchaLabelingException(e);
        }
    }

    private Optional<String> getLabelInternal(BufferedImage bufferedImage) throws InterruptedException {
        synchronized (lock) {
            JTextField textField = new JTextField(5);

            JFrame jFrame = createJFrame(Arrays.asList(
                    new JLabel(new ImageIcon(bufferedImage)),
                    new JLabel("Image label"),
                    textField,
                    createButtonWithNotifyCallback()
            ));

            lock.wait();

            String label = textField.getText();

            jFrame.dispose();

            return getResult(label);
        }
    }

    private JButton createButtonWithNotifyCallback() {
        JButton button = new JButton("OK");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                synchronized (lock) {
                    lock.notify();
                }
            }
        });
        return button;
    }

    private JFrame createJFrame(List<Component> components) {
        JFrame jFrame = new JFrame();
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new FlowLayout());
        components.forEach(jFrame::add);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
        return jFrame;
    }

    private Optional<String> getResult(String labelValue) {
        if (labelValue == null || labelValue.equals("")) {
            return Optional.empty();
        }

        return Optional.of(labelValue);
    }

}
