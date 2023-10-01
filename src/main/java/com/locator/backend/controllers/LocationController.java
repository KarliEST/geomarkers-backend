package com.locator.backend.controllers;

import com.locator.backend.services.LocationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LocationController {
	@Resource
	private LocationService locationService;

	@PostMapping("/add")
	public String addLocation(@RequestBody String request) {
		return locationService.addLocation(request);
	}

	@GetMapping("/id")
	public String getLocationById(@RequestParam int id) {
		return locationService.getLocationById(id);
	}

	@PutMapping("/id")
	public String updateLocationById(@RequestParam int id, @RequestBody String request) {
		return locationService.updateLocationById(id, request);
	}

	@DeleteMapping("/id")
	public String deleteLocationById(@RequestParam int id) {
		return locationService.deleteLocationById(id);
	}

	@GetMapping("/get")
	public String getAllLocations() {
		return locationService.getAllLocations();
	}
}