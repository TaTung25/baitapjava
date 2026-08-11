/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap;

/**
 *
 * @author 56745654242453456656
 */
public class tinhdiem {

    public static double tinhDiem(hsinh sinh) {
        return sinh.getDiemCC() * 0.1 + sinh.getDiemGK() * 0.3 + sinh.getDiemCK() * 0.6;
    }

    public static String xepLoai(double diem) {
        if (diem >= 8.5) {
            return "A";
        }

        if (diem >= 7.0) {
            return "B";
        }

        if (diem >= 5.5) {
            return "C";
        }

        if (diem >= 4.0) {
            return "D";
        }

        return "F";
    }
}