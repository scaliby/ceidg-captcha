package net.scaliby.ceidgcaptcha.machinelearning.resource;

import lombok.Data;

import javax.inject.Inject;
import javax.inject.Named;

@Data
public class ImageTransformConfigurationResource {

    @Inject
    @Named("application.imageTransform.scaledHeight")
    private Integer scaledHeight;
    @Inject
    @Named("application.imageTransform.scaledWidth")
    private Integer scaledWidth;
    @Inject
    @Named("application.imageTransform.channels")
    private Integer channels;

}
