package net.scaliby.ceidgcaptcha.rest.common.impl;

import net.scaliby.ceidgcaptcha.rest.common.OutputLabeler;
import net.scaliby.ceidgcaptcha.rest.factory.LabelsFactory;
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.inject.Inject;

public class OutputLabelerImpl implements OutputLabeler {

    private final LabelsFactory labelsFactory;

    @Inject
    public OutputLabelerImpl(LabelsFactory labelsFactory) {
        this.labelsFactory = labelsFactory;
    }

    @Override
    public String getLabel(INDArray output) {
        int maxValueIndex = getMaxValueIndex(output);
        return labelsFactory.create().get(maxValueIndex);
    }

    private static int getMaxValueIndex(INDArray indArray) {
        int maxIndex = 0;
        float maxValue = Float.MIN_VALUE;
        for (int i = 0; i < indArray.columns(); i++) {
            float value = indArray.getFloat(i);
            if (maxValue <= value) {
                maxValue = value;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
