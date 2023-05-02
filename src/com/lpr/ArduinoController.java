package com.lpr;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;

public class ArduinoController {
    final SerialPort sp;
    OutputStream outStream;

    /**
     * Sends a signal through the USB serial port.
     */
    public void sendSignal() {
        try {
            outStream.write(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param sp contains object of a Serial Port.
     */
    public ArduinoController(SerialPort sp) {
        this.sp = sp;
        sp.openPort();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sp.setBaudRate(9600);
        outStream = sp.getOutputStream();
    }
}
