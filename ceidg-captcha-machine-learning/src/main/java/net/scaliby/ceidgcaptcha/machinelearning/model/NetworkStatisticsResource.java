package net.scaliby.ceidgcaptcha.machinelearning.model;

import lombok.Data;

@Data
public class NetworkStatisticsResource {

    private Double accuracy;
    private Double precision;
    private Double recall;
    private Double f1;

}
