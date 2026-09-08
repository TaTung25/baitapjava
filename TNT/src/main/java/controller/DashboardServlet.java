/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.SanPhamDAO;
import model.SanPham;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SanPhamDAO dao = new SanPhamDAO();
        List<SanPham> listSP = dao.getAllSanPham();

        int totalProducts = listSP.size();
        int totalStock = listSP.stream().mapToInt(SanPham::getSoLuong).sum();

        // Logic cảnh báo tồn kho <= 5[cite: 1]
        List<SanPham> lowStockProducts = listSP.stream()
                .filter(sp -> sp.getSoLuong() <= 5)
                .collect(Collectors.toList());

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalStock", totalStock);
        request.setAttribute("lowStockCount", lowStockProducts.size());
        request.setAttribute("lowStockList", lowStockProducts);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
