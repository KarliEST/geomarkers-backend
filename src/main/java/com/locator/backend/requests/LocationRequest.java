package com.locator.backend.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationRequest {
	private double longitude;
	private double latitude;
	private double[] coordinates;
	private String description;
}
