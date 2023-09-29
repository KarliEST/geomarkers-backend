package com.locator.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationResponse {
    private int id;
    private double longitude;
    private double latitude;
    private String description;


    public String toString() {
        String geoJson = """
                    {
                      "type": "Feature",
                      "properties": {
                        "id": %d,
                        "description": "%s"
                      },
                      "geometry": {
                        "coordinates": [
                          %f,
                          %f
                        ],
                        "type": "Point"
                      }
                    }""";
        return String.format(Locale.US,geoJson, id, description, longitude, latitude);
    }
}
