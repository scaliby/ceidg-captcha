package net.scaliby.ceidgcaptcha.rest.factory.impl;

import lombok.Setter;
import net.scaliby.ceidgcaptcha.rest.factory.LabelsFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

public class PropertiesLabelsFactory implements LabelsFactory {

    private static final String LABELS = "2,3,4,6,7,8,9,a,c,d,e,f,g,h,j,k,l,n,p,q,r,t,u,v,x,y,z";
    private static final String LABELS_SEPARATOR = ",";

    @Setter
    @Inject
    @Named("application.labels")
    private String labels = LABELS;

    @Setter
    private String labelsSeparator = LABELS_SEPARATOR;

    @Override
    public List<String> create() {
        String[] labels = this.labels.split(labelsSeparator);
        return Arrays.asList(labels);
    }
}
