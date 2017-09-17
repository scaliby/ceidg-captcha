package net.scaliby.ceidgcaptcha.downloader

import net.scaliby.ceidgcaptcha.downloader.service.CaptchaDownloaderService
import spock.lang.Specification

class CEIDGCaptchaDownloaderRunnerSpec extends Specification {

    def captchaDownloaderService = Mock(CaptchaDownloaderService)

    def runner = new CEIDGCaptchaDownloaderRunner(captchaDownloaderService)

    void setup() {
    }

    def "running should execute service method with given attribtues"() {
        given:
        def imagesFormat = "fooImagesFormat"
        def imagesCount = 1337

        runner.imagesCount = imagesCount
        runner.imagesFormat = imagesFormat

        when:
        runner.run()

        then:
        1 * captchaDownloaderService.downloadCaptchaImages(imagesCount, imagesFormat)
    }
}
