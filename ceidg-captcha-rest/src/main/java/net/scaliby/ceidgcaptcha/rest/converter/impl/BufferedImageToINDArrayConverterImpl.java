package net.scaliby.ceidgcaptcha.rest.converter.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.ImageTransformFactory;
import net.scaliby.ceidgcaptcha.machinelearning.resource.ImageTransformConfigurationResource;
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
    private final ImageTransformConfigurationResource imageTransformConfigurationResource;

    @Inject
    public BufferedImageToINDArrayConverterImpl(ImageTransformFactory imageTransformFactory, ImageTransformConfigurationResource imageTransformConfigurationResource) {
        this.imageTransformFactory = imageTransformFactory;
        this.imageTransformConfigurationResource = imageTransformConfigurationResource;
    }

    @Override
    public INDArray convert(BufferedImage bufferedImage) {
        try {
            ImageTransform transform = imageTransformFactory.create();
            int height = imageTransformConfigurationResource.getScaledHeight();
            int width = imageTransformConfigurationResource.getScaledWidth();
            int channels = imageTransformConfigurationResource.getChannels();
            return new NativeImageLoader(height, width, channels, transform).asMatrix(bufferedImage);
        } catch (IOException ex) {
            throw new ImageProcessingException(ex);
        }
    }
}
