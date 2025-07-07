package com.br.l7.credit_simulator_app.integration

import com.br.l7.credit_simulator_app.dto.LoanDetailsDto
import com.br.l7.credit_simulator_app.model.AgeRangeInterestRate
import com.br.l7.credit_simulator_app.model.PaymentResponse
import com.br.l7.credit_simulator_app.repository.AgeRangeInterestRateRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDate

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimulationControllerIntegrationTest{

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var repository: AgeRangeInterestRateRepository

    @Test
    fun `should simulate loan and return correct payment response`() {
        val ageRangeInterestRate = AgeRangeInterestRate(
            id = 1L,
            minimumAge = 151,
            maximumAge = 160,
            rateInterestYear = BigDecimal("5.5")
        )
        repository.save(ageRangeInterestRate)

        val loanDetailsDto = LoanDetailsDto(
            clientBirthDate =  LocalDate.of(1871, 1, 1),
            loanAmount = 10000.0,
            paymentTermInMonths = 12
        )

        val response = restTemplate.postForEntity("/simulation", loanDetailsDto, PaymentResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(BigDecimal("10300.44"), response.body?.totalAmountToBePaid)
        assertEquals(BigDecimal("858.37"), response.body?.monthlyInstallmentAmount)
        assertEquals(BigDecimal("300.44"), response.body?.totalInterestPaid)
    }
}