package application;

import com.google.gson.GsonBuilder;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import weather.Weather;
import weather.WeatherInterface;
import widget.ScrollingWidget;

/**
 * Created by trojan on 30.11.16.
 */
public class WeatherReceinver  {

    WeatherInterface api;

    public void getWeather(ScrollingWidget scrollingWidget){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/")
 //               .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .build();
        api = restAdapter.create(WeatherInterface.class);
        api.getData(scrollingWidget);
    }
}
