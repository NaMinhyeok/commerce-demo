package com.nmh.commerce.product;

import com.nmh.commerce.domain.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {
    private final Long id;
    private final String name;
    private final Money price;

    @Builder
    private Product(Long id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
