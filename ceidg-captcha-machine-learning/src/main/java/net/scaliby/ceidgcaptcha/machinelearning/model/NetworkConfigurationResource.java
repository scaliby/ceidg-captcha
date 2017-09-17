package net.scaliby.ceidgcaptcha.machinelearning.model;

import lombok.Data;

import javax.inject.Inject;
import javax.inject.Named;

@Data
public class NetworkConfigurationResource {

    @Inject
    @Named("application.network.scaledHeight")
    private Integer scaledHeight;
    @Inject
    @Named("application.network.scaledWidth")
    private Integer scaledWidth;
    @Inject
    @Named("application.network.outputs")
    private Integer outputs;
    @Inject
    @Named("application.network.epoch")
    private Integer epoch;
    @Inject
    @Named("application.network.channels")
    private Integer channels;
    @Inject
    @Named("application.network.seed")
    private Integer seed;
    @Inject
    @Named("application.network.batchSize")
    private Integer batchSize;

}
