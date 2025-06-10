package com.emperia.controller;
import com.emperia.dto.BillStatusDto;
import com.emperia.dto.BillStatusDto;
import com.emperia.service.BillStatusService;
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
@RequestMapping(value = "/api/billStatus")
@Tag(name = "Bill Status Controller", description = "API endpoints for managing bill statuses")
public class BillStatusController {

    @Autowired
    private BillStatusService billStatusService;

    @PostMapping(value = "/")
    @Operation(summary = "Create a bill status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill status created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @RequestBody @Valid BillStatusDto billStatusDto) {

        billStatusService.save(billStatusDto);

        return ResponseEntity.ok("Created with great success!");
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all bill statuses")
    @ApiResponse(responseCode = "200", description = "Bill statuses retrieved successfully")
    public ResponseEntity<List<BillStatusDto>> getAll() {
        List<BillStatusDto> billStatusDtoList = billStatusService.findAll();

        return ResponseEntity.ok(billStatusDtoList);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a bill status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill status deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(
            @PathVariable @Parameter(description = "Bill status ID") Long id) {
        billStatusService.delete(id);

        return ResponseEntity.ok("Successfully deleted");
    }

    @PutMapping(value = "/")
    @Operation(summary = "Update a bill status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill status updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> update(
            @RequestBody @Valid BillStatusDto billStatusDto) {
        billStatusService.update(billStatusDto);

        return ResponseEntity.ok("Successfully updated");
    }
}
