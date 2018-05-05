package com.apulbere.quito.service;

import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.model.Payment;
import com.apulbere.quito.model.PaymentGroup;

import java.util.List;
import java.util.stream.Stream;

public interface PaymentService {
    List<Payment> process(Stream<List<String>> records, DataDescription dataDescription);
    List<PaymentGroup> merge(List<List<Payment>> payments);
}
