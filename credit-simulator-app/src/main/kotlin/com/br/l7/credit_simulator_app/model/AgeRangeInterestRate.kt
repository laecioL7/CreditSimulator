package com.br.l7.credit_simulator_app.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tb_faixa_etaria_taxa_juros")
data class AgeRangeInterestRate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "idade_min")
    val minimumAge: Int,

    @Column(name = "idade_max")
    val maximumAge: Int,

    @Column(name = "taxa_juros_ano", precision = 5, scale = 2)
    val rateInterestYear: BigDecimal
) {
    constructor() : this(0, 0, 0, BigDecimal.ZERO)
}
