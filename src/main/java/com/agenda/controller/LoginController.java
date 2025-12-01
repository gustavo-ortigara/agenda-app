package com.agenda.controller;

import com.agenda.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    
    private ScreenManager screenManager;
    
    @FXML
    public void initialize() {
        // Configurações iniciais se necessário
    }
    
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (isValidLogin(username, password)) {
            screenManager.showMainMenu();
        } else {
            showError("Usuário ou senha inválidos!");
        }
    }
    
    private boolean isValidLogin(String username, String password) {
        // Lógica de validação simples
        return "admin".equals(username) && "123".equals(password);
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
