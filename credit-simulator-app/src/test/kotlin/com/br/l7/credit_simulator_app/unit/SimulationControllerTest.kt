package com.br.l7.credit_simulator_app.unit

import com.br.l7.credit_simulator_app.controller.SimulationController
import com.br.l7.credit_simulator_app.dto.LoanDetailsDto
import com.br.l7.credit_simulator_app.model.LoanDetails
import com.br.l7.credit_simulator_app.model.PaymentResponse
import com.br.l7.credit_simulator_app.service.SimulationService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.math.BigDecimal
import java.time.LocalDate

@Tag("unit")
class SimulationControllerTest {

    private val simulationService: SimulationService = Mockito.mock(SimulationService::class.java)
    private val controller = SimulationController(simulationService)

    @Test
    fun `should return payment response for valid loan details`() {
        val loanDetailsDto = LoanDetailsDto(
            clientBirthDate = LocalDate.of(1990, 1, 1),
            loanAmount = 10000.0,
            paymentTermInMonths = 12
        )

        val expectedResponse = PaymentResponse(
            totalAmountToBePaid = BigDecimal("10272.84"),
            monthlyInstallmentAmount = BigDecimal("856.07"),
            totalInterestPaid = BigDecimal("272.84")
        )

        whenever(simulationService.simulateLoan(any())).thenReturn(expectedResponse)

        val actualResponse = controller.doSimulation(loanDetailsDto)

        assertEquals(expectedResponse, actualResponse)
    }
}