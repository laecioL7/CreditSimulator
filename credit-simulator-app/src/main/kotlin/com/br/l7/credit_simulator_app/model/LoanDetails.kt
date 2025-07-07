package com.br.l7.credit_simulator_app.model

import java.time.LocalDate

data class LoanDetails(
    val loanAmount: Double,
    val clientBirthDate: LocalDate,
    val paymentTermInMonths: Int
)
