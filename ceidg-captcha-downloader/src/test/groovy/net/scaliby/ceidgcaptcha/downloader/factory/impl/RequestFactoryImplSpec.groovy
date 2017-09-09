package net.scaliby.ceidgcaptcha.downloader.factory.impl

import net.scaliby.ceidgcaptcha.downloader.common.CookieParser
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGClientEndpointConfigurationResource
import spock.lang.Specification

class RequestFactoryImplSpec extends Specification {

    def ceidgClientEndpointConfigurationResource = new CEIDGClientEndpointConfigurationResource(
            sessionGenerateUrl: "fooSessionGenerateUrl",
            imageDownloadUrl: "fooImageDownloadUrl"
    )
    def cookieParser = Mock(CookieParser)
    def factory = new RequestFactoryImpl(ceidgClientEndpointConfigurationResource, cookieParser)

    def "creating session generate request should return request with valid uri"() {
        when:
        def result = factory.createGenerateSessionRequest()

        then:
        result.getMethod() == "GET"
        result.getURI() == new URI(ceidgClientEndpointConfigurationResource.sessionGenerateUrl)
    }

    def "create get captcha image request should return request with valid cookies header"() {
        given:
        def sessionResource = new CEIDGCaptchaSessionResource(sessionId: "fooSessionId", captchaId: "fooCaptchaId")
        def cookieHeader = "fooCookieHeader"
        def expectedCookieMap = ["ASP.NET_SessionId": sessionResource.sessionId]

        when:
        def result = factory.createGetCaptchaImageRequest(sessionResource)

        then:
        cookieParser.formatCookieString(expectedCookieMap) >> cookieHeader

        and:
        result.getMethod() == "GET"
        result.getURI() == new URI("/fooImageDownloadUrl?part=0&id=fooCaptchaId")
        result.getFirstHeader("Cookie").getValue() == cookieHeader
    }

}
