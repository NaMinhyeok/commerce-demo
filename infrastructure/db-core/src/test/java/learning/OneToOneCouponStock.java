package learning;

import jakarta.persistence.*;

@Entity
public class OneToOneCouponStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private OneToOneCoupon coupon;

    private int remainingQuantity;
}
