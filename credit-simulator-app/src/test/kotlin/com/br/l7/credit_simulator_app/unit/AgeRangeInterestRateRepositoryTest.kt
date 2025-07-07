package com.br.l7.credit_simulator_app.unit

import com.br.l7.credit_simulator_app.repository.AgeRangeInterestRateRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal

@Tag("unit")
class AgeRangeInterestRateRepositoryTest {

    private val repository: AgeRangeInterestRateRepository = Mockito.mock(AgeRangeInterestRateRepository::class.java)

    @Test
    fun `should return correct interest rate for given age`() {
        val clientAge = 30
        val expectedRate = BigDecimal("5.5")

        Mockito.`when`(repository.findInterestRateByAge(clientAge)).thenReturn(expectedRate)

        val actualRate = repository.findInterestRateByAge(clientAge)

        assertEquals(expectedRate, actualRate)
    }
}