package com.locator.backend.services;

import com.locator.backend.models.Location;
import com.locator.backend.repository.LocationRepository;
import com.locator.backend.requests.LocationRequest;
import com.locator.backend.responses.LocationResponse;
import jakarta.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LocationService {
	@Resource
	private LocationRepository locationRepository;


	public String addLocation(String request) {
		Location location = convertJsonToLocation(request);
		locationRepository.save(location);
		LocationResponse response = convertLocationToLocationResponse(location);
		return jsonResponse(response.toString());
	}

	public String getLocationById(int id) {
		LocationResponse locationResponse = convertLocationToLocationResponse(locationRepository.getReferenceById(id));
		return jsonResponse(locationResponse.toString());
	}

	public String updateLocationById(int id, String request) {
		Location updatedLocation = convertJsonToLocation(request);
		locationRepository.findById(id)
				.map(location -> {
					location.setLatitude(updatedLocation.getLatitude());
					location.setLongitude(updatedLocation.getLongitude());
					location.setDescription(updatedLocation.getDescription());
					return locationRepository.save(location);
				});
		LocationResponse response = convertLocationToLocationResponse(locationRepository.getReferenceById(id));
		return jsonResponse(response.toString());
	}

	public String deleteLocationById(int id) {
		Location location = locationRepository.getReferenceById(id);
		locationRepository.delete(location);
		return "DELETED SUCCESSFULLY";
	}

	public String getAllLocations() {
		List<Location> locations = locationRepository.findAll();
		List<LocationResponse> locationResponses = convertLocationsToLocationResponses(locations);
		StringBuilder json = new StringBuilder();
		String separator = "";
		for (LocationResponse response : locationResponses) {
			json.append(separator).append(response.toString());
			separator = ",\n";
		}
		return jsonResponse(json.toString());
	}

	private String jsonResponse(String response) {
		return String.format("""
				{
				    "type": "FeatureCollection",
				    "features": [
				        %s
				    ]
				}
				""", response);
	}

	private LocationResponse convertLocationToLocationResponse(Location location) {
		LocationResponse locationResponse = new LocationResponse();
		locationResponse.setId(location.getId());
		locationResponse.setLatitude(location.getLatitude());
		locationResponse.setLongitude(location.getLongitude());
		locationResponse.setDescription(location.getDescription());
		return locationResponse;
	}

	private List<LocationResponse> convertLocationsToLocationResponses(List<Location> locations) {
		List<LocationResponse> locationResponses = new LinkedList<>();
		for (Location location : locations) {
			locationResponses.add(convertLocationToLocationResponse(location));
		}
		return locationResponses;
	}

	private Location convertJsonToLocation(String request) {
		JSONObject obj = new JSONObject(request);

		JSONObject features = obj
				.getJSONArray("features")
				.getJSONObject(0);

		String description = features
				.getJSONObject("properties")
				.getString("description");

		JSONArray coordinates = features
				.getJSONObject("geometry")
				.getJSONArray("coordinates");

		double latitude = coordinates.getDouble(0);
		double longitude = coordinates.getDouble(1);

		Location location = new Location();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setDescription(description);
		return location;
	}

	private void validateJson(JSONObject object) {
		if (object.getJSONArray("features").length() != 1) {
			return;
		}
	}
}
