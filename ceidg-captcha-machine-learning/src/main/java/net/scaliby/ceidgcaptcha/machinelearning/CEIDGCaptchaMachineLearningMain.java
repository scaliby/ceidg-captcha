package net.scaliby.ceidgcaptcha.machinelearning;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.scaliby.ceidgcaptcha.common.CEIDGCaptchaCommonModule;
import net.scaliby.ceidgcaptcha.common.CEIDGCaptchaPropertiesModule;

public class CEIDGCaptchaMachineLearningMain {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new CEIDGCaptchaMachineLearningModule(),
                new CEIDGCaptchaCommonModule(),
                new CEIDGCaptchaPropertiesModule()
        );
        injector.getInstance(CEIDGCaptchaMachineLearningRunner.class).run();
    }
}
