package com.apulbere.quito;

import com.apulbere.quito.inject.BasicModule;
import com.apulbere.quito.model.DataDescription;
import com.apulbere.quito.service.FilePaymentService;
import com.apulbere.quito.service.PaymentService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;

import static java.time.LocalDate.of;

public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        var paymentProcessor = injector.getInstance(PaymentService.class);
        var filePaymentProcessor = injector.getInstance(FilePaymentService.class);


        var monefyDataDescription = DataDescription.builder()
                .separator(',')
                .datePosition(0)
                .amountPosition(3)
                .lineFilter(record -> record.get(0).contains("/") && !record.get(0).contains("Initial balance"))
                .between(of(2018, 4, 1), of(2018, 4, 30))
                .build();

        var monefyPayments = filePaymentProcessor.read("/home/adrian/Downloads/Monefy.Data.01-05-2018.csv", monefyDataDescription);

        var bankDataDescription = DataDescription.builder()
                .separator(';')
                .datePosition(6)
                .amountPosition(1)
                .lineFilter(record -> record.size() > 2 && record.get(0).contains("/") && record.get(1).length() != 0)
                .build();

        var bankPayments = filePaymentProcessor.read("/home/adrian/Downloads/RLV_0002_01_05_2018.CSV", bankDataDescription);

        var merge = paymentProcessor.merge(List.of(monefyPayments, bankPayments));
        System.out.println(merge);

    }
}
