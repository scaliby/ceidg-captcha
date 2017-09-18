package net.scaliby.ceidgcaptcha.rest.common.impl;

import net.scaliby.ceidgcaptcha.rest.common.MultiLayerNetworkLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import java.io.IOException;

public class MultiLayerNetworkLoaderImpl implements MultiLayerNetworkLoader {

    private final Object lock = new Object();
    private MultiLayerNetwork loadedNetwork = null;

    @Override
    public MultiLayerNetwork load() {
        if (loadedNetwork == null) {
            synchronized (lock) {
                if (loadedNetwork == null) {
                    loadedNetwork = loadInternal();
                }
            }
        }
        return loadedNetwork;
    }

    private MultiLayerNetwork loadInternal() {
        try {
            return ModelSerializer.restoreMultiLayerNetwork(getClass().getClassLoader().getResourceAsStream("model.zip"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
