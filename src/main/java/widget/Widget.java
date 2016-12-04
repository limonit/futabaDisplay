package widget;

import driver.FutabaDisplay;

/**
 * Created by trojan on 27.11.16.
 */
public interface Widget {
    void setPosition(int position);

    void redraw(FutabaDisplay display);
}
