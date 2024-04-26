package org.nikdev.entityservice.dto;


import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionEventDto {


    private Integer accountId;
    private BigDecimal amount;
    private LocalDateTime createAt;
}
