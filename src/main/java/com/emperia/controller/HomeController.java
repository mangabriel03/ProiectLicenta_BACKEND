package com.emperia.controller;

import com.emperia.dto.HomeDto;
import com.emperia.security.CurrentUser;
import com.emperia.security.UserPrincipal;
import com.emperia.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/home")
@Tag(name = "Home Controller", description = "API endpoints for managing homes")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping(value = "/search/{name}")
    @Operation(summary = "Search homes by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Homes found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> search(
            @PathVariable @Parameter(description = "Home name") String name) {

        List<HomeDto> homeDtos;
        try {
            homeDtos = homeService.search(name);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not fetch the homes with the given name");
        }

        return ResponseEntity.ok(homeDtos);
    }

    @GetMapping(value = "/mine")
    @Operation(summary = "Get user's homes")
    @ApiResponse(responseCode = "200", description = "User's homes retrieved successfully")
    public ResponseEntity<List<HomeDto>> getMyHouses(
            @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(homeService.getAllForUser(currentUser.getId()));
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all homes")
    @ApiResponse(responseCode = "200", description = "Homes retrieved successfully")
    public ResponseEntity<List<HomeDto>> getAll() {
        return ResponseEntity.ok(homeService.getAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a home by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Home retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> get(
            @PathVariable @Parameter(description = "Home ID") String id) {

        Long idNumeric;
        try {
            idNumeric = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Please provide a numeric id!");
        }

        HomeDto homeDto;
        try {
            homeDto = homeService.get(idNumeric);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The home with the provided id does not exist!");
        }

        return ResponseEntity.ok(homeDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Home deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Home ID") String id,
            @CurrentUser UserPrincipal currentUser) {

        Long idNumeric;
        try {
            idNumeric = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Please provide a numeric id!");
        }

        HomeDto homeDto;
        try {
            homeDto = homeService.delete(idNumeric);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The home with the provided id does not exist!");
        }

        return ResponseEntity.ok(homeDto);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create a home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Home created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @RequestBody @Valid HomeDto homeDto,
            @CurrentUser UserPrincipal currentUser) {

        try {
            homeService.save(homeDto, currentUser.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Home was not created...");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Home was created with great success!");
    }

    @PutMapping(value = "/")
    @Operation(summary = "Update a home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Home updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> edit(
            @RequestBody @Valid HomeDto homeDto,
            @CurrentUser UserPrincipal currentUser) {

        try {
            homeService.edit(homeDto, currentUser.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Home was not created...");
        }

        return ResponseEntity.ok("Home was updated with great success!");
    }
}
