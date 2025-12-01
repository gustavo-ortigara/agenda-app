package com.agenda.controller;

import com.agenda.dao.ContactDAO;
import com.agenda.dao.EventDAO;
import com.agenda.model.Contact;
import com.agenda.model.Event;
import com.agenda.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class EventsController {
    
    @FXML private TextField titleField;
    @FXML private DatePicker dateField;
    @FXML private TextField locationField;
    @FXML private ComboBox<String> contactComboBox;
    @FXML private TextArea descriptionField;
    @FXML private TableView<Event> eventsTable;
    
    private EventDAO eventDAO;
    private ContactDAO contactDAO;
    private ObservableList<Event> eventsList;
    private Event currentEvent;
    private ScreenManager screenManager;
    
    @FXML
    public void initialize() {
        eventDAO = new EventDAO();
        contactDAO = new ContactDAO();
        setupTable();
        loadEvents();
        setupContactComboBox();
        clearForm();
        
        // Listener para seleção na tabela
        eventsTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    loadEventData(newSelection);
                }
            });
    }
    
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    
    private void setupTable() {
        eventsList = FXCollections.observableArrayList();
        eventsTable.setItems(eventsList);
    }
    
    private void loadEvents() {
        eventsList.clear();
        eventsList.addAll(eventDAO.findAll());
    }
    
    private void setupContactComboBox() {
        contactComboBox.getItems().clear();
        contactComboBox.getItems().add("Nenhum");
        contactDAO.findAll().forEach(contact -> 
            contactComboBox.getItems().add(contact.getName()));
    }
    
    private void loadEventData(Event event) {
        currentEvent = event;
        titleField.setText(event.getTitle());
        dateField.setValue(event.getDate());
        locationField.setText(event.getLocation());
        contactComboBox.setValue(event.getRelatedContact());
        descriptionField.setText(event.getDescription());
    }
    
    @FXML
    private void handleSaveEvent() {
        if (validateForm()) {
            Event event;
            
            if (currentEvent != null) {
                // Atualizando evento existente
                event = currentEvent;
            } else {
                // Criando novo evento
                event = new Event(titleField.getText(), dateField.getValue(), locationField.getText(), contactComboBox.getValue(), descriptionField.getText());
            }
            
            event.setTitle(titleField.getText());
            event.setDate(dateField.getValue());
            event.setLocation(locationField.getText());
            event.setRelatedContact(contactComboBox.getValue());
            event.setDescription(descriptionField.getText());
            
            eventDAO.save(event);
            loadEvents();
            clearForm();
            showAlert("Sucesso", "Evento salvo com sucesso!", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void handleNewEvent() {
        clearForm();
    }
    
    @FXML
    private void handleDeleteEvent() {
        if (currentEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Excluir Evento");
            alert.setContentText("Tem certeza que deseja excluir o evento: " + currentEvent.getTitle() + "?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                eventDAO.delete(currentEvent.getId());
                loadEvents();
                clearForm();
                showAlert("Sucesso", "Evento excluído com sucesso!", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Aviso", "Selecione um evento para excluir.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void handleBack() {
        screenManager.showMainMenu();
    }
    
    private boolean validateForm() {
        if (titleField.getText().isEmpty()) {
            showAlert("Erro", "O campo Título é obrigatório.", Alert.AlertType.ERROR);
            return false;
        }
        if (dateField.getValue() == null) {
            showAlert("Erro", "O campo Data é obrigatório.", Alert.AlertType.ERROR);
            return false;
        }
        if (dateField.getValue().isBefore(LocalDate.now())) {
            showAlert("Erro", "A data não pode ser anterior à data atual.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        currentEvent = null;
        titleField.clear();
        dateField.setValue(LocalDate.now());
        locationField.clear();
        contactComboBox.setValue("Nenhum");
        descriptionField.clear();
        eventsTable.getSelectionModel().clearSelection();
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
