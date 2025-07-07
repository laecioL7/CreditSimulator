package com.br.l7.credit_simulator_app.unit

import com.br.l7.credit_simulator_app.model.LoanDetails
import com.br.l7.credit_simulator_app.repository.AgeRangeInterestRateRepository
import com.br.l7.credit_simulator_app.service.SimulationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal
import java.time.LocalDate

@Tag("unit")
class SimulationServiceTest {

    private val ageRangeInterestRateRepository: AgeRangeInterestRateRepository = Mockito.mock(AgeRangeInterestRateRepository::class.java)
    private val simulationService = SimulationService(ageRangeInterestRateRepository)

    @Test
    fun `should calculate correct monthly payment`() {
        val loanValue = BigDecimal("10000")
        val annualInterestRate = BigDecimal("5.0")
        val months = 12

        val monthlyPayment = simulationService.calculateMonthlyPayment(loanValue, annualInterestRate, months)

        assertEquals(BigDecimal("856.07"), monthlyPayment)
    }

    @Test
    fun `should calculate total loan amount`() {
        val monthlyInstallment = BigDecimal("856.07")
        val numberOfInstallments = 12

        val totalAmount = simulationService.calculateTotalLoanAmount(monthlyInstallment, numberOfInstallments)

        assertEquals(BigDecimal("10272.84"), totalAmount)
    }

    @Test
    fun `should calculate total interest paid`() {
        val totalAmountToBePaid = BigDecimal("10272.84")
        val loanPrincipal = BigDecimal("10000")

        val totalInterestPay = simulationService.calculateTotalInterestPaid(totalAmountToBePaid, loanPrincipal)

        assertEquals(BigDecimal("272.84"), totalInterestPay)
    }

    @Test
    fun `should calculate correct age`() {
        val birthDate = LocalDate.of(1990, 1, 1)
        val age = simulationService.calculateAge(birthDate)

        assertEquals(LocalDate.now().year - 1990, age)
    }

    @Test
    fun `should simulate loan and return correct payment response`() {
        val loanDetails = LoanDetails(
            clientBirthDate = LocalDate.of(1990, 1, 1),
            loanAmount = 10000.0,
            paymentTermInMonths = 12
        )

        val calculatedAge = simulationService.calculateAge(loanDetails.clientBirthDate)
        Mockito.`when`(ageRangeInterestRateRepository.findInterestRateByAge(calculatedAge))
            .thenReturn(BigDecimal("5.0"))

        val paymentResponse = simulationService.simulateLoan(loanDetails)

        assertEquals(BigDecimal("10272.84"), paymentResponse.totalAmountToBePaid)
        assertEquals(BigDecimal("856.07"), paymentResponse.monthlyInstallmentAmount)
        assertEquals(BigDecimal("272.84"), paymentResponse.totalInterestPaid)
    }
}