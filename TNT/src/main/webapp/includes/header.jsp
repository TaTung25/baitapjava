<%-- 
    Document   : header
    Created on : Sep 8, 2026, 7:25:15 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản Lý Kho Hàng Máy Tính TNT</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand fw-bold" href="DashboardServlet">KHO TNT</a>
                <div class="d-flex text-white align-items-center">
                    <span class="me-3"><i class="bi bi-person-circle"></i> ${sessionScope.user.hoTen} (${sessionScope.user.quyen})</span>
                    <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Đăng xuất</a>
                </div>
            </div>
        </nav>
