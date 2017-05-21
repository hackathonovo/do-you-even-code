package eu.hackathonovo.injection.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.data.api.NetworkInterceptor;
import eu.hackathonovo.data.service.CustomVisionAPI;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.service.NetworkServiceImpl;
import eu.hackathonovo.data.service.TemplateAPI;
import eu.hackathonovo.device.ApplicationInformation;
import eu.hackathonovo.device.DeviceInformation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static eu.hackathonovo.data.api.APIConstants.BASE_URL;
import static eu.hackathonovo.data.api.APIConstants.BASE_URL2;

@Module
public final class ApiModule {

    private static final String RETROFIT_1 = "retrofit1";
    private static final String RETROFIT_2 = "retrofit2";
    private static final int CONNECTION_TIMEOUT = 10;

    @Provides
    @Singleton
    @Named(RETROFIT_1)
    Retrofit provideRetrofit(final OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(RETROFIT_2)
    Retrofit provideRetrofit2(final OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService(final TemplateAPI templateAPI, final CustomVisionAPI customVisionAPI) {
        return new NetworkServiceImpl(templateAPI, customVisionAPI);
    }

    @Provides
    @Singleton
    TemplateAPI provideTemplateAPI(@Named(RETROFIT_1) final Retrofit retrofit) {
        return retrofit.create(TemplateAPI.class);
    }

    @Provides
    @Singleton
    CustomVisionAPI provideCustomVisionAPI(@Named(RETROFIT_2) final Retrofit retrofit) {
        return retrofit.create(CustomVisionAPI.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(final HttpLoggingInterceptor loggingInterceptor, final NetworkInterceptor networkInterceptor) {
        final OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder()
                .addInterceptor(networkInterceptor);
        okhttpBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).interceptors().add(loggingInterceptor);

        return okhttpBuilder.build();
    }

    @Provides
    @Singleton
    public NetworkInterceptor provideNetworkInterceptor(final DeviceInformation deviceInformation,
                                                        final ApplicationInformation applicationInformation) {
        final int osVersion = deviceInformation.getOsVersionInt();
        final String appVersionName = applicationInformation.getVersionName();

        return new NetworkInterceptor(osVersion, appVersionName);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }
}
