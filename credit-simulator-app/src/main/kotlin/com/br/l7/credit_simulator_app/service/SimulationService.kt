package com.br.l7.credit_simulator_app.service

import com.br.l7.credit_simulator_app.controller.SimulationController
import com.br.l7.credit_simulator_app.model.LoanDetails
import com.br.l7.credit_simulator_app.model.PaymentResponse
import com.br.l7.credit_simulator_app.repository.AgeRangeInterestRateRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.time.LocalDate
import java.time.Period

@Service
class SimulationService(
    private val ageRangeInterestRateRepository: AgeRangeInterestRateRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(SimulationService::class.java)

    @Cacheable("simulateLoan")
    fun simulateLoan(loanDetails: LoanDetails): PaymentResponse {
        logger.debug("Buscando taxa de acordo com a idade")
        val annualInterestRateByAge =
            ageRangeInterestRateRepository.findInterestRateByAge(calculateAge(loanDetails.clientBirthDate))

        logger.debug("Calculando valor mensal, total e juros")
        val monthlyPayment = calculateMonthlyPayment(
            loanValue = BigDecimal.valueOf(loanDetails.loanAmount),
            annualInterestRate = annualInterestRateByAge,
            months = loanDetails.paymentTermInMonths
        )

        val totalAmount = calculateTotalLoanAmount(
            monthlyInstallment = monthlyPayment,
            numberOfInstallments = loanDetails.paymentTermInMonths
        )

        val totalInterestPay = calculateTotalInterestPaid(totalAmount, BigDecimal.valueOf(loanDetails.loanAmount))

        logger.info(
            "Valor Mensal = {} , Total = {}, Total de Juros = {}",
            monthlyPayment,
            totalAmount,
            totalInterestPay
        )

        return PaymentResponse(
            totalAmountToBePaid = totalAmount,
            monthlyInstallmentAmount = monthlyPayment,
            totalInterestPaid = totalInterestPay
        )
    }

    fun calculateAge(birthday: LocalDate): Int {
        val today = LocalDate.now()
        return Period.between(birthday, today).years
    }

    /**
     * Calcula o valor da parcela mensal de um empréstimo com base no valor do empréstimo,
     * taxa de juros anual e número de meses.
     *
     * A fórmula utilizada é derivada da fórmula padrão de anuidade:
     * M = P * r / (1 - (1 + r)^(-n))
     * Onde:
     * - M é o valor da parcela mensal
     * - P é o valor do empréstimo (principal)
     * - r é a taxa de juros mensal (taxa anual dividida por 12)
     * - n é o número de meses
     *
     * @param loanValue O valor total do empréstimo (principal) como um [BigDecimal].
     * @param annualInterestRate A taxa de juros anual como porcentagem (ex.: 5 para 5%).
     * @param months O número total de meses para o pagamento do empréstimo.
     * @return O valor calculado da parcela mensal como um [BigDecimal], arredondado para 2 casas decimais.
     */
    fun calculateMonthlyPayment(
        loanValue: BigDecimal, // PV: Valor do empréstimo
        annualInterestRate: BigDecimal,  // Taxa de juros anual (%)
        months: Int // n: Número de meses
    ): BigDecimal {
        val monthlyRate = annualInterestRate
            .divide(BigDecimal(100), 10, RoundingMode.HALF_UP)
            .divide(BigDecimal(12), 10, RoundingMode.HALF_UP) // r = annual rate / 12

        if (monthlyRate == BigDecimal.ZERO) {
            return loanValue.divide(BigDecimal(months), 2, RoundingMode.HALF_UP)
        }

        val onePlusRatePowerMinusN = (BigDecimal.ONE + monthlyRate)
            .pow(-months, MathContext.DECIMAL128)

        val denominator = BigDecimal.ONE - onePlusRatePowerMinusN
        val numerator = loanValue.multiply(monthlyRate)

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP)
    }

    fun calculateTotalLoanAmount(monthlyInstallment: BigDecimal, numberOfInstallments: Int): BigDecimal {
        return monthlyInstallment.multiply(BigDecimal(numberOfInstallments))
    }

    fun calculateTotalInterestPaid(totalAmountToBePaid: BigDecimal, loanPrincipal: BigDecimal): BigDecimal {
        return totalAmountToBePaid.subtract(loanPrincipal)
    }
}