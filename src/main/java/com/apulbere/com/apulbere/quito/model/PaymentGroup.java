package com.apulbere.com.apulbere.quito.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaymentGroup {
    private LocalDate dates;
    private List<BigDecimal> amounts;

    public PaymentGroup(LocalDate dates, List<BigDecimal> amounts) {
        this.dates = dates;
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "PaymentGroup{" +
                "dates=" + dates +
                ", amounts=" + amounts +
                "}\n";
    }
}
