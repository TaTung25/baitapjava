/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.SanPhamDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.SanPham;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    @Inject
    private SanPhamDAO sanPhamDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SanPham> listSP = sanPhamDAO.getAllSanPham();

        int totalProducts = listSP.size();
        int totalStock = listSP.stream().mapToInt(SanPham::getSoLuong).sum();
        List<SanPham> lowStockList = listSP.stream()
                .filter(sp -> sp.getSoLuong() <= 5)
                .collect(Collectors.toList());

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalStock", totalStock);
        request.setAttribute("lowStockCount", lowStockList.size());
        request.setAttribute("lowStockList", lowStockList);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
