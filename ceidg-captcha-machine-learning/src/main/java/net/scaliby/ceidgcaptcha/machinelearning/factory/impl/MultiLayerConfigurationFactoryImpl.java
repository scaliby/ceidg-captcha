package net.scaliby.ceidgcaptcha.machinelearning.factory.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerConfigurationFactory;
import net.scaliby.ceidgcaptcha.machinelearning.model.NetworkConfigurationResource;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import javax.inject.Inject;

public class MultiLayerConfigurationFactoryImpl implements MultiLayerConfigurationFactory {

    private final NetworkConfigurationResource networkConfigurationResource;

    @Inject
    public MultiLayerConfigurationFactoryImpl(NetworkConfigurationResource networkConfigurationResource) {
        this.networkConfigurationResource = networkConfigurationResource;
    }

    @Override
    public MultiLayerConfiguration create() {
        int width = networkConfigurationResource.getScaledWidth();
        int height = networkConfigurationResource.getScaledHeight();
        int channels = networkConfigurationResource.getChannels();
        int outputs = networkConfigurationResource.getOutputs();
        return new NeuralNetConfiguration.Builder()
                .seed(networkConfigurationResource.getSeed())
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1)
                .learningRate(0.0001)
                .activation(Activation.RELU)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .regularization(true).l2(1e-3)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(width * height * channels)
                        .nOut(1200)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(1200)
                        .nOut(600)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(600)
                        .activation(Activation.SOFTMAX)
                        .nOut(outputs)
                        .build())
                .pretrain(false).backprop(true)
                .setInputType(InputType.convolutional(height, width, channels))
                .build();
    }
}
