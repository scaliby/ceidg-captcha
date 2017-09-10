package net.scaliby.ceidgcaptcha.machinelearning.transform;

import org.bytedeco.javacv.OpenCVFrameConverter;
import org.datavec.image.data.ImageWritable;
import org.datavec.image.transform.BaseImageTransform;

import java.util.Random;

import static org.bytedeco.javacpp.opencv_core.*;

public class NotImageTransform extends BaseImageTransform {
    private static final OpenCVFrameConverter.ToIplImage OPEN_CV_FRAME_CONVERTER = new OpenCVFrameConverter.ToIplImage();

    public NotImageTransform(Random random) {
        super(random);
    }

    public ImageWritable transform(ImageWritable image, Random random) {
        if (image == null) {
            return null;
        }
        IplImage sourceImage = OPEN_CV_FRAME_CONVERTER.convert(image.getFrame());
        IplImage targetImage = cvCreateImage(cvGetSize(sourceImage), sourceImage.depth(), sourceImage.nChannels());
        cvNot(sourceImage, targetImage);
        return new ImageWritable(OPEN_CV_FRAME_CONVERTER.convert(targetImage));

    }
}
