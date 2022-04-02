package a8.ybond.javafxtodo;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
//43:05 can't add dependency
// no update on MAVEN

// to send the added items to our database
public class APIBridge {

    // private static client is needed
    private static final HttpClient client = HttpClient.newBuilder().build();
    private static String baseURL = "https://javafxtodo-c03ab-default-rtdb.firebaseio.com/";
    private static final String apiKey = "hFcAZigDoYnb5kKGFmyNc5R91szmGBWe137bW3Dv";


    public static void addItem(String selectedTab, Item item)
    {
        // to have url encoded
        String path = URLEncoder.encode(item.getDescription(), StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(item);

        // we need http class to build http Client and send a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + selectedTab + "/" + path + ".json?auth=" + apiKey))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(obj.toString()))
                .build();

        try {   client.send(request, HttpResponse.BodyHandlers.discarding());    }
        catch (IOException | InterruptedException e) {  e.printStackTrace();    }


    }

    public static void getList(String selectedTab, ObservableList<Item> items)
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + selectedTab + ".json?auth=" + apiKey))
                .header("Content-Type", "application/json")
                .build();

        // getting items might take a while, so we are using Async here
        // getting the JSONObject from the response then iterating over the keys to get each value
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    JSONObject obj = new JSONObject(response.body());
                    Iterator<String> keys = obj.keys();

                    while(keys.hasNext())
                    {
                        JSONObject jsonItem = obj.getJSONObject(keys.next());
                        Platform.runLater(() -> {
                            items.add(new Item(jsonItem.getString("description")));

                        });
                    }

                    return response;

                });

    }

    public static void deleteItem(String selectedTab, Item selectedItem)
    {
        // to delete an item, we need a specific path of the item
        String path = URLEncoder.encode(selectedItem.getDescription(), StandardCharsets.UTF_8);

        // then, we need a HttpRequest item
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + selectedTab + "/" + path + ".json?auth=" + apiKey))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("Item deleted from database");
                    return response;

                });

    }
}


















