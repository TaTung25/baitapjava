<%-- 
    Document   : index
    Created on : Sep 8, 2026, 9:43:19 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Công Ty TNT - Hệ Thống Quản Lý Kho Hàng Máy Tính</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3 shadow-sm">
            <div class="container">
                <a class="navbar-brand fw-bold text-primary fs-4" href="${pageContext.request.contextPath}/">
                    <i class="bi bi-box-seam-fill me-2"></i>TNT KHO HANG
                </a>
                <div class="d-flex align-items-center">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-warning fw-bold">
                                <i class="bi bi-speedometer2 me-1"></i>Vào Dashboard
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary fw-bold px-4">
                                <i class="bi bi-box-arrow-in-right me-1"></i>Đăng Nhập
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>

        <section class="py-5 bg-white border-bottom">
            <div class="container py-4">
                <div class="row align-items-center">
                    <div class="col-lg-6">
                        <span class="badge bg-primary-subtle text-primary mb-2 px-3 py-2 fw-semibold">Phần Mềm Quản Lý Kho Chuyên Nghiệp</span>
                        <h1 class="display-5 fw-bold mb-3">Hệ Thống Kho Máy Tính TNT</h1>
                        <p class="lead text-muted mb-4">Giải pháp quản lý tự động kho hàng, kiểm soát tồn kho đơn giản hóa !</p>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary btn-lg fw-bold">Truy Cập Hệ Thống</a>
                    </div>
                    <div class="col-lg-6 text-center mt-4 mt-lg-0">
                        <img src="https://images.unsplash.com/photo-1586528116311-ad8dd3c8310d?auto=format&fit=crop&w=800&q=80" class="img-fluid rounded-4 shadow" alt="Kho TNT">
                    </div>
                </div>
            </div>
        </section>

        <footer class="bg-dark text-white py-4 mt-auto">
            <div class="container text-center">
                <p class="mb-0 small">HIHI TNT XIN CHAO</p>
            </div>
        </footer>
    </body>
</html>