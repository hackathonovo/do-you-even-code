package eu.hackathonovo.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.device.ApplicationInformation;
import eu.hackathonovo.device.ApplicationInformationImpl;
import eu.hackathonovo.device.DeviceInformation;
import eu.hackathonovo.device.DeviceInformationImpl;

@Module
public final class DeviceModule {

    @Provides
    @Singleton
    public DeviceInformation provideDeviceInformation() {
        return new DeviceInformationImpl();
    }

    @Provides
    @Singleton
    public ApplicationInformation provideApplicationInformation(final HackathonovoApplication application) {
        return new ApplicationInformationImpl(application, application.getPackageManager());
    }
}
