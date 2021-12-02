package com.denysenko.odz.controllers;

import com.denysenko.odz.models.Student;
import com.denysenko.odz.services.StudentsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddSceneController {
    private StudentsService studentsService = new StudentsService();

    @FXML
    private TextField nameTF;
    @FXML
    private TextField surnameTF;
    @FXML
    private TextField fatherNameTF;
    @FXML
    private TextField birthDateTF;
    @FXML
    private TextField addressTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField facultyTF;
    @FXML
    private TextField courseTF;
    @FXML
    private TextField groupTF;

    public void addStudent() throws IOException {
        String name = nameTF.getText().trim();
        String surname = surnameTF.getText().trim();
        String fatherName = fatherNameTF.getText().trim();
        String stBirthDate = birthDateTF.getText().trim();
        String address = addressTF.getText().trim();
        String phone = phoneTF.getText().trim();
        String faculty = facultyTF.getText().trim();
        String stCourse = courseTF.getText().trim();
        String group = groupTF.getText().trim();
        if (name == null || name.isEmpty()
                || surname == null || surname.isEmpty()
                || faculty == null || faculty.isEmpty()
                || stCourse == null || stCourse.isEmpty()
                || group == null || group.isEmpty()
                || stBirthDate == null || stBirthDate.isEmpty()
        ) throw new RuntimeException("Перевірте чи заповнені обов'язкові поля!");
        if (!name.matches("^[а-яА-ЯёЁa-zA-ZЇїІіЄєҐґ]+$")) throw new RuntimeException("Значення поля не схоже на ім'я");
        if (!surname.matches("^[а-яА-ЯёЁa-zA-ZЇїІіЄєҐґ]+$"))
            throw new RuntimeException("Значення поля не схоже на прізвище");
        if (fatherName != null && !fatherName.isEmpty() && !fatherName.matches("^[а-яА-ЯёЁa-zA-ZЇїІіЄєҐґ]+$")) {
            throw new RuntimeException("Значення поля не схоже на по-батькові");
        }
        if (phone != null && !phone.isEmpty() && !phone.matches("[\\+]?\\d*(\\(\\d{3}\\))?\\d*\\-?\\d*\\-?\\d*\\d$")) {
            throw new RuntimeException("Значення не схоже на номер телефону");
        }
        LocalDate birthDate;
        int course;
        try {
            birthDate = LocalDate.parse(stBirthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            course = Integer.valueOf(courseTF.getText());
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Дата народження очікується в форматі дд-мм-рррр");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Значення курсу повинно бути числовим");
        }
        if (!birthDate.isBefore(LocalDate.now()))
            throw new RuntimeException("Перевірте чи день народження не в майбутньому");
        if (course <= 0 || course > 7) throw new RuntimeException("Значення курсу має бути в межах від 1 до 7");
        Student student = new Student(name, surname, fatherName, birthDate, address, phone, faculty, course, group);
        studentsService.addStudent(student);
        goToMainScene();
    }

    public void goToMainScene() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/mainScene.fxml"));
        Stage stage = (Stage) nameTF.getScene().getWindow();
        stage.close();
        stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
