<%-- 
    Document   : detail
    Created on : Sep 9, 2026, 11:23:29 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="tnt" uri="http://tnt.com/functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid py-4 px-4">
    <div class="row g-4">
        <div class="col-lg-2">
            <%@ include file="../includes/sidebar.jsp" %>
        </div>
        <div class="col-lg-10">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="fw-bold mb-0">Chi Tiết Phiếu Xuất Kho #PX${phieuXuat.maPX}</h3>
                <a href="PhieuXuatServlet" class="btn btn-secondary fw-semibold"><i class="bi bi-arrow-left me-1"></i> Quay Lại</a>
            </div>

            <div class="card border-0 shadow-sm mb-4">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4">
                            <p class="mb-1 text-muted">Khách Hàng Nhận:</p>
                            <h5 class="fw-bold text-primary">${phieuXuat.tenKH}</h5>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1 text-muted">Người Lập Phiếu:</p>
                            <h5 class="fw-bold">${phieuXuat.tenNguoiDung}</h5>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1 text-muted">Ngày Xuất Kho:</p>
                            <h5 class="fw-bold"><fmt:formatDate value="${phieuXuat.ngayXuat}" pattern="dd/MM/yyyy HH:mm:ss"/></h5>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card table-modern border-0">
                <div class="card-header bg-white py-3 border-0">
                    <h5 class="fw-bold mb-0 text-secondary">Mặt Hàng Đã Xuất</h5>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã SP</th>
                                <th>Tên Sản Phẩm Máy Tính</th>
                                <th>Số Lượng Xuất</th>
                                <th>Đơn Giá Bán</th>
                                <th>Thành Tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listChiTiet}" var="ct">
                            <tr>
                                <td class="ps-3 fw-semibold">#${ct.maSP}</td>
                                <td class="fw-bold">${ct.tenSP}</td>
                                <td><span class="badge bg-danger fs-6">${ct.soLuong}</span></td>
                                <td class="text-nowrap">${tnt:vnd(ct.donGia)}</td>
                                <td class="fw-bold text-success text-nowrap">${tnt:vnd(ct.thanhTien)}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="card-footer bg-white p-3 text-end">
                    <h4 class="fw-bold text-success mb-0">Tổng Doanh Thu Xuất Kho: ${tnt:vnd(phieuXuat.tongTien)}</h4>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>