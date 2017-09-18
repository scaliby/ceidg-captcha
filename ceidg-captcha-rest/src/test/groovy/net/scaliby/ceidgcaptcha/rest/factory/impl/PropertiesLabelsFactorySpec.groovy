package net.scaliby.ceidgcaptcha.rest.factory.impl

import spock.lang.Specification

class PropertiesLabelsFactorySpec extends Specification {

    def factory = new PropertiesLabelsFactory()

    def "creating should return splitted labels"() {
        given:
        def separator = "fooSeparator";
        def labels = "afooSeparatorbfooSeparatorc"

        factory.labels = labels
        factory.labelsSeparator = separator

        when:
        def result = factory.create()

        then:
        result == ["a", "b", "c"]
    }

}
