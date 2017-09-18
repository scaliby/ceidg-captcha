package net.scaliby.ceidgcaptcha.machinelearning.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.Setter;

import javax.inject.Named;
import java.util.Random;

public class RandomProvider implements Provider<Random> {

    private static final int SEED = 1337;

    @Setter
    @Inject(optional = true)
    @Named("application.seed")
    private int seed = SEED;

    @Override
    public Random get() {
        return new Random(seed);
    }
}
