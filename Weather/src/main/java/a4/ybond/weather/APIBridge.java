package a4.ybond.weather;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIBridge {
    // logics on how to call the api in this class
    // so that we can use it in other application like a different weather app

    private String apiKey, geocodingURL, weatherURL, weatherIconURL;
    private WeatherModel weatherModel = new WeatherModel();
    private UIBind uiBind;

    public APIBridge(UIBind uiBind) {
        //constructor

        //read from jason file to assign private String values
        this.uiBind = uiBind;
        try(Scanner scanner = new Scanner(new File("api.json"))) {
            // in api.json file, there are multiple lines
            // so, we are reading line by line
            StringBuilder apiJSONString = new StringBuilder();
            while(scanner.hasNextLine()) {
                // BUT string builder is much better
                // String is a mutable object, so += will create an object upon appending each time
                // This is why we use string builder that appends next line for us

                apiJSONString.append(scanner.nextLine());
            }

            // We are taking string from String Builder and passing it to the JSONObject
            JSONObject jsonObject = new JSONObject(apiJSONString.toString());
            this.apiKey = jsonObject.getString("apikey");
            this.geocodingURL = jsonObject.getString("geocodingURL");
            this.weatherURL = jsonObject.getString("weatherURL");
            this.weatherIconURL = jsonObject.getString("weatherIconURL");

            //System.out.println("test" + apiKey);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void GenerateWeatherModel(String loc) {

        // factory function to generate a weather object and send it back to us
        // to call REST service, we need HTTP request
        // HTTP client object
        HttpClient client = HttpClient.newBuilder().build();
        String URL = String.format(geocodingURL,
                URLEncoder.encode(loc, StandardCharsets.UTF_8),
                apiKey);

        // Request to send out to HTTP client
        HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(URL))
                            .header("Content-Type", "application/json").build();

        // sendAsync() so it would not lock up our application
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(response -> {
                           JSONArray tmp = new JSONArray(response.body());
                           weatherModel.setLat(tmp.getJSONObject(0).getDouble("lat"));
                           weatherModel.setLon(tmp.getJSONObject(0).getDouble("lon"));
                           //instead of calling uiBind, we will call getWeather()
                           getWeather(String.valueOf(weatherModel.getLat()), String.valueOf(weatherModel.getLon()));
                           //uiBind.mapUI(weatherModel);
                           return response.body();

                        });
        System.out.println(URL);

    }

    // to make the fact that we are creating another thread
    // we are creating another function instead of having a long GenerateWeatherModel()
    private void getWeather(String lat, String lon) {
        HttpClient client = HttpClient.newBuilder().build();
        String URL = String.format(weatherURL, lat, lon, apiKey);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
                .header("Content-Type", "application/json").build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response->{

                    JSONObject tmp = new JSONObject(response.body());
                    //parsing the tmp objects and getting the data and insert to weather model

                    // After adding windSpeed and windDirection in weather Model.java, parsing here
                    JSONObject wind = tmp.getJSONObject("wind");
                    weatherModel.setWindSpeed(wind.getDouble("speed"));
                    weatherModel.setWindDirection(wind.getInt("deg"));
                    // Go to WeatherViewController, and we can add the wind object variables

                    JSONObject weather = tmp.getJSONArray("weather").getJSONObject(0);
                    // After adding weatherIcon and weatherDescript in WeatherModel.java
                    // Parsing happens here
                    //weatherModel.setWeatherIcon(weather.getString("icon"));
                    // After adding weatherIconURL key - value, we can now pass the string parameter
                    // in the URL for the image
                    weatherModel.setWeatherIcon(String.format(weatherIconURL, weather.getString("icon")));
                    weatherModel.setWeatherDescript(weather.getString("description"));

                    JSONObject main = tmp.getJSONObject("main");

                    weatherModel.setTemp(main.getDouble("temp"));
                    weatherModel.setTempMin(main.getDouble("temp_min"));
                    weatherModel.setHumidity(main.getInt("humidity"));
                    weatherModel.setPressure(main.getInt("pressure"));
                    weatherModel.setFeelsLike(main.getDouble("feels_like"));
                    weatherModel.setTempMax(main.getDouble("temp_max"));


                    uiBind.mapUI(weatherModel);
                    return response.body();
                });
    }

    public WeatherModel getWeatherModel() {
        return weatherModel;
    }

    public void setWeatherModel(WeatherModel weatherModel) {
        this.weatherModel = weatherModel;
    }
}
