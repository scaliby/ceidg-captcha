package net.scaliby.ceidgcaptcha.downloader.resource;

import lombok.Data;

import javax.inject.Inject;
import javax.inject.Named;

@Data
public class CEIDGClientEndpointConfigurationResource {

    @Inject
    @Named("application.ceidg.client.endpoint.sessionGenerateUrl")
    private String sessionGenerateUrl;
    @Inject
    @Named("application.ceidg.client.endpoint.imageDownloadUrl")
    private String imageDownloadUrl;

}
