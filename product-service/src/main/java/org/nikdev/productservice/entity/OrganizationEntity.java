package org.nikdev.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "organization")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_organization", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logotype", nullable = false)
    private String logotype;


    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;

}
