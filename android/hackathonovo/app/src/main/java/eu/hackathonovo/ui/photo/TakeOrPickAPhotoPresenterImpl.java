package eu.hackathonovo.ui.photo;

import android.app.Activity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.ScanImage;
import eu.hackathonovo.data.api.models.response.ImageResponse;
import eu.hackathonovo.data.api.models.response.ScanImageResponse;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.domain.usecase.LocalImagesUseCase;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

import static eu.hackathonovo.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static eu.hackathonovo.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;

public class TakeOrPickAPhotoPresenterImpl extends BasePresenter implements TakeOrPickAPhotoPresenter {

    private TakeOrPickAPhotoView view;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final StringManager stringManager;

    private final LocalImagesUseCase localImagesUseCase;

    private final NetworkService networkService;

    private final TemplatePreferences templatePreferences;

    private final Map<String, Double> predictionsMap = new HashMap<>();

    public TakeOrPickAPhotoPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                                         @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final StringManager stringManager,
                                         final LocalImagesUseCase localImagesUseCase, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.localImagesUseCase = localImagesUseCase;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final TakeOrPickAPhotoView view) {
        this.view = view;
    }

    @Override
    public void getImagesPath(final Activity activity) {
        if (view != null) {
            addDisposable(localImagesUseCase.getImagesPath(activity)
                                            .subscribeOn(subscribeScheduler)
                                            .observeOn(observeScheduler)
                                            .subscribe(this::onGetMovieInfoSuccess, this::onGetMovieInfoFailure));
        }
    }

    private void onGetMovieInfoFailure(final Throwable throwable) {
        Timber.e(stringManager.getString(R.string.local_image_error), throwable);
    }

    private void onGetMovieInfoSuccess(final List<String> imageList) {
        if (view != null) {
            view.showImagesPath(imageList);
        }
    }

    @Override
    public void scanImage(final File file) {
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
        addDisposable(networkService.scanImage(body)
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onScanImageSuccess, this::onScanImageFailure));
    }

    private void onScanImageSuccess(final ImageResponse scanImageResponse) {
        addDisposable(networkService.scanImageCustom(new ScanImage(scanImageResponse.path))
                                    .observeOn(observeScheduler)
                                    .subscribeOn(subscribeScheduler)
                                    .subscribe(this::onCustomVisionSuccess, this::onCustomVisionFailure));
    }

    private void onCustomVisionFailure(final Throwable throwable) {
        Timber.e(throwable);
    }

    private void onCustomVisionSuccess(final ScanImageResponse scanImageResponse) {
        predictionsMap.clear();
        for (ScanImageResponse.Predictions predictions : scanImageResponse.predictions) {
            predictionsMap.put(predictions.tag, predictions.probability);
            Timber.e(predictions.tag + " : " + predictions.probability);
        }
        if (predictionsMap.get("gljiva") > 0.5){
            Timber.e("Ovo je gljiva");
        }

        view.showDialog(predictionsMap);
    }

    private void onScanImageFailure(final Throwable throwable) {
        Timber.e(throwable);
    }


}


