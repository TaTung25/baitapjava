<%-- 
    Document   : list
    Created on : Sep 8, 2026, 7:30:40 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid py-4 px-4">
    <div class="row g-4">
        <div class="col-lg-2">
            <%@ include file="../includes/sidebar.jsp" %>
        </div>
        <div class="col-lg-10">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold mb-0">Lịch Sử Tất Cả Phiếu Xuất Kho</h3>
                <a href="PhieuXuatServlet?action=create" class="btn btn-danger fw-bold">+ Tạo Phiếu Xuất Mới</a>
            </div>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã PX</th>
                                <th>Khách Hàng</th>
                                <th>Người Lập Phiếu</th>
                                <th>Ngày Xuất</th>
                                <th>Tổng Doanh Thu</th>
                                <th>Ghi Chú</th>
                                <th class="text-center">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listPX}" var="px">
                                <tr>
                                    <td class="ps-3 fw-semibold">#PX${px.maPX}</td>
                                    <td class="fw-bold text-primary">${px.tenKH}</td>
                                    <td>${px.tenNguoiDung}</td>
                                    <td><fmt:formatDate value="${px.ngayXuat}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td class="fw-bold text-success">${px.tongTien} VNĐ</td>
                                    <td>${px.ghiChu}</td>
                                    <td class="text-center">
                                        <a href="PhieuXuatServlet?action=detail&id=${px.maPX}" class="btn btn-sm btn-outline-info">
                                            <i class="bi bi-eye-fill me-1"></i> Chi Tiết
                                        </a>
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