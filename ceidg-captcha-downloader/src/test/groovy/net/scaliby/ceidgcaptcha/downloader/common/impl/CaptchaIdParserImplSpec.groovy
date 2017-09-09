package net.scaliby.ceidgcaptcha.downloader.common.impl

import net.scaliby.ceidgcaptcha.downloader.factory.JsoupDocumentFactory
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import spock.lang.Specification

class CaptchaIdParserImplSpec extends Specification {

    def jsoupDocumentFactory = Mock(JsoupDocumentFactory)
    def parser = new CaptchaIdParserImpl(jsoupDocumentFactory)

    def "parsing document with captcha element with new line characters and id parameter should return optional with value of this parameter"() {
        given:
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        def inputStream = Mock(InputStream)
        def document = Mock(Document)
        def element = Mock(Element)
        def captchaUrl = "foo\r\n?id=\r\nfooId"

        when:
        def result = parser.parseCaptchaId(httpResponse)

        then:
        httpResponse.getEntity() >> httpEntity
        httpEntity.getContent() >> inputStream
        jsoupDocumentFactory.create(inputStream) >> document
        document.getElementById("MainContent_ctrlCaptcha2") >> element
        element.attr("src") >> captchaUrl

        and:
        result.present
        result.get() == "fooId"
    }

    def "parsing document with captcha element without id url param should return empty optional"() {
        given:
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        def inputStream = Mock(InputStream)
        def document = Mock(Document)
        def element = Mock(Element)
        def captchaUrl = "foo?isd=fooId"

        when:
        def result = parser.parseCaptchaId(httpResponse)

        then:
        httpResponse.getEntity() >> httpEntity
        httpEntity.getContent() >> inputStream
        jsoupDocumentFactory.create(inputStream) >> document
        document.getElementById("MainContent_ctrlCaptcha2") >> element
        element.attr("src") >> captchaUrl

        and:
        !result.present
    }

    def "parsing document with exception from jsoup document factory should return empty optional"() {
        given:
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        def inputStream = Mock(InputStream)

        when:
        def result = parser.parseCaptchaId(httpResponse)

        then:
        httpResponse.getEntity() >> httpEntity
        httpEntity.getContent() >> inputStream
        jsoupDocumentFactory.create(inputStream) >> { throw new IOException("foo") }

        and:
        !result.present
    }

}
