package net.scaliby.ceidgcaptcha.rest.service.impl

import net.scaliby.ceidgcaptcha.rest.common.MultiLayerNetworkLoader
import net.scaliby.ceidgcaptcha.rest.common.OutputLabeler
import net.scaliby.ceidgcaptcha.rest.converter.BufferedImageToINDArrayConverter
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.api.ndarray.INDArray
import spock.lang.Specification

import java.awt.image.BufferedImage

class CEIDGCaptchaPredictionServiceImplSpec extends Specification {

    def multiLayerNetworkLoader = Mock(MultiLayerNetworkLoader)
    def bufferedImageToINDArrayConverter = Mock(BufferedImageToINDArrayConverter)
    def outputLabeler = Mock(OutputLabeler)

    def service = new CEIDGCaptchaPredictionServiceImpl(multiLayerNetworkLoader, bufferedImageToINDArrayConverter, outputLabeler)

    def "predicting buffered image should return predicted label"() {
        given:
        def bufferedImage = Mock(BufferedImage)
        def input = Mock(INDArray)
        def network = Mock(MultiLayerNetwork)
        def output = Mock(INDArray)
        def label = "fooLabel"

        when:
        def result = service.predict(bufferedImage)

        then:
        bufferedImageToINDArrayConverter.convert(bufferedImage) >> input
        multiLayerNetworkLoader.load() >> network
        network.output(input) >> output
        outputLabeler.getLabel(output) >> label

        and:
        result == label
    }

}
