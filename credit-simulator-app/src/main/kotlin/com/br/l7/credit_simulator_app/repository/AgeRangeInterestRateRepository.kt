package com.br.l7.credit_simulator_app.repository

import com.br.l7.credit_simulator_app.model.AgeRangeInterestRate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface AgeRangeInterestRateRepository : JpaRepository<AgeRangeInterestRate, Long> {

    @Query("select taxa_juros_ano " +
            "from tb_faixa_etaria_taxa_juros " +
            "where ?1 between idade_min and idade_max ", nativeQuery = true)
    fun findInterestRateByAge(clientAge: Int): BigDecimal
}