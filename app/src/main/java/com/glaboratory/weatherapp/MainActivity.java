package com.glaboratory.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather mCurrentWeather, mOneDayWeather, mTwoDaysWeather, mThreeDaysWeather;
    private LocationListener locationListener;
    private LocationManager locationManager;

    @Bind(R.id.locationLabel) TextView mLocation;
    @Bind(R.id.temperatureLable) TextView mTemperatureLable;
    @Bind(R.id.timeLable) TextView mTimeLable;
    @Bind(R.id.humidityValue) TextView mHumidityValue;
    @Bind(R.id.precipValue) TextView mPrecipValue;
    @Bind(R.id.precipLabel) TextView mPrecipLabel;
    @Bind(R.id.windValue) TextView mWindValue;
    @Bind(R.id.summaryLabel) TextView mSummaryLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.firstDayImageView) ImageView mFirstDayImageView;
    @Bind(R.id.firstDayTempLabel) TextView mFirstDayTemp;
    @Bind(R.id.firstDayPrecipLabel) TextView mFirstDayPrecipLabel;
    @Bind(R.id.firstDayPrecipValue) TextView mFirstDayPrecipValue;
    @Bind(R.id.secondDayImageView) ImageView mSecondDayImageView;
    @Bind(R.id.secondDayTempLabel) TextView mSecondDayTemp;
    @Bind(R.id.secondDayPrecipLabel) TextView mSecondDayPrecipLabel;
    @Bind(R.id.secondDayPrecipValue) TextView mSecondDayPrecipValue;
    @Bind(R.id.thirdDayImageView) ImageView mThirdDayImageView;
    @Bind(R.id.thirdDayTempLabel) TextView mThirdDayTemp;
    @Bind(R.id.thirdDayPrecipLabel) TextView mThirdDayPrecipLabel;
    @Bind(R.id.thirdDayPrecipValue) TextView mThirdDayPrecipValue;
    @Bind(R.id.relativeLayout) RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        gettingLocation();

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettingLocation();
            }
        });

        Log.d(TAG, "Main UI code is running!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locate();
                }
                return;

            case 20:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //stopLocating();
                }
                return;
        }
    }

    private void gettingLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getForecast(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            locate();
        }
    }

    private void locate() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "a384cf465dcd5f5aaa565b930d0066af";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey + "/" + latitude + "," + longitude;

        if(isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else{
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else{
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        mLocation.setText(mCurrentWeather.getTimeZone());
        mTemperatureLable.setText(mCurrentWeather.getTemperature() + " \u2103");
        mTimeLable.setText("Last update: " + mCurrentWeather.getFormattedTime());
        mHumidityValue.setText(mCurrentWeather.getHumidity() + "");
        mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
        mSummaryLabel.setText(mCurrentWeather.getSummary());
        Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
        mWindValue.setText(mCurrentWeather.getWindSpeed() + "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable background = getResources().getDrawable(mCurrentWeather.getBackgroundId());
            mRelativeLayout.setBackground(background);
        }

        mFirstDayTemp.setText(mOneDayWeather.getMinTemperature() + " / " + mOneDayWeather.getMaxTemperature() + " \u2103");
        mFirstDayPrecipLabel.setText(mOneDayWeather.getPrecipType() + " ");
        mFirstDayPrecipValue.setText(mOneDayWeather.getPrecipChance() + "%");
        Drawable firstDayDrawable = getResources().getDrawable(mOneDayWeather.getIconId());
        mFirstDayImageView.setImageDrawable(firstDayDrawable);

        mSecondDayTemp.setText(mTwoDaysWeather.getMinTemperature() + " / " + mTwoDaysWeather.getMaxTemperature() + " \u2103");
        mSecondDayPrecipLabel.setText(mTwoDaysWeather.getPrecipType() + " ");
        mSecondDayPrecipValue.setText(mTwoDaysWeather.getPrecipChance() + "%");
        Drawable secondDayDrawable = getResources().getDrawable(mTwoDaysWeather.getIconId());
        mSecondDayImageView.setImageDrawable(secondDayDrawable);

        mThirdDayTemp.setText(mThreeDaysWeather.getMinTemperature() + " / " + mThreeDaysWeather.getMaxTemperature() + " \u2103");
        mThirdDayPrecipLabel.setText(mThreeDaysWeather.getPrecipType() + " ");
        mThirdDayPrecipValue.setText(mThreeDaysWeather.getPrecipChance() + "%");
        Drawable thirdDayDrawable = getResources().getDrawable(mThreeDaysWeather.getIconId());
        mThirdDayImageView.setImageDrawable(thirdDayDrawable);
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);
        currentWeather.setWindSpeed(currently.getDouble("windSpeed"));

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray niz = daily.getJSONArray("data");

        JSONObject firstDay = (JSONObject) niz.get(0);
        CurrentWeather firstDayWeather = new CurrentWeather();
        firstDayWeather.setIcon(firstDay.getString("icon"));
        firstDayWeather.setMinTemperature(firstDay.getDouble("temperatureMin"));
        firstDayWeather.setMaxTemperature(firstDay.getDouble("temperatureMax"));
        firstDayWeather.setHumidity(firstDay.getDouble("humidity"));
        firstDayWeather.setPrecipChance(firstDay.getDouble("precipProbability"));
        firstDayWeather.setPrecipType(firstDay.getString("precipType"));
        firstDayWeather.setWindSpeed(firstDay.getDouble("windSpeed"));
        mOneDayWeather = firstDayWeather;

        JSONObject secondDay = (JSONObject) niz.get(1);
        CurrentWeather secondDayWeather = new CurrentWeather();
        secondDayWeather.setIcon(secondDay.getString("icon"));
        secondDayWeather.setMinTemperature(secondDay.getDouble("temperatureMin"));
        secondDayWeather.setMaxTemperature(secondDay.getDouble("temperatureMax"));
        secondDayWeather.setHumidity(secondDay.getDouble("humidity"));
        secondDayWeather.setPrecipChance(secondDay.getDouble("precipProbability"));
        //secondDayWeather.setPrecipType(secondDay.getString("precipType"));
        secondDayWeather.setWindSpeed(secondDay.getDouble("windSpeed"));
        mTwoDaysWeather = secondDayWeather;

        JSONObject thirdDay = (JSONObject) niz.get(2);
        CurrentWeather thirdDayWeather = new CurrentWeather();
        thirdDayWeather.setIcon(thirdDay.getString("icon"));
        thirdDayWeather.setMinTemperature(thirdDay.getDouble("temperatureMin"));
        thirdDayWeather.setMaxTemperature(thirdDay.getDouble("temperatureMax"));
        thirdDayWeather.setHumidity(thirdDay.getDouble("humidity"));
        thirdDayWeather.setPrecipChance(thirdDay.getDouble("precipProbability"));
        //thirdDayWeather.setPrecipType(thirdDay.getString("precipType"));
        thirdDayWeather.setWindSpeed(thirdDay.getDouble("windSpeed"));
        mThreeDaysWeather = thirdDayWeather;

        Log.d(TAG, String.valueOf(currentWeather));

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }

        return  isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}
