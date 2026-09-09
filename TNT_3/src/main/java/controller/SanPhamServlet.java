/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.LoaiSanPhamDAO;
import dao.NhaCungCapDAO;
import dao.SanPhamDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.SanPham;

import java.io.IOException;

@WebServlet("/SanPhamServlet")
public class SanPhamServlet extends HttpServlet {

    @Inject
    private SanPhamDAO sanPhamDAO;

    @Inject
    private LoaiSanPhamDAO loaiSanPhamDAO;

    @Inject
    private NhaCungCapDAO nhaCungCapDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                sanPhamDAO.deleteSanPham(deleteId);
                response.sendRedirect("SanPhamServlet");
                return;

            case "search":
                String keyword = request.getParameter("keyword");
                int maLoai = 0;
                if (request.getParameter("maLoai") != null && !request.getParameter("maLoai").isEmpty()) {
                    maLoai = Integer.parseInt(request.getParameter("maLoai"));
                }
                request.setAttribute("listSP", sanPhamDAO.searchSanPham(keyword, maLoai));
                request.setAttribute("keyword", keyword);
                request.setAttribute("selectedLoai", maLoai);
                break;

            case "list":
            default:
                request.setAttribute("listSP", sanPhamDAO.getAllSanPham());
                break;
        }

        request.setAttribute("listLoai", loaiSanPhamDAO.getAllLoai());
        request.setAttribute("listNCC", nhaCungCapDAO.getAllNCC());
        request.getRequestDispatcher("sanpham/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String maSPStr = request.getParameter("maSP");
        SanPham sp = new SanPham();
        sp.setTenSP(request.getParameter("tenSP"));
        sp.setMaLoai(Integer.parseInt(request.getParameter("maLoai")));
        sp.setMaNCC(Integer.parseInt(request.getParameter("maNCC")));
        sp.setHangSX(request.getParameter("hangSX"));
        sp.setDonViTinh(request.getParameter("donViTinh"));
        sp.setDonGiaNhap(Double.parseDouble(request.getParameter("donGiaNhap")));
        sp.setDonGiaBan(Double.parseDouble(request.getParameter("donGiaBan")));
        sp.setSoLuong(Integer.parseInt(request.getParameter("soLuong")));
        sp.setBaoHanh(Integer.parseInt(request.getParameter("baoHanh")));
        sp.setMoTa(request.getParameter("moTa"));

        if (maSPStr != null && !maSPStr.trim().isEmpty()) {
            // Cập nhật sản phẩm cũ
            sp.setMaSP(Integer.parseInt(maSPStr));
            sanPhamDAO.updateSanPham(sp);
        } else {
            // Thêm mới sản phẩm
            sanPhamDAO.insertSanPham(sp);
        }

        response.sendRedirect("SanPhamServlet");
    }
}
