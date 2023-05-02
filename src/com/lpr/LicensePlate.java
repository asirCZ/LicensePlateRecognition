package com.lpr;

import com.openalpr.jni.Alpr;
import com.openalpr.jni.AlprResults;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LicensePlate {
    /**
     *
     * @param licensePlate contains name of the image with the license plate
     * @return returns the license plate number as a string
     * @throws Exception
     */
    public String findPlate(String licensePlate) throws Exception {


        String country = "eu", configfile = "openalpr.conf", runtimeDataDir = "runtime_data";
        Alpr alpr = new Alpr(country, configfile, runtimeDataDir);

        alpr.setTopN(1);

        // Read an image into a byte array and send it to OpenALPR
        Path path = Paths.get(licensePlate);
        byte[] imagedata = Files.readAllBytes(path);

        AlprResults results = alpr.recognize(imagedata);

        System.out.println("OpenALPR Version: " + alpr.getVersion());
        System.out.println("Image Size: " + results.getImgWidth() + "x" + results.getImgHeight());
        System.out.println("Processing Time: " + results.getTotalProcessingTimeMs() + " ms");
        System.out.println("Found " + results.getPlates().size() + " results");
        System.out.println("Found: " + results.getPlates().get(0).getBestPlate().getCharacters());

        // Releases memory
        alpr.unload();
        return results.getPlates().get(0).getBestPlate().getCharacters();
    }
}
