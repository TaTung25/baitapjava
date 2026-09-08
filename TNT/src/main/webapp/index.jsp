<%-- 
    Document   : index
    Created on : Sep 8, 2026, 9:43:19 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Công Ty TNT - Hệ Thống Quản Lý Kho Hàng Máy Tính</title>
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <!-- Header Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-3">
            <div class="container">
                <a class="navbar-brand fw-bold fs-4 text-primary" href="${pageContext.request.contextPath}/">
                    <i class="bi bi-box-seam-fill me-2"></i>TNT LOGISTICS
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto align-items-center">
                        <li class="nav-item me-3">
                            <a class="nav-link active fw-semibold" href="${pageContext.request.contextPath}/">Trang Chủ</a>
                        </li>
                        <li class="nav-item me-3">
                            <a class="nav-link fw-semibold" href="#dichvu">Dịch Vụ Kho</a>
                        </li>
                        <li class="nav-item me-3">
                            <a class="nav-link fw-semibold" href="#lienhe">Lên Hệ</a>
                        </li>
                        <li class="nav-item">
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
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Hero Section -->
        <section class="py-5 bg-white border-bottom">
            <div class="container py-4">
                <div class="row align-items-center">
                    <div class="col-lg-6">
                        <span class="badge bg-primary-subtle text-primary mb-2 px-3 py-2 fw-semibold">Giải Pháp Quản Lý Kho Thông Minh</span>
                        <h1 class="display-5 fw-bold mb-3 text-dark">Hệ Thống Quản Lý Kho Máy Tính TNT</h1>
                        <p class="lead text-muted mb-4">Giải pháp tối ưu theo dõi số lượng tồn kho, kiểm soát phiếu nhập - xuất kho, cảnh báo hàng sắp hết và thống kê doanh thu máy tính chính xác, thời gian thực.</p>
                        <div class="d-flex gap-3">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-primary btn-lg fw-bold">Truy Cập Hệ Thống</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary btn-lg fw-bold">
                                        <i class="bi bi-shield-lock me-1"></i>Đăng Nhập Ngay
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-lg-6 text-center mt-4 mt-lg-0">
                        <img src="https://images.unsplash.com/photo-1586528116311-ad8dd3c8310d?auto=format&fit=crop&w=800&q=80" class="img-fluid rounded-4 shadow-lg" alt="Kho hàng máy tính TNT">
                    </div>
                </div>
            </div>
        </section>

        <!-- Features Section -->
        <section id="dichvu" class="py-5">
            <div class="container py-4">
                <div class="text-center mb-5">
                    <h2 class="fw-bold">Chức Năng Nổi Bật Hệ Thống</h2>
                    <p class="text-muted">Được thiết kế riêng cho mô hình quản lý phân phối máy tính công ty TNT</p>
                </div>
                <div class="row g-4">
                    <div class="col-md-4">
                        <div class="card h-100 border-0 shadow-sm p-3 text-center">
                            <div class="card-body">
                                <i class="bi bi-laptop text-primary display-4 mb-3"></i>
                                <h5 class="fw-bold">Quản Lý Sản Phẩm</h5>
                                <p class="text-muted small">Phân loại máy tính, cập nhật giá nhập, giá bán và cấu hình kỹ thuật chi tiết.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 border-0 shadow-sm p-3 text-center">
                            <div class="card-body">
                                <i class="bi bi-arrow-left-right text-success display-4 mb-3"></i>
                                <h5 class="fw-bold">Nhập / Xuất Kho Tự Động</h5>
                                <p class="text-muted small">Kiểm soát giao dịch với Nhà cung cấp & Khách hàng, tự động cập nhật số lượng tồn kho.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 border-0 shadow-sm p-3 text-center">
                            <div class="card-body">
                                <i class="bi bi-graph-up-arrow text-danger display-4 mb-3"></i>
                                <h5 class="fw-bold">Cảnh Báo & Báo Cáo</h5>
                                <p class="text-muted small">Biểu đồ trực quan, chủ động cảnh báo khi tồn kho máy tính giảm dưới ngưỡng an toàn.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <footer id="lienhe" class="bg-dark text-white py-4 mt-auto">
            <div class="container text-center">
                <p class="mb-1 fw-bold">CÔNG TY QUẢN LÝ KHO HÀNG MÁY TÍNH TNT</p>
                <small class="text-secondary">&copy; 2026 TNT Company. All rights reserved.</small>
            </div>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>