package com.emperia.controller;
import com.emperia.dto.ServiceTypeDto;
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
@RequestMapping(value = "/api/serviceType")
@Tag(name = "Service Type Controller", description = "API endpoints for managing service types")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @PostMapping(value = "/")
    @Operation(summary = "Create a service type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service type created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @RequestBody @Valid ServiceTypeDto serviceTypeDto) {
        serviceTypeService.save(serviceTypeDto);
        return ResponseEntity.ok("ServiceType was created with great success!");
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all service types")
    @ApiResponse(responseCode = "200", description = "Service types retrieved successfully")
    public ResponseEntity<List<ServiceTypeDto>> getAll() {
        List<ServiceTypeDto> serviceTypeDtoList = serviceTypeService.findAll();
        return ResponseEntity.ok(serviceTypeDtoList);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a service type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service type deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(
            @PathVariable @Parameter(description = "Service type ID") Long id) {
        serviceTypeService.delete(id);
        return ResponseEntity.ok("Successfully deleted service type");
    }

    @PutMapping(value = "/")
    @Operation(summary = "Update a service type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service type updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> update(
            @RequestBody @Valid ServiceTypeDto serviceTypeDto) {
        serviceTypeService.update(serviceTypeDto);
        return ResponseEntity.ok("Successfully updated service type");
    }
}
