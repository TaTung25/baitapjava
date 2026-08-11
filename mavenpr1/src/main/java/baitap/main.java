/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap;
import java.util.*;
/**
 *
 * @author 56745654242453456656
 */
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma sinh vien: ");
        String maSV = sc.nextLine();
        System.out.print("Nhap ho ten: ");
        String hoTen = sc.nextLine();
        System.out.print("Nhap diem chuyen can: ");
        double diemCC = sc.nextDouble();
        System.out.print("Nhap diem giua ky: ");
        double diemGK = sc.nextDouble();
        System.out.print("Nhap diem cuoi ky: ");
        double diemCK = sc.nextDouble();

        hsinh sv = new hsinh(maSV, hoTen, diemCC, diemGK, diemCK);

        double diemTongKet = tinhdiem.tinhDiem(sv);
        System.out.printf("| %-15s | %-25s | %-10s | %-15s |%n ", "Ma sv", "Ho ten", "Diem", "xep loai");
        System.out.printf("| %-15s | %-25s | %-10s | %-15s |%n ", getmaSV, "Ho ten", "Diem", "xep loai");
        System.out.println("Diem tong ket: " + diemTongKet);
        System.out.println("Xep loai: " + tinhdiem.xepLoai(diemTongKet));
    }
}
