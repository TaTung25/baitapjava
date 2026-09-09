<%-- 
    Document   : sidebar
    Created on : Sep 8, 2026, 7:27:24 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="servlet" value="${pageContext.request.servletPath}" />

<div class="sidebar shadow-sm">
    <div class="nav flex-column">
        <a href="${pageContext.request.contextPath}/DashboardServlet" class="nav-link ${servlet.endsWith('DashboardServlet') ? 'active' : ''}">
            <i class="bi bi-grid-1x2-fill"></i>Dashboard
        </a>
        <a href="${pageContext.request.contextPath}/SanPhamServlet" class="nav-link ${servlet.endsWith('SanPhamServlet') ? 'active' : ''}">
            <i class="bi bi-laptop"></i>Sản Phẩm
        </a>
        <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
            <a href="${pageContext.request.contextPath}/LoaiSanPhamServlet" class="nav-link ${servlet.endsWith('LoaiSanPhamServlet') ? 'active' : ''}">
                <i class="bi bi-tags"></i>Loại Sản Phẩm
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/NhaCungCapServlet" class="nav-link ${servlet.endsWith('NhaCungCapServlet') ? 'active' : ''}">
            <i class="bi bi-truck"></i>Nhà Cung Cấp
        </a>
        <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
            <a href="${pageContext.request.contextPath}/KhachHangServlet" class="nav-link ${servlet.endsWith('KhachHangServlet') ? 'active' : ''}">
                <i class="bi bi-people"></i>Khách Hàng
            </a>
        </c:if>

        <div class="sidebar-heading">Nghiệp vụ kho</div>
        <a href="${pageContext.request.contextPath}/PhieuNhapServlet?action=create" class="nav-link">
            <i class="bi bi-arrow-down-right-square text-success"></i>Tạo Phiếu Nhập
        </a>
        <a href="${pageContext.request.contextPath}/PhieuNhapServlet" class="nav-link ${servlet.endsWith('PhieuNhapServlet') ? 'active' : ''}">
            <i class="bi bi-journal-text"></i>Lịch Sử Nhập Kho
        </a>
        <a href="${pageContext.request.contextPath}/PhieuXuatServlet?action=create" class="nav-link">
            <i class="bi bi-arrow-up-right-square text-danger"></i>Tạo Phiếu Xuất
        </a>
        <a href="${pageContext.request.contextPath}/PhieuXuatServlet" class="nav-link ${servlet.endsWith('PhieuXuatServlet') ? 'active' : ''}">
            <i class="bi bi-journal-check"></i>Lịch Sử Xuất Kho
        </a>
        <a href="${pageContext.request.contextPath}/TonKhoServlet" class="nav-link ${servlet.endsWith('TonKhoServlet') ? 'active' : ''}">
            <i class="bi bi-boxes"></i>Báo Cáo Tồn Kho
        </a>

        <div class="sidebar-heading">Hệ thống</div>
        <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
            <a href="${pageContext.request.contextPath}/BaoCaoServlet" class="nav-link ${servlet.endsWith('BaoCaoServlet') ? 'active' : ''}">
                <i class="bi bi-bar-chart-line"></i>Thống Kê
            </a>
        </c:if>

        <c:if test="${sessionScope.user.quyen == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/NguoiDungServlet" class="nav-link ${servlet.endsWith('NguoiDungServlet') ? 'active' : ''}" style="color:#fbbf24;">
                <i class="bi bi-shield-lock-fill"></i>Quản Lý Tài Khoản
            </a>
        </c:if>

        <a href="${pageContext.request.contextPath}/ProfileServlet" class="nav-link ${servlet.endsWith('ProfileServlet') ? 'active' : ''}">
            <i class="bi bi-person-gear"></i>Cá Nhân
        </a>
    </div>
</div>
