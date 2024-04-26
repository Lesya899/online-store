package org.nikdev.useraccount.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserIncomeOutDto {

    private String userName;
    private BigDecimal balance;
}
