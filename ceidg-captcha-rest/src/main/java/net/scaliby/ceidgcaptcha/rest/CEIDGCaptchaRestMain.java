package net.scaliby.ceidgcaptcha.rest;

import net.scaliby.ceidgcaptcha.common.CEIDGCaptchaCommonModule;
import net.scaliby.ceidgcaptcha.common.CEIDGCaptchaPropertiesModule;
import net.scaliby.ceidgcaptcha.rest.handler.CEIDGCaptchaPredictionHandler;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;

public class CEIDGCaptchaRestMain {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> {
            server.serverConfig(config -> config
                    .props(CEIDGCaptchaRestMain.class.getClassLoader().getResource("ratpack.properties"))
                    .sysProps()
                    .args(args)
                    .env()
            );
            server.registry(Guice.registry(b -> b
                    .module(CEIDGCaptchaRestModule.class)
                    .module(CEIDGCaptchaPropertiesModule.class)
                    .module(CEIDGCaptchaCommonModule.class)
            ));
            server.handlers(chain -> chain
                    .post("predict-captcha", CEIDGCaptchaPredictionHandler.class)
            );
        });
    }

}
