package net.scaliby.ceidgcaptcha.common;

import com.google.inject.AbstractModule;
import net.scaliby.ceidgcaptcha.common.common.StoreChooser;
import net.scaliby.ceidgcaptcha.common.common.impl.SwingStoreChooser;
import net.scaliby.ceidgcaptcha.common.factory.JFileChooserFactory;
import net.scaliby.ceidgcaptcha.common.factory.impl.JFileChooserFactoryImpl;

public class CEIDGCaptchaCommonModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StoreChooser.class).to(SwingStoreChooser.class);
        bind(JFileChooserFactory.class).to(JFileChooserFactoryImpl.class);
    }
}
