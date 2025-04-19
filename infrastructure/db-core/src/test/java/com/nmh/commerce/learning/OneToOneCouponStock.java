package com.nmh.commerce.learning;

import jakarta.persistence.*;

@Entity
public class OneToOneCouponStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private OneToOneCoupon coupon;

    private int remainingQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OneToOneCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(OneToOneCoupon coupon) {
        this.coupon = coupon;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
