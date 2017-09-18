package net.scaliby.ceidgcaptcha.machinelearning;

import lombok.extern.log4j.Log4j;
import net.scaliby.ceidgcaptcha.machinelearning.common.MultiLayerNetworkSaver;
import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerConfigurationFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerNetworkFactory;
import net.scaliby.ceidgcaptcha.machinelearning.resource.NetworkStatisticsResource;
import net.scaliby.ceidgcaptcha.machinelearning.service.MachineLearningService;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import javax.inject.Inject;

@Log4j
public class CEIDGCaptchaMachineLearningRunner implements Runnable {

    private final MultiLayerConfigurationFactory multiLayerConfigurationFactory;
    private final MachineLearningService machineLearningService;
    private final MultiLayerNetworkFactory multiLayerNetworkFactory;
    private final MultiLayerNetworkSaver multiLayerNetworkSaver;

    @Inject
    public CEIDGCaptchaMachineLearningRunner(MultiLayerConfigurationFactory multiLayerConfigurationFactory,
                                             MachineLearningService machineLearningService,
                                             MultiLayerNetworkFactory multiLayerNetworkFactory,
                                             MultiLayerNetworkSaver multiLayerNetworkSaver) {
        this.multiLayerConfigurationFactory = multiLayerConfigurationFactory;
        this.machineLearningService = machineLearningService;
        this.multiLayerNetworkFactory = multiLayerNetworkFactory;
        this.multiLayerNetworkSaver = multiLayerNetworkSaver;
    }

    @Override
    public void run() {
        MultiLayerConfiguration configuration = multiLayerConfigurationFactory.create();
        MultiLayerNetwork multiLayerNetwork = multiLayerNetworkFactory.create(configuration);

        machineLearningService.train(multiLayerNetwork);

        multiLayerNetworkSaver.save(multiLayerNetwork);

        NetworkStatisticsResource networkStatisticsResource = machineLearningService.test(multiLayerNetwork);

        log.info("Network statistics: " + networkStatisticsResource);
    }
}
