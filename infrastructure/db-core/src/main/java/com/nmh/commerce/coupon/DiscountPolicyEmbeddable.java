package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.domain.Percentage;
import com.nmh.commerce.utils.MoneyConverter;
import com.nmh.commerce.utils.PercentageConverter;
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
    @Convert(converter = PercentageConverter.class)
    private Percentage percentage;
    @Convert(converter = MoneyConverter.class)
    private Money price;

    @Builder
    private DiscountPolicyEmbeddable(DiscountType type, Percentage percentage, Money price) {
        this.type = type;
        this.percentage = percentage;
        this.price = price;
    }

    public static DiscountPolicyEmbeddable from(DiscountPolicy discountPolicy) {
        if (discountPolicy instanceof PercentageDiscountPolicy) {
            return DiscountPolicyEmbeddable.builder()
                .type(DiscountType.PERCENTAGE)
                .percentage(((PercentageDiscountPolicy) discountPolicy).getDiscountRate())
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
            case PERCENTAGE -> new PercentageDiscountPolicy(percentage);
            case FIXED_PRICE -> new FixedPriceDiscountPolicy(price);
        };
    }

    public enum DiscountType {
        PERCENTAGE, FIXED_PRICE
    }

}
