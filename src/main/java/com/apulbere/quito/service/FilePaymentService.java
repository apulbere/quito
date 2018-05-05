package com.apulbere.quito.service;

import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.model.Payment;
import com.apulbere.quito.model.PaymentGroup;

import java.util.List;
import java.util.Map;

public interface FilePaymentService {
    List<Payment> read(String filePath, DataDescription dataDescription);
    List<PaymentGroup> read(Map<String, DataDescription> fileDescriptions);
}
