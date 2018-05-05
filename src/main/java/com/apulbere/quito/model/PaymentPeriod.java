package com.apulbere.quito.model;

import java.time.LocalDate;
import java.util.Objects;

public class PaymentPeriod {
    private LocalDate start;
    private LocalDate end;

    final static PaymentPeriod EMPTY = new PaymentPeriod();

    private PaymentPeriod() {}

    PaymentPeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(LocalDate localDate) {
        return EMPTY.equals(this) || localDate.isAfter(start) && localDate.isBefore(end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentPeriod paymentPeriod = (PaymentPeriod) o;
        return Objects.equals(start, paymentPeriod.start) &&
                Objects.equals(end, paymentPeriod.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
