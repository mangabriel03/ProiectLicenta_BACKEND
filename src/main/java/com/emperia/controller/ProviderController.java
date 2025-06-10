package com.emperia.controller;
import com.emperia.dto.ProviderDto;
import com.emperia.service.ProviderService;
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
@RequestMapping(value = "/api/provider")
@Tag(name = "Provider Controller", description = "API endpoints for managing providers")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @PostMapping(value = "/")
    @Operation(summary = "Create a provider")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @RequestBody @Valid ProviderDto providerDto) {
        providerService.save(providerDto);
        return ResponseEntity.ok("Successfully created");
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all providers")
    @ApiResponse(responseCode = "200", description = "Providers retrieved successfully")
    public ResponseEntity<List<ProviderDto>> getAll() {
        List<ProviderDto> providerDtoList = providerService.findAll();
        return ResponseEntity.ok(providerDtoList);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a provider")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(
            @PathVariable @Parameter(description = "Provider ID") Long id) {
        providerService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @PutMapping(value = "/")
    @Operation(summary = "Update a provider")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> update(
            @RequestBody @Valid ProviderDto providerDto) {
        providerService.update(providerDto);
        return ResponseEntity.ok("Successfully updated");
    }
}
