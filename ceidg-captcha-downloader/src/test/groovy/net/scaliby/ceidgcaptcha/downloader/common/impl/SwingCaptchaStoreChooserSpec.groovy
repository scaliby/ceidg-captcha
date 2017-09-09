package net.scaliby.ceidgcaptcha.downloader.common.impl

import net.scaliby.ceidgcaptcha.downloader.factory.JFileChooserFactory
import spock.lang.Specification

import javax.swing.*

class SwingCaptchaStoreChooserSpec extends Specification {

    def jFileChooserFactory = Mock(JFileChooserFactory)
    def chooser = new SwingCaptchaStoreChooser(jFileChooserFactory)

    def "opening dialog with APPROVE_OPTION result should return optional with selected file"() {
        given:
        def fileChooser = Mock(JFileChooser)
        def file = new File("/foo")
        def openDialogResult = JFileChooser.APPROVE_OPTION

        when:
        def result = chooser.getDirectory()

        then:
        jFileChooserFactory.create() >> fileChooser
        fileChooser.showOpenDialog(_) >> openDialogResult
        fileChooser.getSelectedFile() >> file

        and:
        result.present
        result.get() == file
    }

    def "opening dialog with not APPROVE_OPTION result should return empty optional"() {
        given:
        def fileChooser = Mock(JFileChooser)
        def openDialogResult = JFileChooser.CANCEL_OPTION

        when:
        def result = chooser.getDirectory()

        then:
        jFileChooserFactory.create() >> fileChooser
        fileChooser.showOpenDialog(_) >> openDialogResult

        and:
        !result.present
    }

}
