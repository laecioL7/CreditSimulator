package com.br.l7.credit_simulator_app.integration

import com.br.l7.credit_simulator_app.model.AgeRangeInterestRate
import com.br.l7.credit_simulator_app.repository.AgeRangeInterestRateRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.math.BigDecimal

@Tag("integration")
@DataJpaTest
class AgeRangeInterestRateRepositoryIT {

    @Autowired
    private lateinit var repository: AgeRangeInterestRateRepository

    @Test
    fun `should return correct interest rate for given age`() {
        val ageRangeInterestRate = AgeRangeInterestRate(
            id = 1L,
            minimumAge = 151,
            maximumAge = 160,
            rateInterestYear = BigDecimal("5.5")
        )
        repository.save(ageRangeInterestRate)

        val clientAge = 152
        val actualRate = repository.findInterestRateByAge(clientAge)

        assertEquals(BigDecimal("5.50"), actualRate)
    }
}