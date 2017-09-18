package net.scaliby.ceidgcaptcha.machinelearning.resource;

import lombok.Data;

@Data
public class NetworkStatisticsResource {

    private Double accuracy;
    private Double precision;
    private Double recall;
    private Double f1;

}
