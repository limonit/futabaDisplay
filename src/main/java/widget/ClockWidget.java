package widget;

import driver.FutabaDisplay;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by trojan on 27.11.16.
 */
public class ClockWidget implements Widget {

    int position;

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void redraw(FutabaDisplay display) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalTime time = LocalTime.now();
        String formattedTime = time.format(formatter);
        display.setDisplayPosition(position);
        if (formattedTime.length() == 8)
            display.printString(formattedTime);
        else display.printString(" " + formattedTime);



    }
}
