package net.scaliby.ceidgcaptcha.machinelearning.factory.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerNetworkFactory;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;

public class MultiLayerNetworkFactoryImpl implements MultiLayerNetworkFactory {
    @Override
    public MultiLayerNetwork create(MultiLayerConfiguration multiLayerConfiguration) {
        MultiLayerNetwork multiLayerNetwork = new MultiLayerNetwork(multiLayerConfiguration);
        multiLayerNetwork.setListeners(new ScoreIterationListener());
        return multiLayerNetwork;
    }
}
