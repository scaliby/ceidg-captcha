package net.scaliby.ceidgcaptcha.downloader.factory;

import net.scaliby.ceidgcaptcha.downloader.resource.CEIDGCaptchaSessionResource;
import org.apache.http.client.methods.HttpUriRequest;

public interface RequestFactory {

    HttpUriRequest createGenerateSessionRequest();

    HttpUriRequest createGetCaptchaImageRequest(CEIDGCaptchaSessionResource ceidgCaptchaSessionResource);

}
