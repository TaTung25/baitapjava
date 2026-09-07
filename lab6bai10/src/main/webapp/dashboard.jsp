<%-- 
    Document   : dashboard
    Created on : Sep 3, 2026, 10:02:35 AM
    Author     : 56745654242453456656
--%>

<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dashboard Quản Lý</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f6f9;
            }
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .card {
                background: white;
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            .btn-manage {
                display: inline-block;
                padding: 10px 20px;
                background-color: #28a745;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
            }
            .btn-manage:hover {
                background-color: #218838;
            }
            .btn-logout {
                background-color: #dc3545;
                color: white;
                padding: 8px 15px;
                text-decoration: none;
                border-radius: 4px;
                font-weight: bold;
            }
            .btn-logout:hover {
                background-color: #c82333;
            }
        </style>
    </head>
    <body>

        <div class="card header">
            <div>
                <h2>Xin chào, ${sessionScope.username}!</h2>
                <p><strong>Thời gian đăng nhập:</strong> ${sessionScope.loginTime}</p>
            </div>
            <div>
                <a href="logout" class="btn-logout">Đăng xuất</a>
            </div>
        </div>

        <div class="card">
            <h3>Thống kê sinh viên</h3>
            <p><strong>Tổng số sinh viên:</strong> <span style="font-size: 18px; color: #007bff; font-weight: bold;">${totalStudents}</span></p>

            <h4>Số sinh viên theo từng lớp:</h4>
            <table>
                <thead>
                    <tr>
                        <th>Tên lớp</th>
                        <th>Số sinh viên</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${studentsPerClass}">
                        <tr>
                            <td>${entry.key}</td>
                            <td>${entry.value}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="card">
            <a href="StudentManagementServlet" class="btn-manage">Đi đến trang Quản lý Sinh viên</a>
        </div>

    </body>
</html>
