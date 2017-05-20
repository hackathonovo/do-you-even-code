package eu.hackathonovo.ui.home_rescuer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.RescuerLocation;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import timber.log.Timber;

public class HomeRescuerActivity extends BaseActivity implements HomeRescuerView {

    @Inject
    HomeRescuerPresenter presenter;

    private LocationManager locationManager;

    public static Intent createIntent(final Context context) {
        return new Intent(context, HomeRescuerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rescuer);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {

                    @Override
                    public void onLocationChanged(final Location location) {

                        presenter.sendRescuersLocation(new RescuerLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()),0));
                        Timber.e(String.valueOf(location.getLatitude()));
                        Timber.e(String.valueOf(location.getLongitude()));
                    }

                    @Override
                    public void onStatusChanged(final String provider, final int status, final Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(final String provider) {

                    }

                    @Override
                    public void onProviderDisabled(final String provider) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zoviVoditelja:
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0977940915")));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            Timber.e(String.valueOf(loc.getLatitude()));
            Timber.e(String.valueOf(loc.getLongitude()));
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
