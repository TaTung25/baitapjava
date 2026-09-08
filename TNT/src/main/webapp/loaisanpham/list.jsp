<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:29:44 AM
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
                <h3 class="fw-bold text-secondary">Danh Sách Loại Sản Phẩm</h3>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addLoaiModal">
                    <i class="bi bi-plus-lg"></i> Thêm Loại SP
                </button>
            </div>

            <table class="table table-custom table-hover">
                <thead>
                    <tr>
                        <th>Mã Loại</th>
                        <th>Tên Loại Sản Phẩm</th>
                        <th>Mô Tả</th>
                        <th>Trạng Thái</th>
                        <th>Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listLoai}" var="l">
                        <tr>
                            <td>${l.maLoai}</td>
                            <td class="fw-bold">${l.tenLoai}</td>
                            <td>${l.moTa}</td>
                            <td>
                                <span class="badge ${l.trangThai ? 'bg-success' : 'bg-secondary'}">
                                    ${l.trangThai ? 'Hoạt động' : 'Khóa'}
                                </span>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-outline-warning me-1"><i class="bi bi-pencil"></i></button>
                                <a href="LoaiSanPhamServlet?action=delete&id=${l.maLoai}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc muốn xóa?')"><i class="bi bi-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Thêm Loại SP -->
<div class="modal fade" id="addLoaiModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="LoaiSanPhamServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Thêm Loại Sản Phẩm</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Tên Loại *</label>
                        <input type="text" name="tenLoai" class="form-control" required placeholder="VD: Gaming Laptop">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mô Tả</label>
                        <textarea name="moTa" class="form-control" rows="3"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu thông tin</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>