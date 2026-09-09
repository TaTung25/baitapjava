<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:30:21 AM
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
                <h3 class="fw-bold mb-0">Danh Sách Nhà Cung Cấp</h3>
                <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
                    <button class="btn btn-primary fw-bold" data-bs-toggle="modal" data-bs-target="#addNCCModal">+ Thêm Nhà Cung Cấp</button>
                </c:if>
            </div>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã NCC</th>
                                <th>Tên Nhà Cung Cấp</th>
                                <th>Số Điện Thoại</th>
                                <th>Email</th>
                                <th>Địa Chỉ</th>
                                <th class="text-center">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listNCC}" var="ncc">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${ncc.maNCC}</td>
                                    <td class="fw-bold text-primary">${ncc.tenNCC}</td>
                                    <td>${ncc.soDienThoai}</td>
                                    <td>${ncc.email}</td>
                                    <td>${ncc.diaChi}</td>
                                    <td class="text-center">
                                        <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
                                            <div class="d-flex justify-content-center gap-2">
                                                <button type="button" class="btn btn-sm btn-icon btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editNCCModal${ncc.maNCC}" title="Sửa">
                                                    <i class="bi bi-pencil-fill"></i>
                                                </button>
                                                <a href="NhaCungCapServlet?action=delete&id=${ncc.maNCC}" class="btn btn-sm btn-icon btn-outline-danger" title="Xóa" onclick="return confirm('Bạn có chắc muốn xóa nhà cung cấp \'${ncc.tenNCC}\'?');">
                                                    <i class="bi bi-trash-fill"></i>
                                                </a>
                                            </div>
                                        </c:if>
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

<!-- Modal Sửa Nhà Cung Cấp -->
<c:forEach items="${listNCC}" var="ncc">
    <div class="modal fade" id="editNCCModal${ncc.maNCC}" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="NhaCungCapServlet" method="post">
                    <input type="hidden" name="maNCC" value="${ncc.maNCC}">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fw-bold">Sửa Nhà Cung Cấp #${ncc.maNCC}</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên Nhà Cung Cấp *</label>
                            <input type="text" name="tenNCC" class="form-control" required value="${ncc.tenNCC}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Số Điện Thoại</label>
                            <input type="text" name="soDienThoai" class="form-control" value="${ncc.soDienThoai}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" value="${ncc.email}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Địa Chỉ</label>
                            <input type="text" name="diaChi" class="form-control" value="${ncc.diaChi}">
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

<div class="modal fade" id="addNCCModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="NhaCungCapServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold">Thêm Nhà Cung Cấp</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Tên Nhà Cung Cấp *</label>
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
                    <button type="submit" class="btn btn-primary fw-bold">Lưu Thông Tin</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>