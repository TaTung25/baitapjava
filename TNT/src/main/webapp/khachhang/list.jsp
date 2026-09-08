<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:50:46 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <%@ include file="../includes/sidebar.jsp" %>
        </div>
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="fw-bold text-secondary">Danh Sách Khách Hàng</h3>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addKHModal">
                    <i class="bi bi-plus-lg"></i> Thêm Khách Hàng
                </button>
            </div>

            <table class="table table-custom table-hover">
                <thead>
                    <tr>
                        <th>Mã KH</th>
                        <th>Tên Khách Hàng</th>
                        <th>Số Điện Thoại</th>
                        <th>Email</th>
                        <th>Địa Chỉ</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listKH}" var="kh">
                        <tr>
                            <td>${kh.maKH}</td>
                            <td class="fw-bold">${kh.tenKH}</td>
                            <td>${kh.soDienThoai}</td>
                            <td>${kh.email}</td>
                            <td>${kh.diaChi}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Thêm Khách Hàng -->
<div class="modal fade" id="addKHModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="KhachHangServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Thêm Khách Hàng Mới</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Tên Khách Hàng *</label>
                        <input type="text" name="tenKH" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Số Điện Thoại</label>
                        <input type="text" name="soDienThoai" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Địa Chỉ</label>
                        <input type="text" name="diaChi" class="form-control">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu Khách Hàng</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>