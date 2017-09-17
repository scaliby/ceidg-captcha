package net.scaliby.ceidgcaptcha.machinelearning.factory.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.DataSetIteratorFactory;
import net.scaliby.ceidgcaptcha.machinelearning.factory.ImageTransformFactory;
import net.scaliby.ceidgcaptcha.machinelearning.model.NetworkConfigurationResource;
import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.api.split.InputSplit;
import org.datavec.image.recordreader.ImageRecordReader;
import org.datavec.image.transform.ImageTransform;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;

import javax.inject.Inject;
import java.io.IOException;

public class DataSetIteratorFactoryImpl implements DataSetIteratorFactory {

    private final NetworkConfigurationResource networkConfigurationResource;
    private final PathLabelGenerator pathLabelGenerator;
    private final ImageTransformFactory imageTransformFactory;

    @Inject
    public DataSetIteratorFactoryImpl(NetworkConfigurationResource networkConfigurationResource, PathLabelGenerator pathLabelGenerator, ImageTransformFactory imageTransformFactory) {
        this.networkConfigurationResource = networkConfigurationResource;
        this.pathLabelGenerator = pathLabelGenerator;
        this.imageTransformFactory = imageTransformFactory;
    }

    @Override
    public DataSetIterator create(InputSplit inputSplit) {
        try {
            return createInternal(inputSplit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSetIterator createInternal(InputSplit inputSplit) throws IOException {
        ImageTransform imageTransform = imageTransformFactory.create();
        int width = networkConfigurationResource.getScaledWidth();
        int height = networkConfigurationResource.getScaledHeight();
        int channels = networkConfigurationResource.getChannels();
        int batchSize = networkConfigurationResource.getBatchSize();
        int outputs = networkConfigurationResource.getOutputs();
        ImageRecordReader recordReader = new ImageRecordReader(height, width, channels, pathLabelGenerator);
        recordReader.initialize(inputSplit, imageTransform);
        RecordReaderDataSetIterator recordReaderDataSetIterator = new RecordReaderDataSetIterator(recordReader, batchSize, 1, outputs);
        DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
        scaler.fit(recordReaderDataSetIterator);
        recordReaderDataSetIterator.setPreProcessor(scaler);
        return recordReaderDataSetIterator;
    }

}
