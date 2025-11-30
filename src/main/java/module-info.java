module com.agenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    
    opens com.agenda to javafx.fxml;
    opens com.agenda.controller to javafx.fxml;
    opens com.agenda.model to com.fasterxml.jackson.databind;
    opens com.agenda.view to javafx.fxml;
    
    exports com.agenda;
    exports com.agenda.controller;
    exports com.agenda.model;
    exports com.agenda.dao;
    exports com.agenda.util;
}
