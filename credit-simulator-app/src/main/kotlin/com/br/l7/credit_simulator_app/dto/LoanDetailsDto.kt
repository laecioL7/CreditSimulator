package com.br.l7.credit_simulator_app.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class LoanDetailsDto(
    @field:NotNull(message = "O valor do empréstimo não pode ser nulo")
    @field:Min(value = 50, message = "O valor do empréstimo deve ser no mínimo 50")
    val loanAmount: Double,

    @field:NotNull(message = "A data de nascimento do cliente não pode ser nula")
    @field:Past(message = "A data de nascimento do cliente deve estar no passado")
    val clientBirthDate: LocalDate,

    @field:NotNull(message = "O prazo de pagamento não pode ser nulo")
    @field:Min(value = 1, message = "O prazo de pagamento deve ser de pelo menos 1 mês")
    val paymentTermInMonths: Int
)