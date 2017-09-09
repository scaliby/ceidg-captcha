package net.scaliby.ceidgcaptcha.downloader.common.impl

import net.scaliby.ceidgcaptcha.downloader.common.CaptchaIdParser
import net.scaliby.ceidgcaptcha.downloader.common.ImageParser
import net.scaliby.ceidgcaptcha.downloader.common.SessionIdParser
import net.scaliby.ceidgcaptcha.downloader.factory.RequestFactory
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.impl.client.CloseableHttpClient
import spock.lang.Specification

import java.awt.image.BufferedImage

class CEIDGClientImplSpec extends Specification {

    def closeableHttpClient = Mock(CloseableHttpClient)
    def captchaIdParser = Mock(CaptchaIdParser)
    def sessionIdParser = Mock(SessionIdParser)
    def requestFactory = Mock(RequestFactory)
    def imageParser = Mock(ImageParser)
    def client = new CEIDGClientImpl(closeableHttpClient, captchaIdParser, sessionIdParser, imageParser, requestFactory)

    def "generating session should return resource with captcha id when parsing return non empty optional"() {
        given:
        def httpRequest = Mock(HttpUriRequest)
        def httpResponse = Mock(CloseableHttpResponse)
        def captchaId = "fooCaptchaId"

        when:
        def result = client.generateSession()

        then:
        requestFactory.createGenerateSessionRequest() >> httpRequest
        1 * closeableHttpClient.execute(httpRequest) >> httpResponse
        captchaIdParser.parseCaptchaId(httpResponse) >> Optional.of(captchaId)
        sessionIdParser.parseSessionId(httpResponse) >> Optional.empty()
        1 * httpResponse.close()

        and:
        result.captchaId == captchaId
        result.sessionId == null
    }

    def "generating session should return resource with session id when parsing return non empty optional"() {
        given:
        def httpRequest = Mock(HttpUriRequest)
        def httpResponse = Mock(CloseableHttpResponse)
        def sessionId = "fooSessionId"

        when:
        def result = client.generateSession()

        then:
        requestFactory.createGenerateSessionRequest() >> httpRequest
        1 * closeableHttpClient.execute(httpRequest) >> httpResponse
        captchaIdParser.parseCaptchaId(httpResponse) >> Optional.empty()
        sessionIdParser.parseSessionId(httpResponse) >> Optional.of(sessionId)
        1 * httpResponse.close()

        and:
        result.sessionId == sessionId
        result.captchaId == null
    }

    def "generating session should return resource with both session id and captcha id when parsing return non empty optional"() {
        given:
        def httpRequest = Mock(HttpUriRequest)
        def httpResponse = Mock(CloseableHttpResponse)
        def sessionId = "fooSessionId"
        def captchaId = "fooCaptchaId"

        when:
        def result = client.generateSession()

        then:
        requestFactory.createGenerateSessionRequest() >> httpRequest
        1 * closeableHttpClient.execute(httpRequest) >> httpResponse
        captchaIdParser.parseCaptchaId(httpResponse) >> Optional.of(captchaId)
        sessionIdParser.parseSessionId(httpResponse) >> Optional.of(sessionId)
        1 * httpResponse.close()

        and:
        result.sessionId == sessionId
        result.captchaId == captchaId
    }

    def "getting captcha image should return parsed response by image parser"() {
        given:
        def httpRequest = Mock(HttpUriRequest)
        def httpResponse = Mock(CloseableHttpResponse)
        def ceidgCaptchaSessionResource = new CEIDGCaptchaSessionResource(sessionId: "fooSessionId", captchaId: "fooCaptchaId")
        def image = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB)

        when:
        def result = client.getCaptchaImage(ceidgCaptchaSessionResource)

        then:
        requestFactory.createGetCaptchaImageRequest(ceidgCaptchaSessionResource) >> httpRequest
        1 * closeableHttpClient.execute(httpRequest) >> httpResponse
        imageParser.parseImage(httpResponse) >> image

        and:
        result == image
    }

}
