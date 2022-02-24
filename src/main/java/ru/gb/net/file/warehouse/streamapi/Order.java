package ru.gb.net.file.warehouse.streamapi;

import java.math.BigDecimal;
import java.util.Random;

public class Order {

    private final Long id;

    public BigDecimal getAmount() {
        return amount;
    }

    private final BigDecimal amount = BigDecimal.valueOf(new Random().nextInt(1000));

    public Order(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

