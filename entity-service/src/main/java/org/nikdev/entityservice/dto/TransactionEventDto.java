package org.nikdev.entityservice.dto;


import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionEventDto {


    private Integer accountId;
    private BigDecimal amount;
    private LocalDateTime createAt;
}
