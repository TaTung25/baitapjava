/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.KhachHangDAO;
import dao.PhieuXuatDAO;
import dao.SanPhamDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PhieuXuatServlet")
public class PhieuXuatServlet extends HttpServlet {

    @Inject
    private PhieuXuatDAO phieuXuatDAO;

    @Inject
    private KhachHangDAO khachHangDAO;

    @Inject
    private SanPhamDAO sanPhamDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        if ("create".equals(action)) {
            request.setAttribute("listKH", khachHangDAO.getAllKhachHang());
            request.setAttribute("listSP", sanPhamDAO.getAllSanPham());
            request.getRequestDispatcher("xuatkho/create.jsp").forward(request, response);
        } else if ("detail".equals(action)) {
            int maPX = Integer.parseInt(request.getParameter("id"));
            PhieuXuat px = phieuXuatDAO.getPhieuXuatById(maPX);
            List<ChiTietPhieuXuat> listCT = phieuXuatDAO.getChiTietPhieuXuatByMaPX(maPX);
            request.setAttribute("phieuXuat", px);
            request.setAttribute("listChiTiet", listCT);
            request.getRequestDispatcher("xuatkho/detail.jsp").forward(request, response);
        } else {
            // Lấy tất cả lịch sử phiếu xuất kho
            request.setAttribute("listPX", phieuXuatDAO.getAllPhieuXuat());
            request.getRequestDispatcher("xuatkho/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        NguoiDung user = (NguoiDung) session.getAttribute("user");

        try {
            int maKH = Integer.parseInt(request.getParameter("maKH"));
            String ghiChu = request.getParameter("ghiChu");

            String[] maSPs = request.getParameterValues("maSP");
            String[] soLuongs = request.getParameterValues("soLuong");
            String[] donGias = request.getParameterValues("donGia");

            double tongTien = 0;
            List<ChiTietPhieuXuat> listCT = new ArrayList<>();

            if (maSPs != null) {
                for (int i = 0; i < maSPs.length; i++) {
                    int maSP = Integer.parseInt(maSPs[i]);
                    int sl = Integer.parseInt(soLuongs[i]);
                    double gia = Double.parseDouble(donGias[i]);
                    double thanhTien = sl * gia;
                    tongTien += thanhTien;

                    ChiTietPhieuXuat ct = new ChiTietPhieuXuat();
                    ct.setMaSP(maSP);
                    ct.setSoLuong(sl);
                    ct.setDonGia(gia);
                    ct.setThanhTien(thanhTien);
                    listCT.add(ct);
                }
            }

            PhieuXuat px = new PhieuXuat();
            px.setMaKH(maKH);
            px.setMaNguoiDung(user.getMaNguoiDung());
            px.setTongTien(tongTien);
            px.setGhiChu(ghiChu);

            phieuXuatDAO.savePhieuXuat(px, listCT);
            response.sendRedirect("PhieuXuatServlet");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("listKH", khachHangDAO.getAllKhachHang());
            request.setAttribute("listSP", sanPhamDAO.getAllSanPham());
            request.getRequestDispatcher("xuatkho/create.jsp").forward(request, response);
        }
    }
}
