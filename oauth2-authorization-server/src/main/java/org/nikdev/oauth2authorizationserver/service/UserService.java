package org.nikdev.oauth2authorizationserver.service;

import org.nikdev.oauth2authorizationserver.dto.UserCreateDto;


public interface UserService {

    /**
     * Регистрация пользователя
     *
     * @param userCreateDto
     */
    void registrationUser(UserCreateDto userCreateDto) throws Exception;
}
