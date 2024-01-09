package org.nikdev.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "product_review")
public class ProductReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
