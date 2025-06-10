package com.emperia.controller;


import com.emperia.dto.*;
import com.emperia.security.CurrentUser;
import com.emperia.security.UserPrincipal;
import com.emperia.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/bills")
@Tag(name = "Bill Controller", description = "API endpoints for managing bills")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping(value = "/")
    @Operation(summary = "Create a bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @RequestBody @Valid BillDto billDto) {

        billService.save(billDto);

        return ResponseEntity.ok("Bill was created with great success!");
    }

    @PutMapping(value = "/")
    @Operation(summary = "Edit a bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> edit(
            @RequestBody @Valid BillDto billDto) {

        billService.edit(billDto);

        return ResponseEntity.ok("Bill was updated with great success!");
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Bill ID") String id,
            @CurrentUser UserPrincipal currentUser) {

        Long idNumeric;
        try {
            idNumeric = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Please provide a numeric id!");
        }

        BillDto billDto;
        try {
            billDto = billService.delete(idNumeric);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The bill with the provided id does not exist!");
        }

        return ResponseEntity.ok(billDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a bill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> get(
            @PathVariable @Parameter(description = "Bill ID") String id) {

        Long idNumeric;
        try {
            idNumeric = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Please provide a numeric id!");
        }

        BillDto billDto;
        try {
            billDto = billService.get(idNumeric);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The bill with the provided id does not exist!");
        }

        return ResponseEntity.ok(billDto);
    }

    @GetMapping(value = "/search/{houseId}")
    @Operation(summary = "Search bills by house ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bills found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> searchByHouseId(
            @PathVariable @Parameter(description = "House ID") Long houseId) {

        List<BillDto> billsDtos;
        try {
            billsDtos = billService.search(houseId);
        }
        catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Could not fetch the bills with the given homeId");
        }

        return ResponseEntity.ok(billsDtos);
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all bills")
    @ApiResponse(responseCode = "200", description = "Bills retrieved successfully")
    public ResponseEntity<List<BillDto>> getAll() {
        return ResponseEntity.ok(billService.getAll());
    }

    @GetMapping(value = "/mine")
    @Operation(summary = "Get user's bills")
    @ApiResponse(responseCode = "200", description = "User's bills retrieved successfully")
    public ResponseEntity<List<BillDto>> getMyBills(
            @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(billService.getAllForUser(currentUser.getId()));
    }

    @GetMapping("/between")
    @Operation(summary = "Get bills generated between a given interval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bills retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<BillDto>> getBillsGeneratedBetween(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        try {
            List<BillDto> bills = billService.getBillsGeneratedBetween(startDate, endDate);
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping(value = "/generate/all")
    @Operation(summary = "Generate all bills")
    @ApiResponse(responseCode = "200", description = "All bills generated successfully")
    public ResponseEntity<String> generateAllBills() {
        billService.generateAllBills();
        return ResponseEntity.ok("All Bills generated successfully");
    }

    @PostMapping("/generate/{homeId}")
    @Operation(summary = "Generate bills for a home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bills generated successfully"),
            @ApiResponse(responseCode = "404", description = "Home not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> generateBillsForHome(
            @PathVariable @Parameter(description = "Home ID") Long homeId) {

        try {
            billService.generateBillsForHome(homeId);
            return ResponseEntity.ok("Bills generated successfully for the home with ID: " + homeId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Home not found with ID: " + homeId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while generating bills for the home with ID: " + homeId);
        }
    }
}