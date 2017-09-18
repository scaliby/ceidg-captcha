package net.scaliby.ceidgcaptcha.rest.common.impl

import net.scaliby.ceidgcaptcha.rest.factory.LabelsFactory
import org.nd4j.linalg.factory.Nd4j
import spock.lang.Specification

class OutputLabelerImplSpec extends Specification {

    def labelsFactory = Mock(LabelsFactory)

    def labeler = new OutputLabelerImpl(labelsFactory)

    def "should return label associated to max value from array"() {
        given:
        def array = Nd4j.create([1.9f, 2f, 1.2f] as float[])
        def labels = ["A", "B", "C"]

        when:
        def result = labeler.getLabel(array)

        then:
        labelsFactory.create() >> labels

        and:
        result == "B"
    }

}
