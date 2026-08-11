/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap;

/**
 *
 * @author 56745654242453456656
 */
public class hsinh {
    private String maSV;
    private String hoTen;
    private double diemCC;
    private double diemGK;
    private double diemCK;

    public hsinh(String maSV, String hoTen, double diemCC, double diemGK, double diemCK) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diemCC = diemCC;
        this.diemGK = diemGK;
        this.diemCK = diemCK;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public double getDiemCC() {
        return diemCC;
    }

    public double getDiemGK() {
        return diemGK;
    }

    public double getDiemCK() {
        return diemCK;
    }
}