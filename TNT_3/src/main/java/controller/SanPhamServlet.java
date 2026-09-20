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
                // Lọc + tìm kiếm nâng cao
                String tuKhoa = request.getParameter("tuKhoa");
                if (tuKhoa == null) {
                    tuKhoa = request.getParameter("keyword"); // tương thích link cũ
                }
                int maLoai = docSo(request.getParameter("maLoai"));
                int maNCC = docSo(request.getParameter("maNCC"));
                String tinhTrang = request.getParameter("tinhTrang");
                String sapXep = request.getParameter("sapXep");

                request.setAttribute("listSP",
                        sanPhamDAO.locSanPham(tuKhoa, maLoai, maNCC, tinhTrang, sapXep));
                request.setAttribute("tuKhoa", tuKhoa == null ? "" : tuKhoa);
                request.setAttribute("selectedLoai", maLoai);
                request.setAttribute("selectedNCC", maNCC);
                request.setAttribute("selectedTinhTrang", tinhTrang == null ? "" : tinhTrang);
                request.setAttribute("selectedSapXep", sapXep == null ? "moinhat" : sapXep);
                request.setAttribute("dangLoc", true);
                break;

            case "list":
            default:
                request.setAttribute("listSP", sanPhamDAO.getAllSanPham());
                request.setAttribute("tuKhoa", "");
                request.setAttribute("selectedLoai", 0);
                request.setAttribute("selectedNCC", 0);
                request.setAttribute("selectedTinhTrang", "");
                request.setAttribute("selectedSapXep", "moinhat");
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

    /** Đọc tham số số nguyên, trả về 0 nếu rỗng hoặc sai định dạng. */
    private int docSo(String giaTri) {
        if (giaTri == null || giaTri.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(giaTri.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
