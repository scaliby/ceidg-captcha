package net.scaliby.ceidgcaptcha.rest.service.impl;

import net.scaliby.ceidgcaptcha.rest.common.MultiLayerNetworkLoader;
import net.scaliby.ceidgcaptcha.rest.common.OutputLabeler;
import net.scaliby.ceidgcaptcha.rest.converter.BufferedImageToINDArrayConverter;
import net.scaliby.ceidgcaptcha.rest.service.CEIDGCaptchaPredictionService;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

public class CEIDGCaptchaPredictionServiceImpl implements CEIDGCaptchaPredictionService {

    private final MultiLayerNetworkLoader multiLayerNetworkLoader;
    private final BufferedImageToINDArrayConverter bufferedImageToINDArrayConverter;
    private final OutputLabeler outputLabeler;

    @Inject
    public CEIDGCaptchaPredictionServiceImpl(MultiLayerNetworkLoader multiLayerNetworkLoader,
                                             BufferedImageToINDArrayConverter bufferedImageToINDArrayConverter,
                                             OutputLabeler outputLabeler) {
        this.multiLayerNetworkLoader = multiLayerNetworkLoader;
        this.bufferedImageToINDArrayConverter = bufferedImageToINDArrayConverter;
        this.outputLabeler = outputLabeler;
    }

    @Override
    public synchronized String predict(BufferedImage bufferedImage) {
        INDArray input = bufferedImageToINDArrayConverter.convert(bufferedImage);
        MultiLayerNetwork network = multiLayerNetworkLoader.load();
        INDArray output = network.output(input);
        return outputLabeler.getLabel(output);
    }

}
