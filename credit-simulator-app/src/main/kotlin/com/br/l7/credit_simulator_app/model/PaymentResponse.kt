package com.br.l7.credit_simulator_app.model

import java.math.BigDecimal

data class PaymentResponse(
    val totalAmountToBePaid: BigDecimal,
    val monthlyInstallmentAmount: BigDecimal,
    val totalInterestPaid: BigDecimal)