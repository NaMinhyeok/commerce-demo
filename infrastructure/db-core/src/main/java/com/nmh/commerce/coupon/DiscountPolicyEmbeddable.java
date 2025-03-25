package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.domain.DiscountRate;
import com.nmh.commerce.utils.MoneyConverter;
import com.nmh.commerce.utils.DiscountRateConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DiscountPolicyEmbeddable {
    @Column(name = "discount_type")
    @Enumerated(EnumType.STRING)
    private DiscountType type;
    @Convert(converter = DiscountRateConverter.class)
    private DiscountRate discountRate;
    @Convert(converter = MoneyConverter.class)
    private Money price;

    @Builder
    private DiscountPolicyEmbeddable(DiscountType type, DiscountRate discountRate, Money price) {
        this.type = type;
        this.discountRate = discountRate;
        this.price = price;
    }

    public static DiscountPolicyEmbeddable from(DiscountPolicy discountPolicy) {
        if (discountPolicy instanceof PercentageDiscountPolicy) {
            return DiscountPolicyEmbeddable.builder()
                .type(DiscountType.PERCENTAGE)
                .discountRate(((PercentageDiscountPolicy) discountPolicy).getDiscountRate())
                .build();
        }
        if (discountPolicy instanceof FixedPriceDiscountPolicy) {
            return DiscountPolicyEmbeddable.builder()
                .type(DiscountType.FIXED_PRICE)
                .price(((FixedPriceDiscountPolicy) discountPolicy).getDiscountPrice())
                .build();
        }
        throw new IllegalArgumentException("지원하지 않는 할인 정책입니다.");
    }

    public DiscountPolicy toDomain() {
        return switch (type) {
            case PERCENTAGE -> new PercentageDiscountPolicy(discountRate);
            case FIXED_PRICE -> new FixedPriceDiscountPolicy(price);
        };
    }

    public enum DiscountType {
        PERCENTAGE, FIXED_PRICE
    }

}
