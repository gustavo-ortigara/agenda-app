package com.agenda.util;

import com.agenda.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ScreenManager {
    private final Stage primaryStage;
    private static final String VIEW_PATH = "/com/agenda/view/";
    
    public ScreenManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Agenda de Contatos e Eventos");
        this.primaryStage.setResizable(false);
    }
    
    public void showLoginScreen() {
        loadScreenWithController("LoginView.fxml", "Login - Agenda", 400, 500);
    }
    
    public void showMainMenu() {
        loadScreenWithController("MainMenuView.fxml", "Menu Principal - Agenda", 800, 600);
    }
    
    public void showContactsScreen() {
        loadScreenWithController("ContactsView.fxml", "Gerenciar Contatos", 900, 700);
    }
    
    public void showEventsScreen() {
        loadScreenWithController("EventsView.fxml", "Gerenciar Eventos", 900, 700);
    }
    
    public void showReportsScreen() {
        loadScreenWithController("ReportsView.fxml", "Relatórios", 800, 600);
    }
    
    private Object loadScreenWithController(String fxmlFile, String title, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
            Parent root = loader.load();
            
            // Conecta o ScreenManager ao controlador
            Object controller = loader.getController();
            if (controller instanceof LoginController) {
                ((LoginController) controller).setScreenManager(this);
            } else if (controller instanceof MainMenuController) {
                ((MainMenuController) controller).setScreenManager(this);
            } else if (controller instanceof ContactsController) {
                ((ContactsController) controller).setScreenManager(this);
            } else if (controller instanceof EventsController) {
                ((EventsController) controller).setScreenManager(this);
            } else if (controller instanceof ReportsController) {
                ((ReportsController) controller).setScreenManager(this);
            }
            
            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/styles/styles.css")).toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
            
            return controller;
        } catch (IOException e) {
            System.err.println("Erro ao carregar tela: " + fxmlFile);
            e.printStackTrace();
            return null;
        }
    }
}
