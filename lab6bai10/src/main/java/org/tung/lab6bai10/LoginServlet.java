/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.tung.lab6bai10;

/**
 *
 * @author 56745654242453456656
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Kiểm tra tài khoản (Đăng nhập mẫu với user: admin / pass: 123456)
        if ("admin".equals(user) && "2".equals(pass)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user);

            // Thời gian đăng nhập
            String loginTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            session.setAttribute("loginTime", loginTime);

            response.sendRedirect("dashboard");
        } else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}
