<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:29:44 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid py-4 px-4">
    <div class="row g-4">
        <div class="col-lg-2">
            <%@ include file="../includes/sidebar.jsp" %>
        </div>
        <div class="col-lg-10">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold mb-0">Danh Sách Loại Sản Phẩm</h3>
                <button class="btn btn-primary fw-bold" data-bs-toggle="modal" data-bs-target="#addLoaiModal">+ Thêm Loại SP</button>
            </div>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã Loại</th>
                                <th>Tên Loại Sản Phẩm</th>
                                <th>Mô Tả</th>
                                <th>Trạng Thái</th>
                                <th class="text-center">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listLoai}" var="l">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${l.maLoai}</td>
                                    <td class="fw-bold text-primary">${l.tenLoai}</td>
                                    <td>${l.moTa}</td>
                                    <td><span class="badge bg-success">Hoạt động</span></td>
                                    <td class="text-center">
                                        <div class="d-flex justify-content-center gap-2">
                                            <button type="button" class="btn btn-sm btn-icon btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editLoaiModal${l.maLoai}" title="Sửa">
                                                <i class="bi bi-pencil-fill"></i>
                                            </button>
                                            <a href="LoaiSanPhamServlet?action=delete&id=${l.maLoai}" class="btn btn-sm btn-icon btn-outline-danger" title="Xóa" onclick="return confirm('Bạn có chắc muốn xóa loại sản phẩm \'${l.tenLoai}\'?');">
                                                <i class="bi bi-trash-fill"></i>
                                            </a>
                                        </div>
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

<c:forEach items="${listLoai}" var="l">
    <div class="modal fade" id="editLoaiModal${l.maLoai}" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="LoaiSanPhamServlet" method="post">
                    <input type="hidden" name="maLoai" value="${l.maLoai}">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fw-bold">Sửa Loại Sản Phẩm #${l.maLoai}</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên Loại *</label>
                            <input type="text" name="tenLoai" class="form-control" required value="${l.tenLoai}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mô Tả</label>
                            <textarea name="moTa" class="form-control" rows="3">${l.moTa}</textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-primary fw-bold">Lưu Thay Đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:forEach>

<div class="modal fade" id="addLoaiModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="LoaiSanPhamServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold">Thêm Loại Sản Phẩm</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Tên Loại *</label>
                        <input type="text" name="tenLoai" class="form-control" required placeholder="VD: Laptop Business">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mô Tả</label>
                        <textarea name="moTa" class="form-control" rows="3"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary fw-bold">Lưu Thông Tin</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>