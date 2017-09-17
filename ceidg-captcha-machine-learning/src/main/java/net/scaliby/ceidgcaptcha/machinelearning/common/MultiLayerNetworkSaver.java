package net.scaliby.ceidgcaptcha.machinelearning.common;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public interface MultiLayerNetworkSaver {

    void save(MultiLayerNetwork network);

}
