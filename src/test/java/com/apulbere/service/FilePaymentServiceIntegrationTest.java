package com.apulbere.service;

import com.apulbere.quito.inject.BasicModule;
import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.model.PaymentGroup;
import com.apulbere.quito.service.FilePaymentService;
import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilePaymentServiceIntegrationTest {

    private FilePaymentService filePaymentService;

    @BeforeEach
    void init() {
        filePaymentService = Guice.createInjector(new BasicModule()).getInstance(FilePaymentService.class);
    }

    @Test
    @DisplayName("two files's records are grouped by date")
    void test1() {
        assertEquals(getExpectedResult(), filePaymentService.read(getDataDescriptionMap()));
    }

    private List<PaymentGroup> getExpectedResult() {
        return List.of(
            new PaymentGroup(LocalDate.of(2018, 4, 27), List.of(of("-39.58"), of("-39.58"))),
            new PaymentGroup(LocalDate.of(2018, 4, 26), List.of(of("-47.53"), of("-47.53"))),
            new PaymentGroup(LocalDate.of(2018, 4, 25), List.of(of("-51.86"), of("-51.86"))),
            new PaymentGroup(LocalDate.of(2018, 4, 24), List.of(of("-51.45"), of("-51.45"))),
            new PaymentGroup(LocalDate.of(2018, 4, 23), List.of(of("-48.06")))
        );
    }

    private BigDecimal of(String value) {
        return new BigDecimal(value);
    }

    private Map<String, DataDescription> getDataDescriptionMap() {
        var data1Description = DataDescription.builder()
                .separator(';')
                .datePosition(6)
                .amountPosition(1)
                .lineFilter(record -> record.size() > 2 && record.get(0).contains("/") && record.get(1).length() != 0)
                .build();

        var data2Description = DataDescription.builder()
                .separator(',')
                .datePosition(0)
                .amountPosition(3)
                .lineFilter(record -> record.get(0).contains("/") && !record.get(0).contains("Initial balance"))
                .between(LocalDate.of(2018, 4, 1), LocalDate.of(2018, 4, 30))
                .build();

        return Map.of(
                getFile("/data2.csv"), data2Description,
                getFile("/data1.csv"), data1Description
        );
    }

    private String getFile(String name) {
        return this.getClass().getResource(name).getFile();
    }
}
