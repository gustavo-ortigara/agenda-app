package com.agenda;

import com.agenda.util.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    private ScreenManager screenManager;
    
    @Override
    public void start(Stage primaryStage) {
        screenManager = new ScreenManager(primaryStage);
        screenManager.showLoginScreen();
    }
    
    @Override
    public void stop() {
        // Limpeza de recursos se necessário
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
