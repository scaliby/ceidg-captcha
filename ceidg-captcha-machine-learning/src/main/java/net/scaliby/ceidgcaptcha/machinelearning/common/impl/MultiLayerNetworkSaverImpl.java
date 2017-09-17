package net.scaliby.ceidgcaptcha.machinelearning.common.impl;

import lombok.extern.log4j.Log4j;
import net.scaliby.ceidgcaptcha.common.common.StoreChooser;
import net.scaliby.ceidgcaptcha.common.exception.ImageStoreException;
import net.scaliby.ceidgcaptcha.machinelearning.common.MultiLayerNetworkSaver;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

@Log4j
public class MultiLayerNetworkSaverImpl implements MultiLayerNetworkSaver {

    private final StoreChooser storeChooser;

    @Inject
    public MultiLayerNetworkSaverImpl(StoreChooser storeChooser) {
        this.storeChooser = storeChooser;
    }

    @Override
    public void save(MultiLayerNetwork network) {
        log.info("Select where you wanna save network");
        storeChooser.getDirectory().ifPresent(store -> {
            try {
                ModelSerializer.writeModel(network, new File(store + File.separator + "network.zip"), false);
            } catch (IOException e) {
                throw new ImageStoreException(e);
            }
        });
    }
}
