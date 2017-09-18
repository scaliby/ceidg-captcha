package net.scaliby.ceidgcaptcha.rest.handler;

import net.scaliby.ceidgcaptcha.rest.converter.ByteArrayToBufferedImageConverter;
import net.scaliby.ceidgcaptcha.rest.resource.CEIDGCaptchaResponseResource;
import net.scaliby.ceidgcaptcha.rest.service.CEIDGCaptchaPredictionService;
import ratpack.form.Form;
import ratpack.form.UploadedFile;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

import static ratpack.jackson.Jackson.json;

public class CEIDGCaptchaPredictionHandler implements Handler {

    private final CEIDGCaptchaPredictionService ceidgCaptchaPredictionService;
    private final ByteArrayToBufferedImageConverter byteArrayToBufferedImageConverter;

    @Inject
    public CEIDGCaptchaPredictionHandler(CEIDGCaptchaPredictionService ceidgCaptchaPredictionService, ByteArrayToBufferedImageConverter byteArrayToBufferedImageConverter) {
        this.ceidgCaptchaPredictionService = ceidgCaptchaPredictionService;
        this.byteArrayToBufferedImageConverter = byteArrayToBufferedImageConverter;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.parse(Form.class).then(form -> {
            UploadedFile image = form.file("image");
            BufferedImage bufferedImage = byteArrayToBufferedImageConverter.convert(image.getBytes());
            String prediction = ceidgCaptchaPredictionService.predict(bufferedImage);

            CEIDGCaptchaResponseResource ceidgCaptchaResponseResource = new CEIDGCaptchaResponseResource();
            ceidgCaptchaResponseResource.setPrediction(prediction);
            ctx.render(json(ceidgCaptchaResponseResource));
        });
    }

}
