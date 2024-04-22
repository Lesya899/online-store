package org.nikdev.useraccount.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserAccountOutDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.nikdev.useraccount.constant.Action.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.nikdev.useraccount.constant.AccountState.ACTIVE;


@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private static final int ACCOUNT_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;



    @Test
    void getAccountByIdTest() throws Exception {
        UserAccountOutDto expectedAccountOutDto = new UserAccountOutDto();
        expectedAccountOutDto.setUserName("innaGF8");
        expectedAccountOutDto.setEmail("inna@mail.ru");
        expectedAccountOutDto.setBalance(BigDecimal.valueOf(20000));
        expectedAccountOutDto.setAccountStatus(ACTIVE);
        when(accountService.findById(ACCOUNT_ID)).thenReturn(expectedAccountOutDto);
        mockMvc.perform(get("/v1/account/{id}", ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(expectedAccountOutDto.getUserName()))
                .andExpect(jsonPath("$.email").value(expectedAccountOutDto.getEmail()))
                .andExpect(jsonPath("$.balance").value(expectedAccountOutDto.getBalance()))
                .andExpect(jsonPath("$.accountStatus").value(expectedAccountOutDto.getAccountStatus()))
                .andDo(print());

    }


    @Test
    void getEmailAddressesTest() throws Exception {
        List<String> listEmail = new ArrayList<>(
                Arrays.asList("inna@mail.ru", "dump45@mail.ru", "verch2d@mail.ru"));
        when(accountService.findEmailAddresses()).thenReturn(listEmail);
        mockMvc.perform(get("/v1/account/email-addresses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listEmail.size()))
                .andDo(print());
    }


    @Test
    void deleteAccountTest() throws Exception {
        ActionUserAccountDto actionUserAccountDto = new ActionUserAccountDto();
        actionUserAccountDto.setId(ACCOUNT_ID);
        actionUserAccountDto.setAction(DELETE);
        doNothing().when(accountService).performAction(actionUserAccountDto);
        mockMvc.perform(post("/v1/account/action")
                        .content(objectMapper.writeValueAsString(actionUserAccountDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted"))
                        .andDo(print());
    }

    @Test
    void lockDownAccountTest() throws Exception {
        ActionUserAccountDto actionUserAccountDto = new ActionUserAccountDto();
        actionUserAccountDto.setId(ACCOUNT_ID);
        actionUserAccountDto.setAction(BLOCK);
        doNothing().when(accountService).performAction(actionUserAccountDto);
        mockMvc.perform(post("/v1/account/action")
                        .content(objectMapper.writeValueAsString(actionUserAccountDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User is blocked"))
                .andDo(print());
        verify(accountService, times(1)).performAction(actionUserAccountDto);
    }

    @Test
    void unlockAccountTest() throws Exception {
        ActionUserAccountDto actionUserAccountDto = new ActionUserAccountDto();
        actionUserAccountDto.setId(ACCOUNT_ID);
        actionUserAccountDto.setAction(UNBLOCK);
        doNothing().when(accountService).performAction(actionUserAccountDto);
        mockMvc.perform(post("/v1/account/action")
                        .content(objectMapper.writeValueAsString(actionUserAccountDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User is unblocked"))
                .andDo(print());
        verify(accountService, times(1)).performAction(actionUserAccountDto);
    }


    @Test
    void refillBalance() throws Exception {
        UserIncomeDto userIncomeDto = new UserIncomeDto();
        userIncomeDto.setId(ACCOUNT_ID);
        userIncomeDto.setAmount(BigDecimal.valueOf(10000));

        UserIncomeOutDto userIncomeOutDto = new UserIncomeOutDto();
        userIncomeOutDto.setUserName("steffRY");
        userIncomeOutDto.setBalance(BigDecimal.valueOf(35000));

        when(accountService.processUserIncome(userIncomeDto)).thenReturn(userIncomeOutDto);
        mockMvc.perform(post("/v1/account/income")
                        .content(objectMapper.writeValueAsString(userIncomeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(userIncomeOutDto.getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(userIncomeOutDto.getBalance()))
                .andDo(print());

    }
}