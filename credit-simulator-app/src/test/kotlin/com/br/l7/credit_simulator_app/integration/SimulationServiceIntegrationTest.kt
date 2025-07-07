package com.br.l7.credit_simulator_app.integration

import com.br.l7.credit_simulator_app.model.AgeRangeInterestRate
import com.br.l7.credit_simulator_app.model.LoanDetails
import com.br.l7.credit_simulator_app.model.PaymentResponse
import com.br.l7.credit_simulator_app.repository.AgeRangeInterestRateRepository
import com.br.l7.credit_simulator_app.service.SimulationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate

@Tag("integration")
@SpringBootTest
class SimulationServiceIntegrationTest {

    @Autowired
    private lateinit var simulationService: SimulationService

    @Autowired
    private lateinit var repository: AgeRangeInterestRateRepository


    @Autowired
    private lateinit var ageRangeInterestRateRepository: AgeRangeInterestRateRepository

    @Test
    fun `should simulate loan and return correct payment response`() {
        val ageRangeInterestRate = AgeRangeInterestRate(
            id = 1L,
            minimumAge = 151,
            maximumAge = 160,
            rateInterestYear = BigDecimal("5.5")
        )
        repository.save(ageRangeInterestRate)

        val loanDetails = LoanDetails(
            clientBirthDate = LocalDate.of(1871, 1, 1),
            loanAmount = 10000.0,
            paymentTermInMonths = 12
        )

        val paymentResponse: PaymentResponse = simulationService.simulateLoan(loanDetails)

        assertEquals(BigDecimal("858.37"), paymentResponse.monthlyInstallmentAmount)
        assertEquals(BigDecimal("10300.44"), paymentResponse.totalAmountToBePaid)
        assertEquals(BigDecimal("300.44"), paymentResponse.totalInterestPaid)
    }
}