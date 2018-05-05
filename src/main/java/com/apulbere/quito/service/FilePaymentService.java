package com.apulbere.quito.service;

import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.model.Payment;

import java.util.List;

public interface FilePaymentService {
    List<Payment> read(String filePath, DataDescription dataDescription);
}
