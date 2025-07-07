package com.br.l7.credit_simulator_app.mapper

import com.br.l7.credit_simulator_app.dto.LoanDetailsDto
import com.br.l7.credit_simulator_app.model.LoanDetails

object LoanDetailsMapper {
    fun toDTO(model: LoanDetails): LoanDetailsDto {
        return LoanDetailsDto(
            loanAmount = model.loanAmount,
            clientBirthDate = model.clientBirthDate,
            paymentTermInMonths = model.paymentTermInMonths
        )
    }

    fun toModel(dto: LoanDetailsDto): LoanDetails {
        return LoanDetails(
            loanAmount = dto.loanAmount,
            clientBirthDate = dto.clientBirthDate,
            paymentTermInMonths = dto.paymentTermInMonths
        )
    }
}