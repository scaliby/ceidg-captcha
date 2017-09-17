package net.scaliby.ceidgcaptcha.machinelearning.factory;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public interface MultiLayerNetworkFactory {

    MultiLayerNetwork create(MultiLayerConfiguration multiLayerConfiguration);

}
