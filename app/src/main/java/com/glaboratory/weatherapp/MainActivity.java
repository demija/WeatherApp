package com.glaboratory.weatherapp;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaboratory.weatherapp.model.Forecast;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    final private ObjectMapper objectMapper = new ObjectMapper();
    private Forecast forecast;
    private LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();
//        ButterKnife.bind(this);

        //mProgressBar.setVisibility(View.INVISIBLE);

        getForecast(43.856430, 18.413029);

//        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gettingLocation();
//            }
//        });

        Log.d(TAG, "Main UI code is running!");
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 10:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    locate();
//                }
//                return;
//
//            case 20:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //stopLocating();
//                }
//                return;
//        }
//    }

//    private void gettingLocation() {
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                getForecast(location.getLatitude(), location.getLongitude());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        };
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
//                }, 10);
//                return;
//            }
//        } else {
//            locate();
//        }
//    }

//    private void locate() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See t
//            // he documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);
//        }
//    }

    private void getForecast(double latitude, double longitude) {
        String forecastUrl = "https://api.openweathermap.org/data/2.5/onecall";

        if(isNetworkAvailable()) {
            HttpUrl.Builder httpBuilder = HttpUrl.parse(forecastUrl).newBuilder();
            httpBuilder.addQueryParameter("lat", String.valueOf(latitude));
            httpBuilder.addQueryParameter("lon", String.valueOf(longitude));
            httpBuilder.addQueryParameter("appid","cf06fb984f9d43a6b63abdfbbf30785d");
            httpBuilder.addQueryParameter("units","metric");
            httpBuilder.addQueryParameter("exclude","minutely");

            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .build();

            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        forecast = objectMapper.readValue(response.body().string(), Forecast.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });
                    } else {
                        alertUserAboutError();
                    }
                }
            });
        } else{
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void updateDisplay() {
        TextView mLocation = (TextView) findViewById(R.id.locationLabel);
        Objects.requireNonNull(mLocation).setText(forecast.getTimezone());

        TextView mTemperatureLabel = (TextView) findViewById(R.id.temperatureLabel);
        Objects.requireNonNull(mTemperatureLabel).setText(String.valueOf(Math.round(forecast.getCurrent().getTemp())));

        TextView mDescriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        Objects.requireNonNull(mDescriptionLabel).setText(forecast.getCurrent().getWeather().get(0).getMain());

//        mTimeLable.setText("Last update: " + mCurrentWeather.getFormattedTime());
//        mHumidityValue.setText(mCurrentWeather.getHumidity() + "");
//        mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
//        mSummaryLabel.setText(mCurrentWeather.getSummary());
//        Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
//        mIconImageView.setImageDrawable(drawable);
//        mWindValue.setText(mCurrentWeather.getWindSpeed() + "");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            Drawable background = getResources().getDrawable(mCurrentWeather.getBackgroundId());
//            mRelativeLayout.setBackground(background);
//        }
//
//        mFirstDayTemp.setText(mOneDayWeather.getMinTemperature() + " / " + mOneDayWeather.getMaxTemperature() + " \u2103");
//        //mFirstDayPrecipLabel.setText(mOneDayWeather.getPrecipType() + " ");
//        //mFirstDayPrecipValue.setText(mOneDayWeather.getPrecipChance() + "%");
//        Drawable firstDayDrawable = getResources().getDrawable(mOneDayWeather.getIconId());
//        mFirstDayImageView.setImageDrawable(firstDayDrawable);
//
//        mSecondDayTemp.setText(mTwoDaysWeather.getMinTemperature() + " / " + mTwoDaysWeather.getMaxTemperature() + " \u2103");
//        //mSecondDayPrecipLabel.setText(mTwoDaysWeather.getPrecipType() + " ");
//        //mSecondDayPrecipValue.setText(mTwoDaysWeather.getPrecipChance() + "%");
//        Drawable secondDayDrawable = getResources().getDrawable(mTwoDaysWeather.getIconId());
//        mSecondDayImageView.setImageDrawable(secondDayDrawable);
//
//        mThirdDayTemp.setText(mThreeDaysWeather.getMinTemperature() + " / " + mThreeDaysWeather.getMaxTemperature() + " \u2103");
//        //mThirdDayPrecipLabel.setText(mThreeDaysWeather.getPrecipType() + " ");
//        //mThirdDayPrecipValue.setText(mThreeDaysWeather.getPrecipChance() + "%");
//        Drawable thirdDayDrawable = getResources().getDrawable(mThreeDaysWeather.getIconId());
//        mThirdDayImageView.setImageDrawable(thirdDayDrawable);
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
