package net.scaliby.ceidgcaptcha.downloader.service.impl

import net.scaliby.ceidgcaptcha.downloader.common.CEIDGClient
import net.scaliby.ceidgcaptcha.downloader.common.CaptchaLabeler
import net.scaliby.ceidgcaptcha.common.common.StoreChooser
import net.scaliby.ceidgcaptcha.downloader.common.ImageWriter
import net.scaliby.ceidgcaptcha.downloader.exception.CaptchaLabelingException
import net.scaliby.ceidgcaptcha.common.exception.ImageStoreException
import net.scaliby.ceidgcaptcha.downloader.factory.ImageStoreFactory
import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource
import spock.lang.Specification

import java.awt.image.BufferedImage

class CaptchaDownloaderServiceImplTest extends Specification {

    def captchaLabeler = Mock(CaptchaLabeler)
    def captchaStoreChooser = Mock(StoreChooser)
    def ceidgClient = Mock(CEIDGClient)
    def imageStoreFactory = Mock(ImageStoreFactory)
    def imageWriter = Mock(ImageWriter)
    def service = new CaptchaDownloaderServiceImpl(captchaLabeler, captchaStoreChooser, ceidgClient, imageStoreFactory, imageWriter)

    def "downloading captcha images should throw exception when image store is not selected"() {
        given:
        def count = 1
        def format = "png"
        def session = new CEIDGCaptchaSessionResource(captchaId: "fooCaptchaId", sessionId: "fooSessionId")

        when:
        service.downloadCaptchaImages(count, format)

        then:
        1 * ceidgClient.generateSession() >> session
        captchaStoreChooser.getDirectory() >> Optional.empty()

        and:
        thrown(ImageStoreException)
    }

    def "downloading captcha images should throw exception when image label is not provided"() {
        given:
        def count = 1
        def format = "png"
        def session = new CEIDGCaptchaSessionResource(captchaId: "fooCaptchaId", sessionId: "fooSessionId")
        def storeBasePath = new File("/")
        def imageUsedToLabel = Mock(BufferedImage)

        when:
        service.downloadCaptchaImages(count, format)

        then:
        1 * ceidgClient.generateSession() >> session
        captchaStoreChooser.getDirectory() >> Optional.of(storeBasePath)
        1 * ceidgClient.getCaptchaImage(session) >> imageUsedToLabel
        captchaLabeler.getLabel(imageUsedToLabel) >> Optional.empty()

        and:
        thrown(CaptchaLabelingException)
    }

    def "downloading captcha images should save images using image writer"() {
        given:
        def count = 2
        def format = "png"
        def session = new CEIDGCaptchaSessionResource(captchaId: "fooCaptchaId", sessionId: "fooSessionId")
        def storeBasePath = new File("/")
        def imageUsedToLabel = Mock(BufferedImage)
        def firstDownloadedImage = Mock(BufferedImage)
        def secondDownloadedImage = Mock(BufferedImage)
        def label = "fooLabel"
        def labelledStore = new File("/foo")

        when:
        service.downloadCaptchaImages(count, format)

        then:
        1 * ceidgClient.generateSession() >> session
        captchaStoreChooser.getDirectory() >> Optional.of(storeBasePath)
        3 * ceidgClient.getCaptchaImage(session) >> imageUsedToLabel >> firstDownloadedImage >> secondDownloadedImage
        captchaLabeler.getLabel(imageUsedToLabel) >> Optional.of(label)
        imageStoreFactory.createImageStore(storeBasePath, label) >> labelledStore

        and:
        1 * imageWriter.write(firstDownloadedImage, format, new File(labelledStore, "0.png"))
        1 * imageWriter.write(secondDownloadedImage, format, new File(labelledStore, "1.png"))
    }

}
