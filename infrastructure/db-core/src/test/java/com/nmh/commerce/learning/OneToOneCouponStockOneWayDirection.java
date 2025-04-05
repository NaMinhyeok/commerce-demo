package com.nmh.commerce.learning;

import jakarta.persistence.*;

@Entity
public class OneToOneCouponStockOneWayDirection {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private OneToOneCouponHasNotRelation coupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OneToOneCouponHasNotRelation getCoupon() {
        return coupon;
    }

    public void setCoupon(OneToOneCouponHasNotRelation coupon) {
        this.coupon = coupon;
    }
}
