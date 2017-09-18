package net.scaliby.ceidgcaptcha.rest.converter;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.awt.image.BufferedImage;

public interface BufferedImageToINDArrayConverter {

    INDArray convert(BufferedImage bufferedImage);

}
