package org.nikdev.oauth2authorizationserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_data")
public class User
{
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private List<Role> userRoles;
}

