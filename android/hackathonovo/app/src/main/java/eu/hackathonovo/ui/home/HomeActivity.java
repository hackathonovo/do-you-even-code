package eu.hackathonovo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;

import java.util.Timer;
import java.util.TimerTask;

import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeView, SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private PowerManager.WakeLock wakeLock;
    private boolean isAquired = false;
    private boolean isKnocked = false;
    private static double vector;

    public static Intent createIntent(final Context context){
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if (isAquired) {
            wakeLock.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        isAquired = true;
        wakeLock.acquire();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        senSensorManager.unregisterListener(this);
        wakeLock.release();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            vector = Math.round(Math.sqrt(Math.pow(x, 2)
                                                  + Math.pow(y, 2)
                                                  + Math.pow(z, 2)));

            if (vector <= 0) {
                if (!isKnocked) {
                    Timber.e("UDARAC!!!!!!!!!");
                    isKnocked = true;
                    new Timer().schedule(
                            new TimerTask() {

                                @Override
                                public void run() {
                                    final long startTime = System.currentTimeMillis();
                                    while (true) {
                                        if ((System.currentTimeMillis() -startTime) >= 5000) {
                                            //postavi varijablu
                                            Timber.e("proslo je 5 s");
                                            isKnocked = false;
                                            break;
                                        }
                                        if (vector != 10) {
                                            //postavi varijablu flag
                                            Timber.e("pomaknuo se mobitel");
                                            isKnocked = false;
                                            break;
                                        }
                                    }
                                }
                            },
                            1000
                    );
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int accuracy) {

    }
}
