package net.scaliby.ceidgcaptcha.machinelearning.service;

import net.scaliby.ceidgcaptcha.machinelearning.model.NetworkStatisticsResource;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public interface MachineLearningService {

    void train(MultiLayerNetwork multiLayerNetwork);

    NetworkStatisticsResource test(MultiLayerNetwork multiLayerNetwork);

}
