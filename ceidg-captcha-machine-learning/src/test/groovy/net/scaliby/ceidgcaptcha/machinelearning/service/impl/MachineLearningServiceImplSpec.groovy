package net.scaliby.ceidgcaptcha.machinelearning.service.impl

import net.scaliby.ceidgcaptcha.machinelearning.factory.DataSetIteratorFactory
import net.scaliby.ceidgcaptcha.machinelearning.resource.InputSplitResource
import net.scaliby.ceidgcaptcha.machinelearning.resource.NetworkConfigurationResource
import net.scaliby.ceidgcaptcha.machinelearning.resource.NetworkStatisticsResource
import net.scaliby.ceidgcaptcha.machinelearning.service.InputSplitService
import org.datavec.api.split.InputSplit
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import spock.lang.Specification

class MachineLearningServiceImplSpec extends Specification {

    def dataSetIteratorFactory = Mock(DataSetIteratorFactory)
    def inputSplitService = Mock(InputSplitService)
    def networkConfigurationResource = new NetworkConfigurationResource()
    def service = new MachineLearningServiceImpl(dataSetIteratorFactory, inputSplitService, networkConfigurationResource)

    def "training network should fit multi layer network given times"() {
        given:
        def multiLayerNetwork = Mock(MultiLayerNetwork)
        def inputSplitResource = new InputSplitResource(train: Mock(InputSplit))
        def dataSetIterator = Mock(DataSetIterator)
        def iterations = 3

        networkConfigurationResource.epoch = iterations

        when:
        service.train(multiLayerNetwork)

        then:
        inputSplitService.getInputSplit() >> inputSplitResource
        dataSetIteratorFactory.create(inputSplitResource.train) >> dataSetIterator

        and:
        iterations * multiLayerNetwork.fit(dataSetIterator)
    }

    def "testing network should return statistics resource filled with data from network"() {
        given:
        def multiLayerNetwork = Mock(MultiLayerNetwork)
        def inputSplitResource = new InputSplitResource(test: Mock(InputSplit))
        def dataSetIterator = Mock(DataSetIterator)
        def evaluation = Mock(Evaluation)
        def expectedResult = new NetworkStatisticsResource(accuracy: 123, precision: 312, recall: 1233, f1: 1337)

        when:
        def result = service.test(multiLayerNetwork)

        then:
        inputSplitService.getInputSplit() >> inputSplitResource
        dataSetIteratorFactory.create(inputSplitResource.test) >> dataSetIterator
        evaluation.precision() >> expectedResult.precision
        evaluation.recall() >> expectedResult.recall
        evaluation.accuracy() >> expectedResult.accuracy
        evaluation.f1() >> expectedResult.f1
        1 * multiLayerNetwork.evaluate(dataSetIterator) >> evaluation

        and:
        result == expectedResult
    }

}
