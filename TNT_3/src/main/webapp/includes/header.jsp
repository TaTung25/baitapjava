<%-- 
    Document   : header
    Created on : Sep 8, 2026, 7:25:15 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hệ Thống Quản Lý Kho TNT</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/assets/js/app.js" defer></script>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-2">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold text-primary" href="${pageContext.request.contextPath}/DashboardServlet">
                    <i class="bi bi-box-seam-fill me-2"></i>TNT KHO HANG
                </a>
                <div class="d-flex text-white align-items-center">
                    <span class="me-3 small"><i class="bi bi-person-circle me-1"></i> ${sessionScope.user.hoTen} (<span class="badge bg-warning text-dark">${sessionScope.user.quyen}</span>)</span>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-danger btn-sm"><i class="bi bi-box-arrow-right me-1"></i>Đăng xuất</a>
                </div>
            </div>
        </nav>