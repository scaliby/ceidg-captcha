package net.scaliby.ceidgcaptcha.rest;

import com.google.inject.AbstractModule;
import net.scaliby.ceidgcaptcha.machinelearning.factory.ImageTransformFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.impl.ImageTransformFactoryImpl;
import net.scaliby.ceidgcaptcha.machinelearning.provider.RandomProvider;
import net.scaliby.ceidgcaptcha.rest.common.MultiLayerNetworkLoader;
import net.scaliby.ceidgcaptcha.rest.common.OutputLabeler;
import net.scaliby.ceidgcaptcha.rest.common.impl.MultiLayerNetworkLoaderImpl;
import net.scaliby.ceidgcaptcha.rest.common.impl.OutputLabelerImpl;
import net.scaliby.ceidgcaptcha.rest.converter.BufferedImageToINDArrayConverter;
import net.scaliby.ceidgcaptcha.rest.converter.ByteArrayToBufferedImageConverter;
import net.scaliby.ceidgcaptcha.rest.converter.impl.BufferedImageToINDArrayConverterImpl;
import net.scaliby.ceidgcaptcha.rest.converter.impl.ByteArrayToBufferedImageConverterImpl;
import net.scaliby.ceidgcaptcha.rest.factory.LabelsFactory;
import net.scaliby.ceidgcaptcha.rest.factory.impl.PropertiesLabelsFactory;
import net.scaliby.ceidgcaptcha.rest.handler.CEIDGCaptchaPredictionHandler;
import net.scaliby.ceidgcaptcha.rest.service.CEIDGCaptchaPredictionService;
import net.scaliby.ceidgcaptcha.rest.service.impl.CEIDGCaptchaPredictionServiceImpl;

import java.util.Random;

public class CEIDGCaptchaRestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Random.class).toProvider(RandomProvider.class);
        bind(ByteArrayToBufferedImageConverter.class).to(ByteArrayToBufferedImageConverterImpl.class);
        bind(BufferedImageToINDArrayConverter.class).to(BufferedImageToINDArrayConverterImpl.class);
        bind(ImageTransformFactory.class).to(ImageTransformFactoryImpl.class);
        bind(CEIDGCaptchaPredictionService.class).to(CEIDGCaptchaPredictionServiceImpl.class);
        bind(OutputLabeler.class).to(OutputLabelerImpl.class);
        bind(MultiLayerNetworkLoader.class).to(MultiLayerNetworkLoaderImpl.class);
        bind(LabelsFactory.class).to(PropertiesLabelsFactory.class);
        bind(CEIDGCaptchaPredictionHandler.class);
    }
}
