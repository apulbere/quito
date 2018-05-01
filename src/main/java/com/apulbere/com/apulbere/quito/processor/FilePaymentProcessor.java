package com.apulbere.com.apulbere.quito.processor;

import com.apulbere.com.apulbere.quito.model.DataDescription;
import com.apulbere.com.apulbere.quito.model.Payment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class FilePaymentProcessor {
    private static final Logger log = Logger.getLogger(PaymentProcessor.class.getName());

    private PaymentProcessor paymentProcessor;

    public FilePaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public List<Payment> read(String filePath, DataDescription dataDescription) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return paymentProcessor.process(stream, dataDescription);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return emptyList();
    }
}
