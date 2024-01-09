package org.nikdev.useraccount.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserIncomeOutDto {

    private String userName;
    private BigDecimal balance;
}
