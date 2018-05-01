package com.apulbere.com.apulbere.quito.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private LocalDate date;
    private BigDecimal amount;

    public Payment(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
