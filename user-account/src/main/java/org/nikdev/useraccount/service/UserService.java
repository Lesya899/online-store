package org.nikdev.useraccount.service;

import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.dto.response.UserOutDto;

public interface UserService {


    /**
     * Получение пользователя по id
     *
     * @param id
     */
    UserOutDto findById(Integer id) throws Exception;


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
