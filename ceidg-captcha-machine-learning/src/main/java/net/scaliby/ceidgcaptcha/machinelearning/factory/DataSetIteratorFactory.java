package net.scaliby.ceidgcaptcha.machinelearning.factory;

import org.datavec.api.split.InputSplit;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface DataSetIteratorFactory {

    DataSetIterator create(InputSplit inputSplit);

}
