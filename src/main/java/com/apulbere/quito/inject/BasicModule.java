package com.apulbere.quito.inject;

import com.apulbere.quito.service.FilePaymentService;
import com.apulbere.quito.service.PaymentService;
import com.apulbere.quito.service.impl.FilePaymentServiceImpl;
import com.apulbere.quito.service.impl.PaymentServiceImpl;
import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PaymentService.class).to(PaymentServiceImpl.class);
        bind(FilePaymentService.class).to(FilePaymentServiceImpl.class);
    }
}
