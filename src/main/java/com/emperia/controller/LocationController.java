package com.emperia.controller;

import com.emperia.dto.HomeLocationDto;
import com.emperia.dto.LocationDto;
import com.emperia.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/location")
@Tag(name = "Location Controller", description = "API endpoints for managing locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping(value = "/")
    @Operation(summary = "Get all locations")
    @ApiResponse(responseCode = "200", description = "Locations retrieved successfully")
    public ResponseEntity<List<LocationDto>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @GetMapping(value = "/homeLocations")
    @Operation(summary = "Get all locations with associated homes")
    @ApiResponse(responseCode = "200", description = "Locations with homes retrieved successfully")
    public ResponseEntity<List<HomeLocationDto>> getAllWithHome() {
        return ResponseEntity.ok(locationService.getAllHomeLocations());
    }
}