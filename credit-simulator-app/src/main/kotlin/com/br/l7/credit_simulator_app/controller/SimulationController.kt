package com.br.l7.credit_simulator_app.controller

import com.br.l7.credit_simulator_app.dto.LoanDetailsDto
import com.br.l7.credit_simulator_app.mapper.LoanDetailsMapper
import com.br.l7.credit_simulator_app.model.PaymentResponse
import com.br.l7.credit_simulator_app.service.SimulationService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@RestController
class SimulationController(val simulationService: SimulationService) {

    private val logger: Logger = LoggerFactory.getLogger(SimulationController::class.java)

    @Operation(
        summary = "Simulate a loan",
        description = "This endpoint simulates a loan based on the provided loan details and returns the payment response.",
    )
    @PostMapping("/simulation")
    fun doSimulation(@Valid @RequestBody loanDetailsDto: LoanDetailsDto) : PaymentResponse{
        logger.info("Iniciando simulação de empréstimos...")
        val loanDetails = LoanDetailsMapper.toModel(loanDetailsDto)
        return simulationService.simulateLoan(loanDetails)
    }

}