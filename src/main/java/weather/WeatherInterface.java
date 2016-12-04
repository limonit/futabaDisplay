package weather;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by trojan on 30.11.16.
 */
public interface WeatherInterface {
    @GET("/data/2.5/forecast?id=756135&&lang=pl&units=metric&appid=" + key)
    void getData(Callback<Weather> pResponse);

    String key = "enter your key here";
}
