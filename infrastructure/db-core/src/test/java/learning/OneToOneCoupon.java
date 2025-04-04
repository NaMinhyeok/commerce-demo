package learning;

import jakarta.persistence.*;

@Entity
public class OneToOneCoupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "coupon", cascade = CascadeType.ALL)
    private OneToOneCouponStock stock;
}
