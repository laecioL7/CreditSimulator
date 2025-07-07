package com.br.l7.credit_simulator_app.performance

import com.br.l7.credit_simulator_app.model.LoanDetails
import com.br.l7.credit_simulator_app.service.SimulationService
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import kotlin.system.measureTimeMillis
import kotlin.test.Test

@SpringBootTest
class SimulationServicePerformanceTest {

    @Autowired
    private lateinit var simulationService: SimulationService

    @Test
    fun `calculation should run in less than 500ms`() {
        val loanDetails = LoanDetails(
            clientBirthDate = LocalDate.of(1990, 1, 1),
            loanAmount = 10000.0,
            paymentTermInMonths = 12
        )

        val tempo = measureTimeMillis {
            simulationService.simulateLoan(loanDetails)
        }

        assertTrue(tempo < 500, "Tempo foi ${tempo}ms")
    }
}