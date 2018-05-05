package com.apulbere.quito.service.impl;

import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.model.Payment;
import com.apulbere.quito.model.PaymentGroup;
import com.apulbere.quito.service.FilePaymentService;
import com.apulbere.quito.service.PaymentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.StreamSupport.stream;

@Named
public class FilePaymentServiceImpl implements FilePaymentService {
    private static final Logger log = Logger.getLogger(PaymentServiceImpl.class.getName());

    private PaymentService paymentService;

    @Inject
    public FilePaymentServiceImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public List<Payment> read(String filePath, DataDescription dataDescription) {
        var csvFormat = CSVFormat.DEFAULT.withDelimiter(dataDescription.getDelimiter());
        try {
            Stream<List<String>> csvStream = stream(csvFormat.parse(new FileReader(filePath)).spliterator(), false)
                    .map(this::toList);
            return paymentService.process(csvStream, dataDescription);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return emptyList();
    }

    private List<Payment> read(Map.Entry<String, DataDescription> dataDescription) {
        return read(dataDescription.getKey(), dataDescription.getValue());
    }

    @Override
    public List<PaymentGroup> read(Map<String, DataDescription> fileDescriptions) {
        return fileDescriptions.entrySet().stream()
                .map(this::read)
                .collect(collectingAndThen(Collectors.toList(), paymentService::merge));
    }

    private List<String> toList(CSVRecord csvRecord) {
        var list = new ArrayList<String>();
        csvRecord.iterator().forEachRemaining(list::add);
        return list;
    }
}
