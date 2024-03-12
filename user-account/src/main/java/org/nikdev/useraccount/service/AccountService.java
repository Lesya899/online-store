package org.nikdev.useraccount.service;

import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserAccountOutDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;

import java.util.List;

public interface AccountService {



    /**
     * Добавление аккаунта пользователя
     *
     * @param userName, email
     */
    void addUserAccount(String userName, String email) throws Exception;


    /**
     * Получение аккаунта пользователя по id
     *
     * @param id
     */
    UserAccountOutDto findById(Integer id) throws Exception;


    /**
     * Получение списка всех адресов электронной почты
     *
     *
     */
    List<String> findEmailAddresses() throws Exception;


    /**
     * Удаление/блокировка/разблокировка аккаунта пользователя
     *
     * @param actionUserAccountDto
     */
    void performAction(ActionUserAccountDto actionUserAccountDto) throws Exception;


    /**
     * Пополнение баланса пользователя
     *
     * @param userIncomeDto
     */

    UserIncomeOutDto processUserIncome(UserIncomeDto userIncomeDto) throws Exception;

}
