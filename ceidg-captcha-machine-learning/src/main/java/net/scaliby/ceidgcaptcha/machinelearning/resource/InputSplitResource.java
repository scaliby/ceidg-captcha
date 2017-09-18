package net.scaliby.ceidgcaptcha.machinelearning.resource;

import lombok.Data;
import org.datavec.api.split.InputSplit;

@Data
public class InputSplitResource {

    private InputSplit train;
    private InputSplit test;

}
