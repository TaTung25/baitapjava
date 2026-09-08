<%-- 
    Document   : sidebar
    Created on : Sep 8, 2026, 7:27:24 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="list-group shadow-sm mb-4 sidebar-menu">
    <a href="${pageContext.request.contextPath}/DashboardServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-speedometer2 me-2"></i>Dashboard
    </a>
    <a href="${pageContext.request.contextPath}/SanPhamServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-laptop me-2"></i>Quản lý Sản phẩm
    </a>
    <a href="${pageContext.request.contextPath}/LoaiSanPhamServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-tags me-2"></i>Loại Sản phẩm
    </a>
    <a href="${pageContext.request.contextPath}/NhaCungCapServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-truck me-2"></i>Nhà cung cấp
    </a>
    <a href="${pageContext.request.contextPath}/KhachHangServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-people me-2"></i>Khách hàng
    </a>

    <div class="list-group-item text-muted fw-bold small text-uppercase bg-light">Nghiệp vụ Kho</div>
    <a href="${pageContext.request.contextPath}/PhieuNhapServlet?action=create" class="list-group-item list-group-item-action">
        <i class="bi bi-arrow-down-right-square me-2 text-success"></i>Tạo Phiếu Nhập
    </a>
    <a href="${pageContext.request.contextPath}/PhieuNhapServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-journal-check me-2"></i>Lịch sử Nhập kho
    </a>
    <a href="${pageContext.request.contextPath}/PhieuXuatServlet?action=create" class="list-group-item list-group-item-action">
        <i class="bi bi-arrow-up-right-square me-2 text-danger"></i>Tạo Phiếu Xuất
    </a>
    <a href="${pageContext.request.contextPath}/PhieuXuatServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-journal-minus me-2"></i>Lịch sử Xuất kho
    </a>
    <a href="${pageContext.request.contextPath}/TonKhoServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-box-seam me-2"></i>Báo cáo Tồn kho
    </a>

    <div class="list-group-item text-muted fw-bold small text-uppercase bg-light">Hệ thống</div>
    <a href="${pageContext.request.contextPath}/BaoCaoServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-graph-up me-2"></i>Thống kê - Báo cáo
    </a>
    <c:if test="${sessionScope.user.quyen == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/NguoiDungServlet" class="list-group-item list-group-item-action">
            <i class="bi bi-shield-lock me-2 text-warning"></i>Quản lý Tài khoản
        </a>
    </c:if>
    <a href="${pageContext.request.contextPath}/ProfileServlet" class="list-group-item list-group-item-action">
        <i class="bi bi-person-gear me-2"></i>Thông tin Cá nhân
    </a>
</div>