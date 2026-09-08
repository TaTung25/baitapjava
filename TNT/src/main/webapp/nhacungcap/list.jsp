<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:30:21 AM
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
                <h3 class="fw-bold text-secondary">Danh Sách Nhà Cung Cấp</h3>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addNCCModal">
                    <i class="bi bi-plus-lg"></i> Thêm Nhà Cung Cấp
                </button>
            </div>

            <table class="table table-custom table-hover">
                <thead>
                    <tr>
                        <th>Mã NCC</th>
                        <th>Tên Nhà Cung Cấp</th>
                        <th>Số Điện Thoại</th>
                        <th>Email</th>
                        <th>Địa Chỉ</th>
                        <th>Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listNCC}" var="ncc">
                        <tr>
                            <td>${ncc.maNCC}</td>
                            <td class="fw-bold">${ncc.tenNCC}</td>
                            <td>${ncc.soDienThoai}</td>
                            <td>${ncc.email}</td>
                            <td>${ncc.diaChi}</td>
                            <td>
                                <button class="btn btn-sm btn-outline-warning"><i class="bi bi-pencil"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Thêm NCC -->
<div class="modal fade" id="addNCCModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="NhaCungCapServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Thêm Nhà Cung Cấp Mới</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Tên Nhà Cung Cấp *</label>
                        <input type="text" name="tenNCC" class="form-control" required>
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
                    <button type="submit" class="btn btn-primary">Lưu Nhà Cung Cấp</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
