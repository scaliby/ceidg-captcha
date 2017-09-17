package net.scaliby.ceidgcaptcha.machinelearning;

import com.google.inject.AbstractModule;
import net.scaliby.ceidgcaptcha.machinelearning.common.MultiLayerNetworkSaver;
import net.scaliby.ceidgcaptcha.machinelearning.common.impl.MultiLayerNetworkSaverImpl;
import net.scaliby.ceidgcaptcha.machinelearning.factory.DataSetIteratorFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.ImageTransformFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerConfigurationFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerNetworkFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.impl.DataSetIteratorFactoryImpl;
import net.scaliby.ceidgcaptcha.machinelearning.factory.impl.ImageTransformFactoryImpl;
import net.scaliby.ceidgcaptcha.machinelearning.factory.impl.MultiLayerConfigurationFactoryImpl;
import net.scaliby.ceidgcaptcha.machinelearning.factory.impl.MultiLayerNetworkFactoryImpl;
import net.scaliby.ceidgcaptcha.machinelearning.provider.RandomProvider;
import net.scaliby.ceidgcaptcha.machinelearning.service.InputSplitService;
import net.scaliby.ceidgcaptcha.machinelearning.service.MachineLearningService;
import net.scaliby.ceidgcaptcha.machinelearning.service.impl.InputSplitServiceImpl;
import net.scaliby.ceidgcaptcha.machinelearning.service.impl.MachineLearningServiceImpl;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.io.labels.PathLabelGenerator;

import java.util.Random;

public class CEIDGCaptchaMachineLearningModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PathLabelGenerator.class).toInstance(new ParentPathLabelGenerator());
        bind(Random.class).toProvider(RandomProvider.class);
        bind(DataSetIteratorFactory.class).to(DataSetIteratorFactoryImpl.class);
        bind(InputSplitService.class).to(InputSplitServiceImpl.class);
        bind(ImageTransformFactory.class).to(ImageTransformFactoryImpl.class);
        bind(MultiLayerConfigurationFactory.class).to(MultiLayerConfigurationFactoryImpl.class);
        bind(MachineLearningService.class).to(MachineLearningServiceImpl.class);
        bind(MultiLayerNetworkFactory.class).to(MultiLayerNetworkFactoryImpl.class);
        bind(MultiLayerNetworkSaver.class).to(MultiLayerNetworkSaverImpl.class);
    }
}
