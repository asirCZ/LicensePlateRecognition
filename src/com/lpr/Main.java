package com.lpr;

import com.fazecast.jSerialComm.*;
import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String plate;
        ArrayList<String> allowedPlates = new ArrayList<>();

        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();
        ArduinoController ac = new ArduinoController(allAvailableComPorts[1]);
        LicensePlate lp = new LicensePlate();
        Webcam webcam = com.github.sarxos.webcam.Webcam.getDefault();
        webcam.open();
        System.out.println("Webcam: " + webcam.getName());

        try (BufferedReader br = new BufferedReader(new FileReader("license_plates.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                allowedPlates.addAll(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                ImageIO.write(webcam.getImage(), "JPG", new File("LPImage.jpg"));
                plate = lp.findPlate("LPImage.jpg");
                plate = plate.replaceAll("\\s", "");
                if (allowedPlates.contains(plate)) {
                    ac.sendSignal();
                }
            } catch (Exception e) {
            }
        }
    }
}
