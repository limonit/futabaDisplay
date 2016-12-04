package widget;

import application.WeatherReceinver;
import driver.FutabaDisplay;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import weather.List;
import weather.Weather;

import java.nio.channels.Pipe;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.round;


/**
 * Created by trojan on 30.11.16.
 */
public class ScrollingWidget implements Widget , Callback<Weather>
{

    private int position;
    private int viewportLength;
    private double interval=0.3;
    private String text = "No data";
    private int currentTextPosition = 0;
    private int scrollCounter = 0;

    private boolean hasEverApiBeenCalled = false;
    private  LocalDateTime nextApiCallTime;

    public ScrollingWidget() {
    }

    @Override
    public void redraw(FutabaDisplay display) {

        if(interval <= ++scrollCounter / (double)10) {
            scroll(display);
            scrollCounter = 0;
        }

        if(!hasEverApiBeenCalled || nextApiCallTime.isBefore(LocalDateTime.now())) {
            new WeatherReceinver().getWeather(this);
            hasEverApiBeenCalled = true;
            nextApiCallTime = LocalDateTime.now().plusMinutes(1);
        }
    }

    public int getPosition() {
        return position;
    }

    public int getViewportLength() {
        return viewportLength;
    }

    public void setViewportLength(int viewportLength) {
        this.viewportLength = viewportLength;
    }

    public void scroll(FutabaDisplay display){
        currentTextPosition++;
        display.setDisplayPosition(position);
        if(text.length() <= viewportLength){
            display.printString(text);
            return;
        }

        String separator = " *** ";

        String textLocal = text + separator;
        int endPosition = viewportLength + currentTextPosition;
        int remainingPart;
        if(endPosition >= textLocal.length()) {
            remainingPart =  endPosition - textLocal.length();
            endPosition = textLocal.length();
        }
        else{
            remainingPart = 0;
        }

        String toBeDisplayed = textLocal.substring(currentTextPosition, endPosition) +  textLocal.substring(0, remainingPart);

        if(currentTextPosition == textLocal.length()) currentTextPosition = 0;

        display. printString(toBeDisplayed);
    }

    void updateWeather(Weather weather){
        currentTextPosition = 0;
        String separator = " -*- ";
        StringBuilder builder = new StringBuilder();
        builder.append(weatherDescForNoon(0, weather));
        builder.append(separator);
        builder.append(weatherDescForNoon(1, weather));
        builder.append(separator);
        builder.append(weatherDescForNoon(2, weather));
        text = builder.toString();
    }

    LocalDateTime getNextNoon(){
        LocalTime time = LocalTime.now();
        if(time.isBefore(LocalTime.NOON)){
            return LocalDateTime.now().withMinute(0).withHour(12).withSecond(0).withNano(0);
        }
        else {
            return LocalDateTime.now().plusDays(1).withMinute(0).withHour(12).withSecond(0).withNano(0);
        }
    }

    String weatherDescForNoon(int dayOffset, Weather weather){

        StringBuilder builder = new StringBuilder();

        LocalDateTime nextNoon = getNextNoon();
        nextNoon = nextNoon.plusDays(dayOffset);
        for(List list: weather.getList()){
            if(LocalDateTime.parse(list.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-M-d H:m:s")).equals(nextNoon)){
                builder.append(nextNoon.format(DateTimeFormatter.ofPattern("MM-dd ")));
                builder.append(round(list.getMain().getTemp()));
                builder.append("^C ");
                builder.append(list.getMain().getHumidity());
                builder.append("% ");
                builder.append(round(list.getWind().getSpeed()));
                builder.append("m/s ");
                builder.append(removeAccents(list.getWeather().get(0).getDescription()));
            }
        }
        return builder.toString();
    }

    @Override
    public void success(Weather weather, Response response) {
        nextApiCallTime = LocalDateTime.now().plusHours(1);
        updateWeather(weather);
    }

    @Override
    public void failure(RetrofitError error) {
        nextApiCallTime = LocalDateTime.now().plusMinutes(2);
        text = error.getLocalizedMessage();
    }

    @Override
    public void setPosition(int position)
    {
        this.position = position;
    }

    String removeAccents(String string){
        string = string.replace("ó","o");
        string = string.replace("Ó","O");
        string = string.replace("ł","l");
        string = string.replace("Ł","L");
        string = string.replace("ń","n");
        string = string.replace("Ń","N");
        string = string.replace("ż","z");
        string = string.replace("Ż","Z");
        string = string.replace("ź","z");
        string = string.replace("Ź","Z");
        string = string.replace("Ć","c");
        string = string.replace("ć","c");
        string = string.replace("ę","e");
        string = string.replace("Ę","E");
        string = string.replace("Ś","S");
        string = string.replace("ś","s");
        return string;
    }

}
