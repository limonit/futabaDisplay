package driver;

import com.pi4j.io.serial.*;
import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import widget.Widget;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trojan on 24.11.16.
 */
public class FutabaDisplay {

    private Serial serial;
    private List<Widget> widgetArray;

    static byte i = 0;

    public enum DimLevel {
        _0, _20, _40, _60, _80, _100;
    }
    
    public FutabaDisplay() {
        widgetArray = new ArrayList<>();
        serial = SerialFactory.createInstance();
        try {
            serial.open(Serial.DEFAULT_COM_PORT, Baud._9600, DataBits._8, Parity.NONE, StopBits._1, FlowControl.NONE);
            serial.write(LocalDateTime.now().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDateTime() throws IOException {
        this.setDisplayPosition(0);
        serial.write(LocalDateTime.now().toString());

    }

    public void reset() {
        try {
            serial.write((byte) (0x1F));
            for(int i = 0; i < 10; i++)
                serial.write('\r');
                serial.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            for(int i = 0; i< 20; i++)
            serial.write((byte) (' '));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDim(DimLevel dimLevel) {
        try {
            switch (dimLevel) {
                case _0:
                    serial.write(new byte[]{4, 0x0});
                    break;
                case _20:
                    serial.write(new byte[]{4, 0x20});
                    break;
                case _40:
                    serial.write(new byte[]{4, 0x40});
                    break;
                case _60:
                    serial.write(new byte[]{4, 0x60});
                    break;
                case _80:
                    serial.write(new byte[]{4, (byte) 0x80});
                    break;
                case _100:
                    serial.write(new byte[]{4, (byte) 0xFF});
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDisplayPosition(int position) {
        if (position > 0x27) throw new IllegalArgumentException("Illegal dim level");
        try {
            serial.write(new byte[]{0x10, (byte) position});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCursorOff() {
        try {
            serial.write((byte) 0x14);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printString(String s){
        try {
            serial.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addWidget(Widget widget){
        widgetArray.add(widget);
    }

    public void redrawWidgets() {
        for(Widget widget: widgetArray){
            widget.redraw(this);
        }
    }
}