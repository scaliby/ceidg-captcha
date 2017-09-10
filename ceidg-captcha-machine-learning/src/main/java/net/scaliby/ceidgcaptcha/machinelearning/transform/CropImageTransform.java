package net.scaliby.ceidgcaptcha.machinelearning.transform;

import lombok.Setter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.datavec.image.data.ImageWritable;
import org.datavec.image.transform.BaseImageTransform;

import java.util.*;
import java.util.stream.Collectors;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.boundingRect;
import static org.bytedeco.javacpp.opencv_imgproc.findContours;

public class CropImageTransform extends BaseImageTransform {
    private static final OpenCVFrameConverter.ToMat OPEN_CV_FRAME_CONVERTER = new OpenCVFrameConverter.ToMat();
    private static final int MAX_RECT_DISTANCE = 5;
    @Setter
    private int maxRectDistance = MAX_RECT_DISTANCE;

    public CropImageTransform(Random random) {
        super(random);
    }

    public ImageWritable transform(ImageWritable image, Random random) {
        if (image == null) {
            return null;
        }
        Mat sourceImage = OPEN_CV_FRAME_CONVERTER.convert(image.getFrame());
        List<Rect> countoursRects = findCountoursRects(sourceImage);
        Mat croppedImage = calculateCroppingRect(countoursRects)
                .map((rect) -> crop(sourceImage, rect))
                .orElse(sourceImage);
        return new ImageWritable(OPEN_CV_FRAME_CONVERTER.convert(croppedImage));
    }

    private List<Rect> findCountoursRects(Mat image) {
        MatVector contours = new MatVector();
        Mat temporaryImage = new Mat();
        image.copyTo(temporaryImage);
        findContours(temporaryImage, contours, new Mat(), 3, 4, new Point(0, 0));
        List<Rect> rects = new ArrayList<>();

        for (int i = 0; (long) i < contours.size(); ++i) {
            Rect rect = boundingRect(contours.get((long) i));
            rects.add(rect);
        }

        return rects;
    }

    private Optional<CvRect> calculateCroppingRect(List<Rect> rects) {
        Rect biggestRect = rects.stream()
                .max(Comparator.comparingInt(this::getFieldOfFigure))
                .orElseThrow(() -> new RuntimeException("Rect not found"));
        List<Rect> closestRects = rects.stream()
                .filter(r -> getDistance(r, biggestRect) < this.maxRectDistance)
                .collect(Collectors.toList());
        Optional<Integer> maxX = closestRects.stream()
                .map(r -> r.x() + r.width())
                .max(Integer::compareTo);
        Optional<Integer> minX = closestRects.stream()
                .map(Rect::x)
                .min(Integer::compareTo);
        Optional<Integer> maxY = closestRects.stream()
                .map(r -> r.y() + r.height())
                .max(Integer::compareTo);
        Optional<Integer> minY = closestRects.stream()
                .map(Rect::y)
                .min(Integer::compareTo);

        if (!maxX.isPresent() || !minX.isPresent() || !maxY.isPresent() || !minY.isPresent()) {
            return Optional.empty();
        }

        int width = maxX.get() - minX.get();
        int height = maxY.get() - minY.get();
        return Optional.of(new CvRect(minX.get(), minY.get(), width, height));
    }

    private Mat crop(Mat sourceImage, CvRect rect) {
        IplImage sourceIplImage = new IplImage(sourceImage);
        cvSetImageROI(sourceIplImage, rect);
        IplImage croppedImage = cvCreateImage(cvGetSize(sourceIplImage), sourceIplImage.depth(), sourceIplImage.nChannels());
        cvCopy(sourceIplImage, croppedImage);
        return new Mat(croppedImage);
    }

    private Integer getDistance(Rect a, Rect b) {
        List<Integer> distances = new ArrayList<>();
        distances.add(this.getEuclidesDistance(a.x(), b.x(), a.y(), b.y()));
        distances.add(this.getEuclidesDistance(a.x(), b.x(), a.y(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x(), b.x(), a.y() + a.height(), b.y()));
        distances.add(this.getEuclidesDistance(a.x(), b.x(), a.y() + a.height(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x(), b.x() + b.width(), a.y(), b.y()));
        distances.add(this.getEuclidesDistance(a.x(), b.x() + b.width(), a.y(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x(), b.x() + b.width(), a.y() + a.height(), b.y()));
        distances.add(this.getEuclidesDistance(a.x(), b.x() + b.width(), a.y() + b.height(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x(), a.y(), b.y()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x(), a.y(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x(), a.y() + a.height(), b.y()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x(), a.y() + a.height(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x() + b.width(), a.y(), b.y()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x() + b.width(), a.y(), b.y() + b.height()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x() + b.width(), a.y() + a.height(), b.y()));
        distances.add(this.getEuclidesDistance(a.x() + a.width(), b.x() + b.width(), a.y() + a.height(), b.y() + b.height()));
        return distances.stream().map(Math::abs).min(Integer::compareTo).orElse(0);
    }

    private int getFieldOfFigure(Rect rect) {
        return rect.width() * rect.height();
    }

    private int getEuclidesDistance(int ax, int bx, int ay, int by) {
        return Math.abs(ax - bx) + Math.abs(ay - by);
    }
}
