package net.scaliby.ceidgcaptcha.machinelearning.transform;

import lombok.Setter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.datavec.image.data.ImageWritable;
import org.datavec.image.transform.BaseImageTransform;

import java.util.Random;

import static org.bytedeco.javacpp.opencv_core.*;

public class RangeImageTransform extends BaseImageTransform {
    private static final OpenCVFrameConverter.ToIplImage OPEN_CV_FRAME_CONVERTER = new OpenCVFrameConverter.ToIplImage();
    private static final int RANGE_LOWER = 180;
    private static final int RANGE_UPPER = 255;

    @Setter
    private int rangeLower = RANGE_LOWER;
    @Setter
    private int rangeUpper = RANGE_UPPER;

    public RangeImageTransform(Random random) {
        super(random);
    }

    public ImageWritable transform(ImageWritable image, Random random) {
        if (image == null) {
            return null;
        }

        IplImage sourceImage = OPEN_CV_FRAME_CONVERTER.convert(image.getFrame());
        CvScalar lower = cvScalar(rangeLower, rangeLower, rangeLower, 0.0D);
        CvScalar upper = cvScalar(rangeUpper, rangeUpper, rangeUpper, 0.0D);
        IplImage transformedImage = cvCreateImage(cvGetSize(sourceImage), 8, 1);
        cvInRangeS(sourceImage, lower, upper, transformedImage);
        return new ImageWritable(OPEN_CV_FRAME_CONVERTER.convert(transformedImage));
    }
}
