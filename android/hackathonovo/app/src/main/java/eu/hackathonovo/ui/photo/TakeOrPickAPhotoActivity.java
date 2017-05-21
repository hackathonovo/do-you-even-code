package eu.hackathonovo.ui.photo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.device.PhotoRotationHandler;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public class TakeOrPickAPhotoActivity extends BaseActivity implements TakeOrPickAPhotoView, SelectedPhotoListener, SelectedPhotoFragment.SendPhotoInterface {

    public static Intent createIntent(final Context context) {
        return new Intent(context, TakeOrPickAPhotoActivity.class);
    }

    private static final int COMPRESS_QUALITY = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CROP_IMAGE_WIDTH = 250;
    private static final int CROP_IMAGE_HEIGHT = 208;

    @Inject
    TakeOrPickAPhotoPresenter presenter;

    @BindView(R.id.take_or_pick_a_photo_activity_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.photo_recycler_view)
    RecyclerView photoRecyclerView;

    @BindView(R.id.image_view_pager)
    ViewPager imageViewPager;

    private static final int OFFSET_TO_CENTER_IMAGE = -70;
    private static final int MOVE_TO_POSITION_TO_CENTER_SELECTED_IMAGE = 2;
    List<String> imageList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    int positionInList;
    TakeorPickaAPhotoAdapter adapter;

    File photoFile = null;
    File imageFile = null;
    private Bitmap imageToSave = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_or_pick_aphoto);

        ButterKnife.bind(this);
        setUpActionBar();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), imageList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(positionInList);
    }

    private void setUpActionBar() {
/*        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "" + "</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                                 WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
    }



    @Override
    public void sendPhoto(final File photo) {

    }

    @Override
    public void takeAnotherPhoto() {
        takeAnotherPhotoWithCamera();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getImagesPath(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.dispose();
    }

    @Override
    public void showImagesPath(final List<String> imageList) {
        this.imageList = imageList;

        positionInList = 0;

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        photoRecyclerView.setHasFixedSize(true);
        photoRecyclerView.setLayoutManager(layoutManager);

        checkPositionOffSelectedImage(positionInList);
        adapter = new TakeorPickaAPhotoAdapter(TakeOrPickAPhotoActivity.this, imageList, this, positionInList);

        photoRecyclerView.setAdapter(adapter);

        setupViewPager(imageViewPager);

        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positionInList = position;
                checkPositionOffSelectedImage(position);
                adapter.changePosition(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkPositionOffSelectedImage(int positionInList) {
        if (positionInList < 2) {
            layoutManager.scrollToPosition(positionInList);
        } else {
            final float scale = TakeOrPickAPhotoActivity.this.getResources().getDisplayMetrics().density;
            int pixels = (int) (OFFSET_TO_CENTER_IMAGE * scale + 0.5f);
            layoutManager.scrollToPositionWithOffset(positionInList - MOVE_TO_POSITION_TO_CENTER_SELECTED_IMAGE, pixels);
        }
    }

    @Override
    public void onClicked(final int position) {
        positionInList = position;
        imageViewPager.setCurrentItem(position);
    }

    private void takeAnotherPhotoWithCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                photoFile = new File(TakeOrPickAPhotoActivity.this.getExternalCacheDir(), "image_taken.jpeg");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageToSave = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File outputDir = this.getCacheDir();
            try {
                Uri photoURI = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                } else {
                    if (photoFile != null) {
                        photoURI = Uri.fromFile(photoFile);
                    }
                }
                imageFile = File.createTempFile("profile_img", "jpeg", outputDir);
                if (photoURI != null) {
                    Crop.of(photoURI, Uri.fromFile(imageFile)).withAspect(CROP_IMAGE_WIDTH, CROP_IMAGE_HEIGHT).start(TakeOrPickAPhotoActivity.this);
                } else {
                    Log.e("GRESKA", "PHOTO URI NULL");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            try {
                imageToSave = PhotoRotationHandler.rotateBitmap(imageFile.getAbsolutePath());
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                imageToSave.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                //File file = new File("/storage/emulated/0/DCIM/Screenshots/Screenshot_20170515-014140.png");
                final File file = new File(getCacheDir(), "slika");
                file.createNewFile();
                byte[] bytes = bs.toByteArray();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.flush();
                fos.close();
                //String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                presenter.scanImage(file);
                //presenter.uploadImage(encodedImage);
                //Log.e("ENCODED IMAGE", encodedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void goToPhotoDetails(final String url) {
        startActivity(PhotoDetailsActivity.createIntent(this, url));
    }
}
