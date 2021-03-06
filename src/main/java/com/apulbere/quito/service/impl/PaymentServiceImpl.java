package com.apulbere.quito.service.impl;

import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.model.Payment;
import com.apulbere.quito.model.PaymentGroup;
import com.apulbere.quito.service.PaymentService;

import javax.inject.Named;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Named
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Override
    public List<Payment> process(Stream<List<String>> records, DataDescription dataDescription) {
        var mapping = mapping(Payment::getAmount, reducing(BigDecimal.ZERO, BigDecimal::add));

        return records.filter(dataDescription.getLineFilter())
                .map(line -> createPayment(line, dataDescription))
                .filter(payment -> isIncluded(payment, dataDescription))
                .collect(groupingBy(Payment::getDate, mapping))
                .entrySet()
                .stream()
                .map(this::createPayment)
                .collect(toList());
    }

    private boolean isIncluded(Payment payment, DataDescription dataDescription) {
        return dataDescription.getAmountFilter().test(payment.getAmount())
                && dataDescription.getPaymentPeriod().contains(payment.getDate());
    }

    @Override
    public List<PaymentGroup> merge(List<List<Payment>> payments) {
        return payments.stream().flatMap(List::stream)
                .collect(groupingBy(Payment::getDate, mapping(Payment::getAmount, toList())))
                .entrySet()
                .stream()
                .map(this::createPaymentGroup)
                .collect(toList());
    }

    private PaymentGroup createPaymentGroup(Entry<LocalDate, List<BigDecimal>> entry) {
        return new PaymentGroup(entry.getKey(), entry.getValue());
    }

    private Payment createPayment(Entry<LocalDate, BigDecimal> entry) {
        return new Payment(entry.getKey(), entry.getValue());
    }

    private Payment createPayment(List<String> record, DataDescription dataDescription) {
        var date = LocalDate.parse(record.get(dataDescription.getDatePosition()), dataDescription.getDateTimeFormatter());
        var amount = BigDecimal.ZERO;
        try {
            amount = (BigDecimal) dataDescription.getDecimalFormat().parse(record.get(dataDescription.getAmountPosition()));
        } catch (ParseException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return new Payment(date, amount);
    }
}
