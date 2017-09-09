package net.scaliby.ceidgcaptcha.downloader.common.impl

import spock.lang.Specification

class CookieParserImplSpec extends Specification {

    def parser = new CookieParserImpl()

    def "parsing collection of Set-Cookie headers should extract cookies to map"() {
        given:
        def headers = [
                "fooName=fooValue; Domain=foo.com; Secure; HttpOnly",
                "otherName=otherValue; Secure"
        ]

        when:
        def result = parser.parseSetCookieStrings(headers)

        then:
        result.size() == 2
        result == [
                "fooName"  : "fooValue",
                "otherName": "otherValue"
        ]
    }

    def "formatting map of cookies should return Cookie header string"() {
        given:
        def cookies = [
                "fooName"  : "fooValue",
                "otherName": "otherValue"
        ]
        def expectedCookieString = "fooName=fooValue; otherName=otherValue"

        when:
        def result = parser.formatCookieString(cookies)

        then:
        result == expectedCookieString
    }

}
