package org.nikdev.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "rating")
public class ProductRatingEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="product_id", nullable=false)
    private ProductEntity product;
}
