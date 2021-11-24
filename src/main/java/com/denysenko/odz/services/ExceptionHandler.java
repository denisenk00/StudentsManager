package com.denysenko.odz.services;

import javafx.scene.control.Alert;
import org.hibernate.HibernateException;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;


public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Упс щось пішло не так...");
        if (e instanceof DateTimeParseException) {
            alert.setContentText("Це поле є обов'язковим! Очікуваний формат дати дд-мм-рррр");
        } else if (e instanceof NumberFormatException) {
            alert.setContentText("Очікується числове значення! Поле є обов'язковим");
        } else if (e instanceof InvocationTargetException) {
            alert.setContentText("Для початку оберіть студента, якого хочете видалити");
        } else if (e instanceof HibernateException) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Проблеми з БД. Перевірте з'єднання...");
        } else if (e instanceof RuntimeException) {
            alert.setContentText(getInitialException(e).getMessage());
        } else if (e instanceof Exception) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
        }
        alert.showAndWait();
    }

    private Throwable getInitialException(Throwable t) {
        Throwable throwable = t.getCause();
        if (throwable == null && t != null) return t;
        return getInitialException(throwable);
    }
}
