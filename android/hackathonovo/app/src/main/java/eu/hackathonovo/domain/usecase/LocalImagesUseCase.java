package eu.hackathonovo.domain.usecase;

import android.app.Activity;

import java.util.List;

import io.reactivex.Single;

public interface LocalImagesUseCase {
    Single<List<String>> getImagesPath(Activity activity);
}
