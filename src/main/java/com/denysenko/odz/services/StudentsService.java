package com.denysenko.odz.services;

import com.denysenko.odz.dao.StudentsDao;
import com.denysenko.odz.models.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsService {
    private StudentsDao studentsDao = new StudentsDao();

    public List<Student> getAllStudents() {
        return studentsDao.getAll();
    }

    public void addStudent(Student student) {
        studentsDao.create(student);
    }

    public void deleteStudent(Student student) {
        studentsDao.delete(student);
    }

    public List<Student> getStudentsByFilters(String group, String faculty, String course, int minYearOfBirth) {
        List<Student> students = getAllStudents();
        List<Student> filteredStudents = students.stream().filter(student ->
                (group == null || group.equals("Всі") || group.equals(student.getGroup()))
                        && (faculty == null || faculty.equals("Всі") || faculty.equals(student.getFaculty()))
                        && (course == null || course.equals("Всі") || course.equals(String.valueOf(student.getCourse())))
                        && student.getBirthDate().getYear() >= minYearOfBirth)
                .collect(Collectors.toList());
        return filteredStudents;
    }

    public void updateStudent(Student student) {
        studentsDao.update(student);
    }
}
