package com.nmh.commerce.learning;

import jakarta.persistence.*;

@Entity
public class OneToOneCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "coupon", cascade = CascadeType.ALL)
    private OneToOneCouponStock stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OneToOneCouponStock getStock() {
        return stock;
    }

    public void setStock(OneToOneCouponStock stock) {
        this.stock = stock;
    }
}
