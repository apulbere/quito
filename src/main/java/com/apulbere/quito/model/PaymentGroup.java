package com.apulbere.quito.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PaymentGroup {
    private LocalDate dates;
    private List<BigDecimal> amounts;

    public PaymentGroup(LocalDate dates, List<BigDecimal> amounts) {
        this.dates = dates;
        this.amounts = amounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentGroup that = (PaymentGroup) o;
        return Objects.equals(dates, that.dates) &&
                Objects.equals(amounts, that.amounts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dates, amounts);
    }

    @Override
    public String toString() {
        return "PaymentGroup{" +
                "dates=" + dates +
                ", amounts=" + amounts +
                "}\n";
    }
}
