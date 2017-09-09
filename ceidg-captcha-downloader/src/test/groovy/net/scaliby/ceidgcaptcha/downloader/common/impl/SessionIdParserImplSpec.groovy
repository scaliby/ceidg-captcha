package net.scaliby.ceidgcaptcha.downloader.common.impl

import net.scaliby.ceidgcaptcha.downloader.common.CookieParser
import org.apache.http.HttpResponse
import org.apache.http.message.BasicHeader
import spock.lang.Specification

class SessionIdParserImplSpec extends Specification {

    def cookieParser = Mock(CookieParser)
    def parser = new SessionIdParserImpl(cookieParser)

    def "parsing session should return empty optional when response headers does not contain session cookie"() {
        given:
        def setCookiesHeaderString = "fooSetCookiesHeaderString"
        def sessionCookiesMap = ["sampleCookie": "sampleValue"]
        def httpResponse = Mock(HttpResponse)
        def httpHeader = new BasicHeader("Set-Cookie", setCookiesHeaderString)

        when:
        def result = parser.parseSessionId(httpResponse)

        then:
        1 * httpResponse.getHeaders("Set-Cookie") >> [httpHeader]
        1 * cookieParser.parseSetCookieStrings({
            it.size() == 1 && it[0] == setCookiesHeaderString
        } as Collection<String>) >> sessionCookiesMap

        and:
        !result.present
    }

    def "parsing session should return optional with value from session cookie"() {
        given:
        def setCookiesHeaderString = "fooSetCookiesHeaderString"
        def sessionCookiesMap = ["ASP.NET_SessionId": "sampleSessionId"]
        def httpResponse = Mock(HttpResponse)
        def httpHeader = new BasicHeader("Set-Cookie", setCookiesHeaderString)

        when:
        def result = parser.parseSessionId(httpResponse)

        then:
        1 * httpResponse.getHeaders("Set-Cookie") >> [httpHeader]
        1 * cookieParser.parseSetCookieStrings({
            it.size() == 1 && it[0] == setCookiesHeaderString
        } as Collection<String>) >> sessionCookiesMap

        and:
        result.present
        result.get() == "sampleSessionId"
    }

}
