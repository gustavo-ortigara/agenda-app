package com.agenda.controller;

import com.agenda.dao.ContactDAO;
import com.agenda.model.Contact;
import com.agenda.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ContactsController {
    
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextArea observationsField;
    @FXML private TableView<Contact> contactsTable;
    
    private ContactDAO contactDAO;
    private ObservableList<Contact> contactsList;
    private Contact currentContact;
    private ScreenManager screenManager;
    
    @FXML
    public void initialize() {
        contactDAO = new ContactDAO();
        setupTable();
        loadContacts();
        setupCategoryComboBox();
        clearForm();
        
        // Listener para seleção na tabela
        contactsTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    loadContactData(newSelection);
                }
            });
    }
    
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    
    private void setupTable() {
        contactsList = FXCollections.observableArrayList();
        contactsTable.setItems(contactsList);
        
        // Configuração das colunas (necessário para o TableView funcionar)
        // O FXML já define as colunas, mas o TableView precisa de um tipo de dado
        // e o PropertyValueFactory precisa ser importado e usado no FXML ou configurado aqui.
        // Como o FXML já está definido, vamos garantir que o TableView está pronto.
        // Se o FXML estiver correto, não precisamos redefinir as colunas aqui.
    }
    
    private void loadContacts() {
        contactsList.clear();
        contactsList.addAll(contactDAO.findAll());
    }
    
    private void setupCategoryComboBox() {
        categoryComboBox.getItems().addAll("Trabalho", "Pessoal", "Família", "Amigos", "Outros");
        // Adiciona categorias existentes no JSON
        contactDAO.getCategories().forEach(cat -> {
            if (!categoryComboBox.getItems().contains(cat)) {
                categoryComboBox.getItems().add(cat);
            }
        });
    }
    
    private void loadContactData(Contact contact) {
        currentContact = contact;
        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        categoryComboBox.setValue(contact.getCategory());
        observationsField.setText(contact.getObservations());
    }
    
    @FXML
    private void handleSaveContact() {
        if (validateForm()) {
            Contact contact;
            
            if (currentContact != null) {
                // Atualizando contato existente
                contact = currentContact;
            } else {
                // Criando novo contato
                // Os valores iniciais são irrelevantes, pois serão sobrescritos
                contact = new Contact(nameField.getText(), phoneField.getText(), emailField.getText(), categoryComboBox.getValue(), observationsField.getText());
            }
            
            contact.setName(nameField.getText());
            contact.setPhone(phoneField.getText());
            contact.setEmail(emailField.getText());
            contact.setCategory(categoryComboBox.getValue());
            contact.setObservations(observationsField.getText());
            
            contactDAO.save(contact);
            loadContacts();
            clearForm();
            showAlert("Sucesso", "Contato salvo com sucesso!", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void handleNewContact() {
        clearForm();
    }
    
    @FXML
    private void handleDeleteContact() {
        if (currentContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Excluir Contato");
            alert.setContentText("Tem certeza que deseja excluir o contato: " + currentContact.getName() + "?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                contactDAO.delete(currentContact.getId());
                loadContacts();
                clearForm();
                showAlert("Sucesso", "Contato excluído com sucesso!", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Aviso", "Selecione um contato para excluir.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void handleBack() {
        screenManager.showMainMenu();
    }
    
    private boolean validateForm() {
        if (nameField.getText().isEmpty()) {
            showAlert("Erro", "O campo Nome é obrigatório.", Alert.AlertType.ERROR);
            return false;
        }
        if (phoneField.getText().isEmpty()) {
            showAlert("Erro", "O campo Telefone é obrigatório.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        currentContact = null;
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        categoryComboBox.setValue(null);
        observationsField.clear();
        contactsTable.getSelectionModel().clearSelection();
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
