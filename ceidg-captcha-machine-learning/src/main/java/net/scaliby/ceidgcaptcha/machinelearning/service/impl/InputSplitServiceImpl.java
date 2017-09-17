package net.scaliby.ceidgcaptcha.machinelearning.service.impl;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.scaliby.ceidgcaptcha.common.common.StoreChooser;
import net.scaliby.ceidgcaptcha.common.exception.ImageStoreException;
import net.scaliby.ceidgcaptcha.machinelearning.model.InputSplitResource;
import net.scaliby.ceidgcaptcha.machinelearning.service.InputSplitService;
import org.datavec.api.io.filters.BalancedPathFilter;
import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.datavec.image.loader.NativeImageLoader;

import javax.inject.Inject;
import java.io.File;
import java.util.Random;

@Log4j
public class InputSplitServiceImpl implements InputSplitService {

    private static final float TRAIN_WEIGHT = 0.75f;
    private static final float TEST_WEIGHT = 0.25f;

    private final PathLabelGenerator pathLabelGenerator;
    private final Random random;
    private final StoreChooser storeChooser;

    @Setter
    private float trainWeight = TRAIN_WEIGHT;
    @Setter
    private float testWeight = TEST_WEIGHT;

    @Inject
    public InputSplitServiceImpl(PathLabelGenerator pathLabelGenerator, Random random, StoreChooser storeChooser) {
        this.pathLabelGenerator = pathLabelGenerator;
        this.random = random;
        this.storeChooser = storeChooser;
    }

    @Override
    public InputSplitResource getInputSplit() {
        log.info("Select images location");
        File file = storeChooser.getDirectory()
                .orElseThrow(() -> new ImageStoreException("No store chosen"));
        FileSplit fileSplit = new FileSplit(file, NativeImageLoader.ALLOWED_FORMATS, random);
        BalancedPathFilter pathFilter = new BalancedPathFilter(random, NativeImageLoader.ALLOWED_FORMATS, pathLabelGenerator);

        InputSplit[] inputSplit = fileSplit.sample(pathFilter, trainWeight, testWeight);

        InputSplitResource resource = new InputSplitResource();
        resource.setTrain(inputSplit[0]);
        resource.setTest(inputSplit[1]);
        return resource;
    }
}
