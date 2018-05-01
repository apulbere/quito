package com.apulbere.com.apulbere.quito.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Predicate;

import static java.math.BigDecimal.ZERO;

public class DataDescription {
    String separator;
    int amountPosition;
    int datePosition;
    DateTimeFormatter dateTimeFormatter;
    DecimalFormat decimalFormat;
    Predicate<String[]> lineFilter;
    Predicate<BigDecimal> amountFilter;
    PaymentPeriod paymentPeriod;

    private DataDescription() {}

    private DataDescription(String separator,
                            int amountPosition,
                            int datePosition,
                            DateTimeFormatter dateTimeFormatter,
                            DecimalFormat decimalFormat,
                            Predicate<String[]> lineFilter,
                            Predicate<BigDecimal> amountFilter,
                            PaymentPeriod paymentPeriod) {
        this.separator = separator;
        this.amountPosition = amountPosition;
        this.datePosition = datePosition;
        this.dateTimeFormatter = dateTimeFormatter;
        this.decimalFormat = decimalFormat;
        this.lineFilter = lineFilter;
        this.amountFilter = amountFilter;
        this.paymentPeriod = paymentPeriod;
    }

    public String getSeparator() {
        return separator;
    }

    public int getAmountPosition() {
        return amountPosition;
    }

    public int getDatePosition() {
        return datePosition;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public Predicate<String[]> getLineFilter() {
        return lineFilter;
    }

    public Predicate<BigDecimal> getAmountFilter() {
        return amountFilter;
    }

    public static DataDescriptionBuilder builder() {
        return new DataDescriptionBuilder();
    }

    public PaymentPeriod getPaymentPeriod() {
        return paymentPeriod;
    }

    public static class DataDescriptionBuilder extends DataDescription {

        private DecimalFormat defaultDecimalFormat() {
            var formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            formatSymbols.setDecimalSeparator('.');
            formatSymbols.setGroupingSeparator(' ');

            var df = new DecimalFormat("###,###.#", formatSymbols);
            df.setParseBigDecimal(true);
            return df;
        }

        private DateTimeFormatter defaultDateTimeFormatter() {
            return DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        private Predicate<String[]> defaultLineFilter() {
            return line -> line.length >= 2 && line[0].contains("/");
        }

        private Predicate<BigDecimal> defaultAmountFilter() {
            return amount -> ZERO.compareTo(amount) > 0;
        }

        public DataDescriptionBuilder separator(String separator) {
            this.separator = separator;
            return this;
        }

        public DataDescriptionBuilder amountPosition(int amountPosition) {
            this.amountPosition = amountPosition;
            return this;
        }

        public DataDescriptionBuilder datePosition(int datePosition) {
            this.datePosition = datePosition;
            return this;
        }

        public DataDescriptionBuilder lineFilter(Predicate<String[]> lineFilter) {
            this.lineFilter = lineFilter;
            return this;
        }

        public DataDescriptionBuilder between(LocalDate start, LocalDate end) {
            this.paymentPeriod = new PaymentPeriod(start, end);
            return this;
        }

        public DataDescription build() {
            return new DataDescription(
                separator,
                amountPosition,
                datePosition,
                defaultDateTimeFormatter(),
                defaultDecimalFormat(),
                lineFilter != null ? lineFilter : defaultLineFilter(),
                defaultAmountFilter(),
                paymentPeriod != null ? paymentPeriod : PaymentPeriod.EMPTY
            );
        }
    }
}
