<%-- 
    Document   : dashboard
    Created on : Sep 8, 2026, 7:28:23 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jsp" %>

<div class="container-fluid py-4 px-4">
    <div class="row g-4">
        <!-- Sidebar Navigation -->
        <div class="col-lg-2">
            <%@ include file="includes/sidebar.jsp" %>
        </div>

        <!-- Main Workspace -->
        <div class="col-lg-10">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h3 class="fw-bold mb-1">Tổng Quan Kho Hàng</h3>
                    <p class="text-muted small mb-0">Thống kê dữ liệu thời gian thực Công ty TNT</p>
                </div>
            </div>

            <!-- Stats Metric Cards -->
            <div class="row g-3 mb-4">
                <div class="col-md-4">
                    <div class="card card-metric p-3">
                        <div class="d-flex align-items-center justify-content-between">
                            <div>
                                <span class="text-muted small fw-semibold">TỔNG MẶT HÀNG</span>
                                <h2 class="fw-bold my-1">${totalProducts}</h2>
                                <small class="text-success fw-semibold"><i class="bi bi-arrow-up"></i> Đang quản lý</small>
                            </div>
                            <div class="metric-icon bg-primary-subtle text-primary">
                                <i class="bi bi-laptop"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card card-metric p-3">
                        <div class="d-flex align-items-center justify-content-between">
                            <div>
                                <span class="text-muted small fw-semibold">TỔNG TỒN KHO</span>
                                <h2 class="fw-bold my-1">${totalStock}</h2>
                                <small class="text-primary fw-semibold">Chiếc trong kho</small>
                            </div>
                            <div class="metric-icon bg-success-subtle text-success">
                                <i class="bi bi-box-seam"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card card-metric p-3">
                        <div class="d-flex align-items-center justify-content-between">
                            <div>
                                <span class="text-muted small fw-semibold">CẢNH BÁO TỒN THẤP</span>
                                <h2 class="fw-bold my-1 text-danger">${lowStockCount}</h2>
                                <small class="text-danger fw-semibold"><i class="bi bi-exclamation-triangle"></i> Cần nhập thêm</small>
                            </div>
                            <div class="metric-icon bg-danger-subtle text-danger">
                                <i class="bi bi-graph-down-arrow"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Low Stock Warning Table -->
            <div class="card table-modern border-0">
                <div class="card-header bg-white py-3 border-0 d-flex align-items-center justify-content-between">
                    <h5 class="fw-bold mb-0 text-danger"><i class="bi bi-bell-fill me-2"></i>Sản Phẩm Cần Bổ Sung Tồn Kho (<= 5)</h5>
                    <a href="PhieuNhapServlet?action=create" class="btn btn-sm btn-primary fw-semibold">+ Tạo Phiếu Nhập</a>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã SP</th>
                                <th>Tên Máy Tính</th>
                                <th>Hãng Sản Xuất</th>
                                <th>Số Lượng Tồn</th>
                                <th>Trạng Thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lowStockList}" var="sp">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${sp.maSP}</td>
                                    <td class="fw-bold">${sp.tenSP}</td>
                                    <td>${sp.hangSX}</td>
                                    <td><span class="badge bg-danger-subtle text-danger fs-6 badge-stock">${sp.soLuong}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sp.soLuong == 0}">
                                                <span class="badge bg-dark text-white">Hết hàng</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning-subtle text-warning-emphasis">Sắp hết hàng</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>