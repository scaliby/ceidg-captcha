package net.scaliby.ceidgcaptcha.machinelearning.service.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.DataSetIteratorFactory;
import net.scaliby.ceidgcaptcha.machinelearning.model.InputSplitResource;
import net.scaliby.ceidgcaptcha.machinelearning.model.NetworkConfigurationResource;
import net.scaliby.ceidgcaptcha.machinelearning.model.NetworkStatisticsResource;
import net.scaliby.ceidgcaptcha.machinelearning.service.InputSplitService;
import net.scaliby.ceidgcaptcha.machinelearning.service.MachineLearningService;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import javax.inject.Inject;

public class MachineLearningServiceImpl implements MachineLearningService {

    private final DataSetIteratorFactory dataSetIteratorFactory;
    private final InputSplitService inputSplitService;
    private final NetworkConfigurationResource networkConfigurationResource;

    @Inject
    public MachineLearningServiceImpl(DataSetIteratorFactory dataSetIteratorFactory, InputSplitService inputSplitService, NetworkConfigurationResource networkConfigurationResource) {
        this.dataSetIteratorFactory = dataSetIteratorFactory;
        this.inputSplitService = inputSplitService;
        this.networkConfigurationResource = networkConfigurationResource;
    }

    @Override
    public void train(MultiLayerNetwork multiLayerNetwork) {
        InputSplitResource inputSplitResource = inputSplitService.getInputSplit();
        DataSetIterator dataSetIterator = dataSetIteratorFactory.create(inputSplitResource.getTrain());
        for (int i = 0; i < networkConfigurationResource.getEpoch(); i++) {
            multiLayerNetwork.fit(dataSetIterator);
        }
    }

    public NetworkStatisticsResource test(MultiLayerNetwork multiLayerNetwork) {
        InputSplitResource inputSplitResource = inputSplitService.getInputSplit();
        DataSetIterator dataSetIterator = dataSetIteratorFactory.create(inputSplitResource.getTest());
        Evaluation evaluation = multiLayerNetwork.evaluate(dataSetIterator);
        return toNetworkStatisticsResource(evaluation);
    }

    private NetworkStatisticsResource toNetworkStatisticsResource(Evaluation evaluation) {
        NetworkStatisticsResource resource = new NetworkStatisticsResource();
        resource.setAccuracy(evaluation.accuracy());
        resource.setF1(evaluation.f1());
        resource.setPrecision(evaluation.precision());
        resource.setRecall(evaluation.recall());
        return resource;
    }

}
