package com.denysenko.odz;

import com.denysenko.odz.services.ExceptionHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StudentsManagerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainScene.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
