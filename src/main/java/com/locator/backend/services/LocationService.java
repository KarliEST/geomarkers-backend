package com.locator.backend.services;

import com.locator.backend.models.Location;
import com.locator.backend.repository.LocationRepository;
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
		if (!checkGeoJson(request)) {
			return "GEOJSON NOT VALID";
		}
		Location location = convertJsonToLocation(request);
		locationRepository.save(location);
		LocationResponse response = convertLocationToLocationResponse(location);
		return jsonResponse(response.toString());
	}

	public String getLocationById(int id) {
		if (checkId(id)) {
			return "ID NOT VALID";
		}
		LocationResponse locationResponse = convertLocationToLocationResponse(locationRepository.getReferenceById(id));
		return jsonResponse(locationResponse.toString());
	}

	public String updateLocationById(int id, String request) {
		if (checkId(id)) {
			return "ID NOT VALID";
		}
		if (checkGeoJson(request)) {
			return "GEOJSON NOT VALID";
		}
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
		if (checkId(id)) {
			return "ID NOT VALID";
		}
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
		Result jsonParams = getJsonParams(request);

		Location location = new Location();
		location.setLatitude(jsonParams.latitude());
		location.setLongitude(jsonParams.longitude());
		location.setDescription(jsonParams.description());
		return location;
	}

	private Result getJsonParams(String request) {
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

		String type = features
				.getJSONObject("geometry")
				.getString("type");

		double latitude = coordinates.getDouble(0);
		double longitude = coordinates.getDouble(1);
		return new Result(type, coordinates, description, latitude, longitude);
	}

	private record Result(String type, JSONArray coordinates, String description, double latitude, double longitude) {
	}

	private boolean checkId(int id) {
		List<Location> locations = locationRepository.findAll();
		return locations
				.stream()
				.noneMatch(location -> location.getId() == id);
	}

	private boolean checkGeoJson(String geoJson) {
		Result jsonParams = getJsonParams(geoJson);
		if (!jsonParams.type.equalsIgnoreCase("POINT")) {
			return false;
		}
		if (jsonParams.description == null ||
				!jsonParams.description.matches(".*\\w.*")) {
			return false;
		}
		if (jsonParams.coordinates == null) {
			return false;
		}
		if (jsonParams.latitude == 0) {
			return false;
		}
		if (jsonParams.longitude == 0) {
			return false;
		}
		return true;
	}

}
