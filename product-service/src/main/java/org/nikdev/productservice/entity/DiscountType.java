package org.nikdev.productservice.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "discount_type")
public class DiscountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "discount_amount")
    private Integer discountAmount;

    @OneToMany(mappedBy = "discountType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscountEntity> listDiscount;
}
