package org.tung.lab6bai4_login;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 56745654242453456656
 */

import org.tung.lab6bai4_login.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentStore {

    private static final List<Student> students = new ArrayList<>();

    static {
        students.add(new Student(
                "SV001",
                "Nguyen Van An",
                "DCCNTT12",
                "an@example.com"
        ));

        students.add(new Student(
                "SV002",
                "Tran Thi Binh",
                "DCCNTT12",
                "binh@example.com"
        ));
    }

    public static List<Student> findAll() {
        return students;
    }

    public static void add(Student student) {
        students.add(student);
    }
}

