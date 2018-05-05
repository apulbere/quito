package com.apulbere.com.apulbere.quito.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import static java.math.BigDecimal.ZERO;

public class DataDescription {
    char delimiter;
    int amountPosition;
    int datePosition;
    DateTimeFormatter dateTimeFormatter;
    DecimalFormat decimalFormat;
    Predicate<List<String>> lineFilter;
    Predicate<BigDecimal> amountFilter;
    PaymentPeriod paymentPeriod;

    private DataDescription() {}

    private DataDescription(char delimiter,
                            int amountPosition,
                            int datePosition,
                            DateTimeFormatter dateTimeFormatter,
                            DecimalFormat decimalFormat,
                            Predicate<List<String>> lineFilter,
                            Predicate<BigDecimal> amountFilter,
                            PaymentPeriod paymentPeriod) {
        this.delimiter = delimiter;
        this.amountPosition = amountPosition;
        this.datePosition = datePosition;
        this.dateTimeFormatter = dateTimeFormatter;
        this.decimalFormat = decimalFormat;
        this.lineFilter = lineFilter;
        this.amountFilter = amountFilter;
        this.paymentPeriod = paymentPeriod;
    }

    public char getDelimiter() {
        return delimiter;
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

    public Predicate<List<String>> getLineFilter() {
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

        private Predicate<List<String>> defaultLineFilter() {
            return line -> line.size() >= 2 && line.get(0).contains("/");
        }

        private Predicate<BigDecimal> defaultAmountFilter() {
            return amount -> ZERO.compareTo(amount) > 0;
        }

        public DataDescriptionBuilder separator(char separator) {
            this.delimiter = separator;
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

        public DataDescriptionBuilder lineFilter(Predicate<List<String>> lineFilter) {
            this.lineFilter = lineFilter;
            return this;
        }

        public DataDescriptionBuilder between(LocalDate start, LocalDate end) {
            this.paymentPeriod = new PaymentPeriod(start, end);
            return this;
        }

        public DataDescription build() {
            return new DataDescription(
                delimiter,
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
