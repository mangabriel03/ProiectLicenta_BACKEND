package com.emperia.controller;
import com.emperia.dto.ProviderServicesDto;
import com.emperia.dto.ServiceTypeDto;
import com.emperia.service.ProviderServiceService;
import com.emperia.service.ServiceTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping(value = "/api/providerService")
@Tag(name = "Provider Service Controller", description = "API endpoints for managing provider services")
public class ProviderServiceController {

    @Autowired
    private ProviderServiceService providerServiceService;

    @PostMapping(value = "/")
    @Operation(summary = "Create a provider service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider service created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @RequestBody @Valid ProviderServicesDto providerServicesDto) {
        providerServiceService.save(providerServicesDto);
        return ResponseEntity.ok("Provider service was created with great success!");
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all provider services")
    @ApiResponse(responseCode = "200", description = "Provider services retrieved successfully")
    public ResponseEntity<List<ProviderServicesDto>> getAll() {
        List<ProviderServicesDto> providerServicesDtoList = providerServiceService.findAll();
        return ResponseEntity.ok(providerServicesDtoList);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a provider service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider service deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(
            @PathVariable @Parameter(description = "Provider service ID") Long id) {
        providerServiceService.delete(id);
        return ResponseEntity.ok("Successfully deleted provider service");
    }

    @PutMapping(value = "/")
    @Operation(summary = "Update a provider service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider service updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> update(
            @RequestBody @Valid ProviderServicesDto providerServicesDto) {
        providerServiceService.update(providerServicesDto);
        return ResponseEntity.ok("Successfully updated provider service");
    }
}
