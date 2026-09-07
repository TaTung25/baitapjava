/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tung.lab6bai10;

/**
 *
 * @author 56745654242453456656
 */

import org.tung.lab6bai10.StudentDAO;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra Session đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy dữ liệu từ DAO
        int totalStudents = studentDAO.getTotalStudents();
        Map<String, Integer> studentsPerClass = studentDAO.getStudentsPerClass();

        // Gửi dữ liệu qua JSP
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("studentsPerClass", studentsPerClass);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}