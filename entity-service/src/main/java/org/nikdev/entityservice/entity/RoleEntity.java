package org.nikdev.entityservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(referencedColumnName = "id", name = "role_id")
    private List<UserEntity> users;
}
