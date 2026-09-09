/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.vd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.vd.nn.Student;

import java.util.List;

@Controller
public class StudentController {

    @GetMapping("/students")
    public String listStudents(Model model) {

        List<Student> students = List.of(
                new Student(
                        "01",
                        "Hihi",
                        "hi@gmail",
                        "10"
                ),
                new Student(
                        "02",
                        "Haha",
                        "ha@gmail",
                        "11"
                ),
                new Student(
                        "03",
                        "Huhu",
                        "hu@gmail",
                        "12"
                )
        );

        model.addAttribute("students", students);

        return "students";
    }
}
