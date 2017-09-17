package net.scaliby.ceidgcaptcha.machinelearning

import net.scaliby.ceidgcaptcha.machinelearning.common.MultiLayerNetworkSaver
import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerConfigurationFactory
import net.scaliby.ceidgcaptcha.machinelearning.factory.MultiLayerNetworkFactory
import net.scaliby.ceidgcaptcha.machinelearning.service.MachineLearningService
import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import spock.lang.Specification

class CEIDGCaptchaMachineLearningRunnerSpec extends Specification {

    def multiLayerConfigurationFactory = Mock(MultiLayerConfigurationFactory)
    def machineLearningService = Mock(MachineLearningService)
    def multiLayerNetworkFactory = Mock(MultiLayerNetworkFactory)
    def multiLayerNetworkSaver = Mock(MultiLayerNetworkSaver)

    def runner = new CEIDGCaptchaMachineLearningRunner(multiLayerConfigurationFactory, machineLearningService, multiLayerNetworkFactory, multiLayerNetworkSaver)

    def "running should train created network"() {
        given:
        def multiLayerConfiguration = Mock(MultiLayerConfiguration)
        def multiLayerNetwork = Mock(MultiLayerNetwork)

        when:
        runner.run()

        then:
        multiLayerConfigurationFactory.create() >> multiLayerConfiguration
        multiLayerNetworkFactory.create(multiLayerConfiguration) >> multiLayerNetwork

        and:
        1 * machineLearningService.train(multiLayerNetwork)
    }

    def "running should save created network"() {
        given:
        def multiLayerConfiguration = Mock(MultiLayerConfiguration)
        def multiLayerNetwork = Mock(MultiLayerNetwork)

        when:
        runner.run()

        then:
        multiLayerConfigurationFactory.create() >> multiLayerConfiguration
        multiLayerNetworkFactory.create(multiLayerConfiguration) >> multiLayerNetwork

        and:
        1 * multiLayerNetworkSaver.save(multiLayerNetwork)
    }

    def "running should test created network"() {
        given:
        def multiLayerConfiguration = Mock(MultiLayerConfiguration)
        def multiLayerNetwork = Mock(MultiLayerNetwork)

        when:
        runner.run()

        then:
        multiLayerConfigurationFactory.create() >> multiLayerConfiguration
        multiLayerNetworkFactory.create(multiLayerConfiguration) >> multiLayerNetwork

        and:
        1 * machineLearningService.test(multiLayerNetwork)
    }

}
