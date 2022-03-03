package a4.ybond.weather;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WeatherViewController implements UIBind {
    private APIBridge apiBridge;

    @FXML
    Label lablLatitude, lablLongitude, lablHigh, lablLow, lablCurrent, lablFeelsLike,
    lablPressure, lablHumidity, lablWeatherDescript, lablWindSpeed;

    @FXML
    ComboBox comboLoc;

    // After adding the sunny weather image in weather-view.fxml
    // You need to reference it as below:
    @FXML
    ImageView imgWeatherIcon;

    // Again, adding wind direction and background images, add reference of them
    @FXML
    ImageView imgWindDirection;


    @FXML
    protected void onLocationChanged(){


        String loc = comboLoc.getSelectionModel().getSelectedItem().toString();
        // Everytime location changes, generate the URL
        // this is like a thread
        apiBridge.GenerateWeatherModel(loc);
        // System.out.println(apiBridge.getWeatherModel().getLat());
    }

    // calling a webpage from your app
    // http request : as if we are a browser, we can request a website
    // http://api.openweathermap.org/geo/1.0/direct?q=Bakersfield,CA,USA&appid=3d8dfc8fb99589bb0a406ee923bad6dc
    // we are to obtain longitude and latitude info from the raw data we checked using our browser

    @FXML
    protected void initialize() {
        // apiBridge variable
        apiBridge = new APIBridge(this);

    }

    @Override
    public void mapUI(WeatherModel weatherModel) {
        //System.out.println(weatherModel.getLat());
        //System.out.println(weatherModel.getLon());

        // lablLatitude.setText(String.valueOf(weatherModel.getLat()));
        // Now, we can call the setLabelText() instead of using the line above
        setLabelText(lablLatitude, String.valueOf(weatherModel.getLat()));
        setLabelText(lablLongitude, String.valueOf(weatherModel.getLon()));
        // without String.valueOf(), by using + "", it changes now to a string value
        setLabelText(lablHigh, weatherModel.getTempMax() + "ᵒ");
        setLabelText(lablLow, weatherModel.getTempMin() + "ᵒ");
        setLabelText(lablCurrent, weatherModel.getTemp() + "ᵒ");
        setLabelText(lablFeelsLike, weatherModel.getFeelsLike() + "ᵒ");
        setLabelText(lablHumidity, weatherModel.getHumidity() + "%");
        setLabelText(lablPressure, weatherModel.getPressure() + "hPa");
        setLabelText(lablWeatherDescript, weatherModel.getWeatherDescript());

        // Now, we need to set image for our icon
        imgWeatherIcon.setImage(new Image(weatherModel.getWeatherIcon()));

        // wind speed value changes per location
        setLabelText(lablWindSpeed, weatherModel.getWindSpeed() + "MPH");
        // using setRotate(), it is easy to rotate the wind direction arrow
        imgWindDirection.setRotate(weatherModel.getWindDirection());

    }

    private void setLabelText(Label label, String val) {
        // JavaFX runs this when its are ready, using random lambda function
        // label will set the value
        Platform.runLater(() -> label.setText(val));

    }

}