CREATE TABLE if not exists tb_faixa_etaria_taxa_juros (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            idade_min INT NOT NULL,
                                            idade_max INT NOT NULL,
                                            taxa_juros_ano DECIMAL(5, 2) NOT NULL
);