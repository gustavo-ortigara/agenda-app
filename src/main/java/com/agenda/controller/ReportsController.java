package com.agenda.controller;

import com.agenda.dao.ContactDAO;
import com.agenda.dao.EventDAO;
import com.agenda.model.Contact;
import com.agenda.model.Event;
import com.agenda.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportsController {
    
    @FXML private PieChart contactsChart;
    @FXML private BarChart<String, Number> eventsChart;
    
    private ContactDAO contactDAO;
    private EventDAO eventDAO;
    private ScreenManager screenManager;
    
    @FXML
    public void initialize() {
        contactDAO = new ContactDAO();
        eventDAO = new EventDAO();
        updateCharts();
    }
    
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    
    @FXML
    public void updateCharts() {
        updateContactsChart();
        updateEventsChart();
    }
    
    private void updateContactsChart() {
        List<Contact> contacts = contactDAO.findAll();
        
        Map<String, Long> contactsByCategory = contacts.stream()
            .collect(Collectors.groupingBy(
                contact -> contact.getCategory() != null && !contact.getCategory().isEmpty() 
                    ? contact.getCategory() 
                    : "Sem Categoria",
                Collectors.counting()
            ));
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        contactsByCategory.forEach((category, count) -> 
            pieChartData.add(new PieChart.Data(category + " (" + count + ")", count)));
        
        contactsChart.setData(pieChartData);
        contactsChart.setTitle("Contatos por Categoria");
        contactsChart.setLegendVisible(true);
    }
    
    private void updateEventsChart() {
        List<Event> events = eventDAO.findAll();
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        
        // Prepara dados para os últimos 6 meses
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Eventos por Mês - " + currentYear);
        
        for (int i = 5; i >= 0; i--) {
            LocalDate targetDate = now.minusMonths(i);
            int month = targetDate.getMonthValue();
            int year = targetDate.getYear();
            
            long eventCount = events.stream()
                .filter(event -> {
                    LocalDate eventDate = event.getDate();
                    return eventDate.getMonthValue() == month && eventDate.getYear() == year;
                })
                .count();
            
            String monthName = targetDate.getMonth().toString().substring(0, 3);
            series.getData().add(new XYChart.Data<>(monthName, eventCount));
        }
        
        eventsChart.getData().clear();
        eventsChart.getData().add(series);
        eventsChart.setTitle("Eventos por Mês");
    }
    
    @FXML
    private void handleBack() {
        screenManager.showMainMenu();
    }
}
