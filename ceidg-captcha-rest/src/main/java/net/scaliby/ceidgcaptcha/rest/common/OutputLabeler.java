package net.scaliby.ceidgcaptcha.rest.common;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface OutputLabeler {

    String getLabel(INDArray output);

}
