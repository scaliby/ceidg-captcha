package net.scaliby.ceidgcaptcha.machinelearning.resource;

import lombok.Data;

import javax.inject.Inject;
import javax.inject.Named;

@Data
public class NetworkConfigurationResource {

    @Inject
    @Named("application.network.outputs")
    private Integer outputs;
    @Inject
    @Named("application.network.epoch")
    private Integer epoch;
    @Inject
    @Named("application.network.batchSize")
    private Integer batchSize;

}
