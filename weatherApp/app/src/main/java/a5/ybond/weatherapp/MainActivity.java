package a5.ybond.weatherapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements UIBind{

    private Spinner spinnerLocations;
    private TextView txtLatitude, txtLongitude, txtHigh, txtLow, txtCurrent, txtFeelsLike,
        txtPressure, txtHumidity, txtWeatherDescript, txtWindSpeed;
    private APIBridge apiBridge;

    NetworkImageView imgWeatherIcon;
    ImageView imgWindDirection;

    RequestQueue requestQueue;
    ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // passing UIBind and context
        this.apiBridge = new APIBridge(this, getApplicationContext());

        this.txtLatitude = findViewById(R.id.txtLatitude);
        this.txtLongitude = findViewById(R.id.txtLongitude);
        this.txtHigh = findViewById(R.id.txtHigh);
        this.txtLow = findViewById(R.id.txtLow);
        this.txtFeelsLike = findViewById(R.id.txtFeelsLike);
        this.txtCurrent = findViewById(R.id.txtCurrent);
        this.txtPressure = findViewById(R.id.txtPressure);
        this.txtHumidity = findViewById(R.id.txtHumidity);

        this.txtWeatherDescript = findViewById(R.id.txtWeatherDescript);
        this.txtWindSpeed = findViewById(R.id.txtWindSpeed);
        this.imgWindDirection = findViewById(R.id.imgWindDirection);

        this.imgWeatherIcon = findViewById(R.id.imgWeatherIcon);

        this.requestQueue = Volley.newRequestQueue(this);
        // In-line object, new ImageLoader.ImageCache() is an interface of the image cache
        // instead of making a new class
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            // data member for new ImageLoader
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
            @Override
            // url is taken as the key and bitmap is placed into a cache for us
            public void putBitmap(String url, Bitmap bitmap) {
                // after getting a new image from the internet
                // we need to store it into that image cache looked up by the getBitmap()
                mCache.put(url, bitmap);

            }

        });



        //everything we put into Android activity is a view
        //R is a special object that holds our resources
        //spinLocations is the spinner id
        this.spinnerLocations = findViewById(R.id.spinLocations);

        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // this is basically a class
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log is a class to print out what item is selected
                // LOCATION is a tag we can search via
                Log.i("LOCATION", spinnerLocations.getSelectedItem().toString());
                apiBridge.GenerateWeatherModel(spinnerLocations.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    @Override
    public void mapUI(WeatherModel weatherModel) {
        // mapping Latitude and Longitude values
        txtLatitude.setText(String.valueOf(weatherModel.getLat()));
        txtLongitude.setText(String.valueOf(weatherModel.getLon()));

        txtHigh.setText(String.format("%s F", weatherModel.getTempMax()));
        txtLow.setText(String.format("%s F", weatherModel.getTempMin()));
        txtCurrent.setText(String.format("%s F", weatherModel.getTemp()));

        txtFeelsLike.setText(String.format("%s F", weatherModel.getFeelsLike()));
        txtPressure.setText(String.format("%s hPa", weatherModel.getPressure()));
        txtHumidity.setText(String.format("%d %%", weatherModel.getHumidity()));

        imgWeatherIcon.setImageUrl(weatherModel.getWeatherIcon(), this.imageLoader);
        txtWeatherDescript.setText(weatherModel.getWeatherDescript());
        txtWindSpeed.setText(String.format("%s MPH", weatherModel.getWindSpeed()));

        imgWindDirection.setRotation(weatherModel.getWindDirection());

    }
}