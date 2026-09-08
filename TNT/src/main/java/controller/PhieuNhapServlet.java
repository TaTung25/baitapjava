/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NhaCungCapDAO;
import dao.PhieuNhapDAO;
import dao.SanPhamDAO;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PhieuNhapServlet")
public class PhieuNhapServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            request.setAttribute("listNCC", new NhaCungCapDAO().getAllNCC());
            request.setAttribute("listSP", new SanPhamDAO().getAllSanPham());
            request.getRequestDispatcher("nhapkho/create.jsp").forward(request, response);
        } else {
            // Xem lịch sử nhập kho
            request.getRequestDispatcher("nhapkho/list.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        NguoiDung user = (NguoiDung) session.getAttribute("user");

        int maNCC = Integer.parseInt(request.getParameter("maNCC"));
        String ghiChu = request.getParameter("ghiChu");

        String[] maSPs = request.getParameterValues("maSP");
        String[] soLuongs = request.getParameterValues("soLuong");
        String[] donGias = request.getParameterValues("donGia");

        double tongTien = 0;
        List<ChiTietPhieuNhap> listCT = new ArrayList<>();

        for (int i = 0; i < maSPs.length; i++) {
            int maSP = Integer.parseInt(maSPs[i]);
            int sl = Integer.parseInt(soLuongs[i]);
            double gia = Double.parseDouble(donGias[i]);
            double thanhTien = sl * gia;
            tongTien += thanhTien;

            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
            ct.setMaSP(maSP);
            ct.setSoLuong(sl);
            ct.setDonGia(gia);
            ct.setThanhTien(thanhTien);
            listCT.add(ct);
        }

        PhieuNhap pn = new PhieuNhap();
        pn.setMaNCC(maNCC);
        pn.setMaNguoiDung(user.getMaNguoiDung());
        pn.setTongTien(tongTien);
        pn.setGhiChu(ghiChu);

        PhieuNhapDAO dao = new PhieuNhapDAO();
        if (dao.savePhieuNhap(pn, listCT)) {
            response.sendRedirect("DashboardServlet?msg=NhapKhoSuccess");
        } else {
            response.sendRedirect("PhieuNhapServlet?action=create&error=1");
        }
    }
}
