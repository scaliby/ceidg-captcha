package net.scaliby.ceidgcaptcha.common;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.Setter;

import java.io.IOException;
import java.util.Properties;

public class CEIDGCaptchaPropertiesModule extends AbstractModule {

    private static final String PROPERTIES_FILE_NAME = "application.properties";

    @Setter
    private String propertiesFileName = PROPERTIES_FILE_NAME;

    @Override
    protected void configure() {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = this.getClass().getClassLoader();
            properties.load(classLoader.getResourceAsStream(propertiesFileName));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
