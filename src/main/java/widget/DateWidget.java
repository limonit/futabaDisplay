package widget;

import driver.FutabaDisplay;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import weather.Weather;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by trojan on 27.11.16.
 */
public class DateWidget implements Widget {

    int position;

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void redraw(FutabaDisplay display) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate time = LocalDate.now();
        String formattedTime = time.format(formatter);
        display.setDisplayPosition(position);
        display.printString(" " + formattedTime);

    }


}

