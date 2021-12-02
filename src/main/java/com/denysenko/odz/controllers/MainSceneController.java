package com.denysenko.odz.controllers;

import com.denysenko.odz.services.ViewHandler;
import com.denysenko.odz.models.Student;
import com.denysenko.odz.services.FiltersService;
import com.denysenko.odz.services.StudentsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    private StudentsService studentsService = new StudentsService();
    private FiltersService filtersService = new FiltersService();

    @FXML
    private TableView<Student> tableStudents;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> surnameColumn;
    @FXML
    private TableColumn<Student, String> fatherNameColumn;
    @FXML
    private TableColumn<Student, LocalDate> birthDateColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;
    @FXML
    private TableColumn<Student, String> facultyColumn;
    @FXML
    private TableColumn<Student, Integer> courseColumn;
    @FXML
    private TableColumn<Student, String> groupColumn;
    @FXML
    private ChoiceBox<String> facultyChBox;
    @FXML
    private ChoiceBox<String> groupChBox;
    @FXML
    private ChoiceBox<String> courseChBox;
    @FXML
    private TextField minBirthYear;
    @FXML
    private AnchorPane mainPain;

    @Override
    public void initialize(URL location, ResourceBundle resources) throws RuntimeException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newName = event.getNewValue().trim();
            if (newName != null && !newName.isEmpty() && newName.matches("^[а-яА-ЯёЁa-zA-ZЇїІіЄєҐґ]+$")) {
                student.setName(newName);
                studentsService.updateStudent(student);
            } else throw new RuntimeException("Поле порожнє або його значення не схоже на ім'я");
        });

        surnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newSurname = event.getNewValue().trim();
            if (newSurname != null && !newSurname.isEmpty() && newSurname.matches("^[а-яА-ЯёЁa-zA-ZЇїІіЄєҐґ]+$")) {
                student.setSurname(newSurname);
                studentsService.updateStudent(student);
            } else throw new RuntimeException("Поле порожнє або його значення не схоже на прізвище");

        });

        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("fatherName"));
        fatherNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fatherNameColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newFatherName = event.getNewValue().trim();
            if (newFatherName != null && !newFatherName.isEmpty() && newFatherName.matches("^[а-яА-ЯёЁa-zA-ZЇїІіЄєҐґ]+$")) {
                student.setFatherName(newFatherName);
                studentsService.updateStudent(student);
            } else if (newFatherName != null && !newFatherName.isEmpty()) {
                throw new RuntimeException("Нове значення не збережено! Рядок не схожий на по-батькові");
            }
        });

        birthDateColumn.setCellValueFactory(new PropertyValueFactory<Student, LocalDate>("birthDate"));
        birthDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(DateTimeFormatter.ofPattern("dd-MM-yyyy"), null)));
        birthDateColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            LocalDate dateOfBirth = event.getNewValue();
            if (dateOfBirth != null && dateOfBirth.isBefore(LocalDate.now())) {
                student.setBirthDate(dateOfBirth);
                studentsService.updateStudent(student);
            } else if (dateOfBirth != null) {
                throw new RuntimeException("Нове значення не збережено! Перевірте чи цей день не в майбутньому");
            } else throw new RuntimeException("Це поле є обов'язковим!");
        });

        addressColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newAddress = event.getNewValue().trim();
            if (newAddress != null && !newAddress.isEmpty())
                student.setAddress(newAddress);
            studentsService.updateStudent(student);
        });

        phoneColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("phone"));
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newPhone = event.getNewValue().trim();
            if (newPhone != null && !newPhone.isEmpty() && newPhone.matches("[\\+]?\\d*(\\(\\d{3}\\))?\\d*\\-?\\d*\\-?\\d*\\d$")) {
                student.setPhone(newPhone);
                studentsService.updateStudent(student);
            } else if (newPhone != null && !newPhone.isEmpty()) {
                throw new RuntimeException("Нове значення не збережено! Значення не схоже на номер телефону");
            }
        });


        facultyColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("faculty"));
        facultyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        facultyColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newFaculty = event.getNewValue().trim();
            if (newFaculty != null && !newFaculty.isEmpty()) {
                facultyChBox.getItems().remove(event.getOldValue());
                facultyChBox.getItems().add(newFaculty);
                student.setFaculty(newFaculty);
                studentsService.updateStudent(student);
            } else throw new RuntimeException("Нове значення не збережено! Це поле є обов'язковим!");
        });

        courseColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("course"));
        courseColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        courseColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            Integer newCourse = event.getNewValue();
            if (newCourse == null) throw new NumberFormatException();
            if (newCourse > 0 && newCourse < 8) {
                courseChBox.getItems().remove(event.getOldValue());
                courseChBox.getItems().add(newCourse.toString());
                student.setCourse(newCourse);
                studentsService.updateStudent(student);
            } else throw new RuntimeException("Значення курсу має бути в межах від 1 до 7");
        });

        groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        groupColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        groupColumn.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            String newGroup = event.getNewValue().trim();
            if (newGroup != null && !newGroup.isEmpty()) {
                groupChBox.getItems().remove(event.getOldValue());
                groupChBox.getItems().add(newGroup);
                student.setGroup(newGroup);
                studentsService.updateStudent(student);
            } else throw new RuntimeException("Нове значення не збережено! Поле є обов'язковим!");
        });

        List<Student> students = studentsService.getAllStudents();
        tableStudents.setItems(FXCollections.observableArrayList(students));
        facultyChBox.setItems(FXCollections.observableArrayList(filtersService.getAvailableFaculties(students)));
        groupChBox.setItems(FXCollections.observableArrayList(filtersService.getAvailableGroups(students)));
        courseChBox.setItems(FXCollections.observableArrayList(filtersService.getAvailableCourses(students)));
        facultyChBox.getSelectionModel().select("Всі");
        groupChBox.getSelectionModel().select("Всі");
        courseChBox.getSelectionModel().select("Всі");
        minBirthYear.setText("0");
        tableStudents.setEditable(true);
        ViewHandler.autoResizeColumns(tableStudents);
    }

    public void applyFilters() {
        String wishedFaculty = facultyChBox.getValue();
        String wishedGroup = groupChBox.getValue();
        String wishedCourse = courseChBox.getValue();
        int wishedMinYearOfBirth;
        try {
            wishedMinYearOfBirth = Integer.valueOf(minBirthYear.getText());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Значення мінімального року повинне бути числовим!");
        }

        tableStudents.getItems().clear();
        List<Student> students = studentsService.getStudentsByFilters(wishedGroup, wishedFaculty, wishedCourse, wishedMinYearOfBirth);
        tableStudents.setItems(FXCollections.observableArrayList(students));
    }

    public void deleteStudent() {
        Student student = tableStudents.getSelectionModel().getSelectedItem();
        if (student == null) throw new RuntimeException("Оберіть студента!");
        facultyChBox.getItems().remove(student.getFaculty());
        groupChBox.getItems().remove(student.getGroup());
        courseChBox.getItems().remove(String.valueOf(student.getCourse()));
        studentsService.deleteStudent(student);
        tableStudents.getItems().remove(student);
    }

    public void goToAddScene() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/addScene.fxml"));
        Stage stage = (Stage) mainPain.getScene().getWindow();
        stage.close();
        stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
