package org.nikdev.oauth2authorizationserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.dto.CreateAccountDto;
import org.nikdev.oauth2authorizationserver.dto.UserCreateDto;
import org.nikdev.oauth2authorizationserver.entity.Role;
import org.nikdev.oauth2authorizationserver.entity.User;
import org.nikdev.oauth2authorizationserver.repository.RoleRepository;
import org.nikdev.oauth2authorizationserver.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CreateAccountProducerService createAccountProducerService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow() ;
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getUserRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    authorities
            );
        }


    @Override
    @Transactional
    public void registrationUser(UserCreateDto userCreateDto) throws Exception {
        Optional<User> user = userRepository.findByUserName(userCreateDto.getUserName());
        if (user.isPresent()) {
            throw new Exception(String.format("Username '%s' is already in use.", userCreateDto.getUserName()));
        }
        User createUser = new User();
        String encodedPassword = passwordEncoder.encode(userCreateDto.getPassword());
        createUser.setUserName(userCreateDto.getUserName());
        createUser.setPassword(encodedPassword);
        userRepository.save(createUser);

        Role role = new Role();
        role.setRoleName(userCreateDto.getRoleName());
        role.setUser(createUser);
        roleRepository.save(role);
        //данные нового пользователя направляем через брокер в микросервис user-account для создания аккаунта
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setUserName(userCreateDto.getUserName());
        createAccountDto.setEmail(userCreateDto.getEmail());
        createAccountProducerService.sendCreateAccountEvent(createAccountDto);
    }
}

