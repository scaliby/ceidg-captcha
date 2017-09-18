package net.scaliby.ceidgcaptcha.rest.converter.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.ImageTransformFactory;
import net.scaliby.ceidgcaptcha.rest.converter.BufferedImageToINDArrayConverter;
import net.scaliby.ceidgcaptcha.rest.exception.ImageProcessingException;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.transform.ImageTransform;
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageToINDArrayConverterImpl implements BufferedImageToINDArrayConverter {

    private final ImageTransformFactory imageTransformFactory;

    @Inject
    public BufferedImageToINDArrayConverterImpl(ImageTransformFactory imageTransformFactory) {
        this.imageTransformFactory = imageTransformFactory;
    }

    @Override
    public INDArray convert(BufferedImage bufferedImage) {
        try {
            ImageTransform transform = imageTransformFactory.create();
            return new NativeImageLoader(32, 32, 1, transform).asMatrix(bufferedImage);
        } catch (IOException ex) {
            throw new ImageProcessingException(ex);
        }
    }
}
