package com.emperia.controller;
import com.emperia.dto.BillDto;
import com.emperia.dto.BillIndexDto;
import com.emperia.security.CurrentUser;
import com.emperia.security.UserPrincipal;
import com.emperia.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/payment")
@Tag(name = "Payment Controller", description = "API endpoints for managing payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/index")
    @Operation(summary = "Submit a payment index")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment index submitted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BillDto> submitIndex(
            @RequestBody @Valid BillIndexDto billIndexDto,
            @CurrentUser UserPrincipal currentUser) {

        BillDto newBill = paymentService.submitIndex(billIndexDto);

        return ResponseEntity.ok(newBill);
    }
}