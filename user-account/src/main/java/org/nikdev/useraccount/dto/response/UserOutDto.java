package org.nikdev.useraccount.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserOutDto {

    private String userName;
    private String email;
    private BigDecimal balance;
    private String status;
    private String roleName;
}
