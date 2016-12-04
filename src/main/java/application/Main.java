package application;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import driver.FutabaDisplay;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import weather.Weather;
import weather.WeatherInterface;
import widget.ClockWidget;
import widget.DateWidget;
import widget.ScrollingWidget;


import java.io.IOException;

/**
 * Created by trojan on 24.11.16.
 */
public class Main {



    public static void main(String[] args) {
        ClockWidget clockWidget = new ClockWidget();
        clockWidget.setPosition(0);
        DateWidget dateWidget = new DateWidget();
        dateWidget.setPosition(9);
        ScrollingWidget scrollingWidget = new ScrollingWidget();
        scrollingWidget.setPosition(0x14);
        scrollingWidget.setViewportLength(19);

        FutabaDisplay display = new FutabaDisplay();
        display.addWidget(clockWidget);
        display.addWidget(dateWidget);
        display.addWidget(scrollingWidget);
        display.reset();
        display.reset();
        display.reset();
        display.reset();

        display.clear();

        display.setDim(FutabaDisplay.DimLevel._100);
        display.setCursorOff();


        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            display.redrawWidgets();
        }
    }

}
