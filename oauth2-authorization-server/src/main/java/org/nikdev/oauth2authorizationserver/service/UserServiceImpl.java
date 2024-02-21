package org.nikdev.oauth2authorizationserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nikdev.oauth2authorizationserver.dto.UserCreateDto;
import org.nikdev.oauth2authorizationserver.entity.Role;
import org.nikdev.oauth2authorizationserver.entity.User;
import org.nikdev.oauth2authorizationserver.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userCreateDto.getPassword());
        user.setUserName(userCreateDto.getUserName());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}

