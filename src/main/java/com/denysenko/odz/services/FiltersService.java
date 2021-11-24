package com.denysenko.odz.services;

import com.denysenko.odz.models.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FiltersService {
    public static Set<String> getAvailableFaculties(List<Student> students) {
        Set<String> faculties = new HashSet<>();
        faculties.add("Всі");
        students.forEach(student -> {
            faculties.add(student.getFaculty());
        });
        return faculties;
    }

    public static Set<String> getAvailableCourses(List<Student> students) {
        Set<String> courses = new HashSet<>();
        courses.add("Всі");
        students.forEach(student -> {
            courses.add(String.valueOf(student.getCourse()));
        });
        return courses;
    }

    public static Set<String> getAvailableGroups(List<Student> students) {
        Set<String> groups = new HashSet<>();
        groups.add("Всі");
        students.forEach(student -> {
            groups.add(student.getGroup());
        });
        return groups;
    }


}
