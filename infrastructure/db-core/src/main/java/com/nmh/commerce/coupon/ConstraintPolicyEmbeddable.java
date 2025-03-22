package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConstraintPolicyEmbeddable {
    @Column(name = "constraint_type")
    @Enumerated(EnumType.STRING)
    public ConstraintType type;
    @Convert(converter = MoneyConverter.class)
    public Money minimumPrice;

    @Builder
    private ConstraintPolicyEmbeddable(ConstraintType type, Money minimumPrice) {
        this.type = type;
        this.minimumPrice = minimumPrice;
    }

    public static ConstraintPolicyEmbeddable from(ConstraintPolicy constraintPolicy) {
        if (constraintPolicy instanceof MinimumPriceConstraintPolicy) {
            return ConstraintPolicyEmbeddable.builder()
                .type(ConstraintType.MIN_PURCHASE_PRICE)
                .minimumPrice(((MinimumPriceConstraintPolicy) constraintPolicy).getMinimumPrice())
                .build();
        }
        throw new IllegalArgumentException("지원하지 않는 제약 정책입니다.");
    }

    public ConstraintPolicy toDomain() {
        return switch (type) {
            case MIN_PURCHASE_PRICE -> new MinimumPriceConstraintPolicy(minimumPrice);
        };
    }

    public enum ConstraintType {
        MIN_PURCHASE_PRICE,
    }
}
