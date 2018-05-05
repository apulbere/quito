module com.apulbere.quito {
    requires com.google.guice;
    requires java.logging;
    requires javax.inject;
    requires commons.csv;

    exports com.apulbere.quito.service.impl to com.google.guice;
    exports com.apulbere.quito.service;
    exports com.apulbere.quito.model;
}