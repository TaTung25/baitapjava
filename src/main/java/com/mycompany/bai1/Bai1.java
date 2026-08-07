/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bai1;

import java.util.*;

public class Bai1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhapmasinhvien: ");
        String msv = sc.nextLine();
        System.out.print("Nhaphoten: ");
        String ht = sc.nextLine();
        System.out.print("Nhapdiemck: ");
        double ck = sc.nextDouble();
        System.out.print("Nhapdiemgk: ");
        double gk = sc.nextDouble();
        System.out.print("Nhapdiemcc: ");
        double cc = sc.nextDouble();
        
        if (ck > 10 || ck < 0 || gk > 10 || gk < 0 || cc > 10 || cc < 0) {
            System.out.print("diem ko hop le");
        }
        double dt = ck * 0.1 + gk * 0.3 + cc * 0.6;
        
        String rank;
        if (dt >= 8.5) {
            rank = "A";
        } else if (dt >= 7.0) {
            rank = "B";
        } else if (dt >= 5.5) {
            rank = "C";
        } else if (dt >= 4.0) {
            rank = "D";
        } else {
            rank = "F";
        }
        System.out.printf("Ma sv: %s | Ho ten: %s | diemtong: %.2f | xeploai: %s ", msv, ht, dt, rank );
    }
}
