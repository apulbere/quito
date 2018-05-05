package com.apulbere.com.apulbere.quito.processor;

import com.apulbere.com.apulbere.quito.model.DataDescription;
import com.apulbere.com.apulbere.quito.model.Payment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.StreamSupport.stream;

public class FilePaymentProcessor {
    private static final Logger log = Logger.getLogger(PaymentProcessor.class.getName());

    private PaymentProcessor paymentProcessor;

    public FilePaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public List<Payment> read(String filePath, DataDescription dataDescription) {
        var csvFormat = CSVFormat.DEFAULT.withDelimiter(dataDescription.getDelimiter());
        try {
            Stream<List<String>> csvStream = stream(csvFormat.parse(new FileReader(filePath)).spliterator(), false)
                    .map(this::toList);
            return paymentProcessor.process(csvStream, dataDescription);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return emptyList();
    }

    private List<String> toList(CSVRecord csvRecord) {
        var list = new ArrayList<String>();
        csvRecord.iterator().forEachRemaining(list::add);
        return list;
    }
}
