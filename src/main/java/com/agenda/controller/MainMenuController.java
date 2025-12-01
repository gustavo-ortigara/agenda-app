package com.agenda.controller;

import com.agenda.util.ScreenManager;
import javafx.fxml.FXML;

public class MainMenuController {
    
    private ScreenManager screenManager;
    
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    
    @FXML
    private void handleContacts() {
        screenManager.showContactsScreen();
    }
    
    @FXML
    private void handleEvents() {
        screenManager.showEventsScreen();
    }
    
    @FXML
    private void handleReports() {
        screenManager.showReportsScreen();
    }
    
    @FXML
    private void handleLogout() {
        screenManager.showLoginScreen();
    }
}
