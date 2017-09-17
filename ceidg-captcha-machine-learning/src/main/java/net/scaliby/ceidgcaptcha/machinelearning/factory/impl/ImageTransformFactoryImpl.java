package net.scaliby.ceidgcaptcha.machinelearning.factory.impl;

import net.scaliby.ceidgcaptcha.machinelearning.factory.ImageTransformFactory;
import net.scaliby.ceidgcaptcha.machinelearning.model.NetworkConfigurationResource;
import net.scaliby.ceidgcaptcha.machinelearning.transform.CropImageTransform;
import net.scaliby.ceidgcaptcha.machinelearning.transform.NotImageTransform;
import net.scaliby.ceidgcaptcha.machinelearning.transform.RangeImageTransform;
import org.datavec.image.transform.ImageTransform;
import org.datavec.image.transform.MultiImageTransform;
import org.datavec.image.transform.ResizeImageTransform;

import javax.inject.Inject;
import java.util.Random;

public class ImageTransformFactoryImpl implements ImageTransformFactory {

    private final NetworkConfigurationResource networkConfigurationResource;
    private final Random random;

    @Inject
    public ImageTransformFactoryImpl(NetworkConfigurationResource networkConfigurationResource, Random random) {
        this.networkConfigurationResource = networkConfigurationResource;
        this.random = random;
    }

    @Override
    public ImageTransform create() {
        int width = networkConfigurationResource.getScaledWidth();
        int height = networkConfigurationResource.getScaledHeight();
        return new MultiImageTransform(
                new RangeImageTransform(random),
                new NotImageTransform(random),
                new CropImageTransform(random),
                new ResizeImageTransform(random, width, height)
        );
    }
}
