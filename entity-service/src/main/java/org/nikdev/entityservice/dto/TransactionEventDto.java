package org.nikdev.entityservice.dto;


import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionEventDto {


    private Integer userId;
    private BigDecimal amount;
    private LocalDateTime createAt;
}
