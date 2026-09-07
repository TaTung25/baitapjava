/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tung.lab6bai10;

/**
 *
 * @author 56745654242453456656
 */ 

import java.util.HashMap;
import java.util.Map;

public class StudentDAO {

    public int getTotalStudents() {
        // Giả lập tổng số sinh viên
        return 120;
    }

    public Map<String, Integer> getStudentsPerClass() {
        // Giả lập dữ liệu số sinh viên theo lớp
        Map<String, Integer> map = new HashMap<>();
        map.put("CNTT K62", 45);
        map.put("KHMT K62", 40);
        map.put("HTTT K62", 35);
        return map;
    }
}