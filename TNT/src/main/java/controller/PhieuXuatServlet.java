/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.KhachHangDAO;
import dao.PhieuXuatDAO;
import dao.SanPhamDAO;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PhieuXuatServlet")
public class PhieuXuatServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            request.setAttribute("listKH", new KhachHangDAO().getAllKhachHang());
            request.setAttribute("listSP", new SanPhamDAO().getAllSanPham());
            request.getRequestDispatcher("xuatkho/create.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("xuatkho/list.jsp").forward(request, response);
        }
    }

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

            PhieuXuat px = new PhieuXuat();
            px.setMaKH(maKH);
            px.setMaNguoiDung(user.getMaNguoiDung());
            px.setTongTien(tongTien);
            px.setGhiChu(ghiChu);

            PhieuXuatDAO dao = new PhieuXuatDAO();
            dao.savePhieuXuat(px, listCT);
            response.sendRedirect("DashboardServlet?msg=XuatKhoSuccess");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("listKH", new KhachHangDAO().getAllKhachHang());
            request.setAttribute("listSP", new SanPhamDAO().getAllSanPham());
            request.getRequestDispatcher("xuatkho/create.jsp").forward(request, response);
        }
    }
}
