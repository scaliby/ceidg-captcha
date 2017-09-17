package net.scaliby.ceidgcaptcha.common;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@AllArgsConstructor
public class CEIDGCaptchaPropertiesModule extends AbstractModule {

    private final String propertiesFileName;

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
