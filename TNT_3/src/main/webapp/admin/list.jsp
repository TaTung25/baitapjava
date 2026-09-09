<%-- 
    Document   : taikhoan_add
    Created on : Sep 8, 2026, 8:32:19 AM
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
            <c:if test="${not empty error}">
                <div class="alert alert-danger p-2 mb-3">${error}</div>
            </c:if>
            <c:if test="${param.error == 'selfaction'}">
                <div class="alert alert-danger py-2 mb-3">
                    <i class="bi bi-exclamation-triangle-fill me-1"></i>
                    Bạn không thể khóa hoặc xóa chính tài khoản đang đăng nhập.
                </div>
            </c:if>
            <c:if test="${param.error == 'coliendon'}">
                <div class="alert alert-danger py-2 mb-3">
                    <i class="bi bi-exclamation-triangle-fill me-1"></i>
                    Không thể xóa tài khoản này vì đã lập Phiếu Nhập/Xuất trong hệ thống. Hãy dùng chức năng Khóa thay thế.
                </div>
            </c:if>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold mb-0">Quản Lý Tài Khoản Hệ Thống</h3>
                <button class="btn btn-primary fw-bold" data-bs-toggle="modal" data-bs-target="#addUserModal">+ Tạo Tài Khoản Mới</button>
            </div>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">ID</th>
                                <th>Tên Đăng Nhập</th>
                                <th>Họ và Tên</th>
                                <th>Email</th>
                                <th>Số Điện Thoại</th>
                                <th>Quyền Hạn</th>
                                <th>Trạng Thái</th>
                                <th class="text-center">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listUser}" var="u">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${u.maNguoiDung}</td>
                                    <td class="fw-bold text-primary">${u.tenDangNhap}</td>
                                    <td>${u.hoTen}</td>
                                    <td>${u.email}</td>
                                    <td>${u.soDienThoai}</td>
                                    <td>
                                        <span class="badge ${u.quyen == 'ADMIN' ? 'bg-danger' : (u.quyen == 'QUANLY' ? 'bg-warning text-dark' : 'bg-info text-dark')}">
                                            ${u.quyen}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge ${u.trangThai ? 'bg-success' : 'bg-secondary'}">
                                            ${u.trangThai ? 'Hoạt động' : 'Đã khóa'}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${sessionScope.user.maNguoiDung == u.maNguoiDung}">
                                                <span class="text-muted small fst-italic">Tài khoản của bạn</span>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="d-flex justify-content-center gap-2">
                                                    <button type="button" class="btn btn-sm btn-icon btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editUserModal${u.maNguoiDung}" title="Sửa">
                                                        <i class="bi bi-pencil-fill"></i>
                                                    </button>
                                                    <a href="NguoiDungServlet?action=lock&id=${u.maNguoiDung}" class="btn btn-sm btn-icon ${u.trangThai ? 'btn-outline-warning' : 'btn-outline-success'}" title="${u.trangThai ? 'Khóa tài khoản' : 'Mở khóa tài khoản'}" onclick="return confirm('${u.trangThai ? 'Khóa' : 'Mở khóa'} tài khoản \'${u.tenDangNhap}\'?');">
                                                        <i class="bi ${u.trangThai ? 'bi-lock-fill' : 'bi-unlock-fill'}"></i>
                                                    </a>
                                                    <a href="NguoiDungServlet?action=delete&id=${u.maNguoiDung}" class="btn btn-sm btn-icon btn-outline-danger" title="Xóa" onclick="return confirm('Bạn có chắc muốn xóa hẳn tài khoản \'${u.tenDangNhap}\'?');">
                                                        <i class="bi bi-trash-fill"></i>
                                                    </a>
                                                </div>
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

<!-- Modal Sửa Tài Khoản -->
<c:forEach items="${listUser}" var="u">
    <div class="modal fade" id="editUserModal${u.maNguoiDung}" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="NguoiDungServlet" method="post">
                    <input type="hidden" name="maNguoiDung" value="${u.maNguoiDung}">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fw-bold">Sửa Tài Khoản: ${u.tenDangNhap}</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên Đăng Nhập</label>
                            <input type="text" class="form-control bg-light" value="${u.tenDangNhap}" readonly>
                            <div class="form-text">Không thể đổi tên đăng nhập.</div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật Khẩu Mới</label>
                            <input type="password" name="password" class="form-control" placeholder="Để trống nếu không đổi mật khẩu">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Họ và Tên *</label>
                            <input type="text" name="hoTen" class="form-control" required value="${u.hoTen}">
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" value="${u.email}">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Số Điện Thoại</label>
                                <input type="text" name="soDienThoai" class="form-control" value="${u.soDienThoai}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Quyền Hạn</label>
                            <select name="quyen" class="form-select" required>
                                <option value="NHANVIENKHO" ${u.quyen == 'NHANVIENKHO' ? 'selected' : ''}>Nhân Viên Kho</option>
                                <option value="QUANLY" ${u.quyen == 'QUANLY' ? 'selected' : ''}>Quản Lý Kho</option>
                                <option value="ADMIN" ${u.quyen == 'ADMIN' ? 'selected' : ''}>Quản Trị Viên (ADMIN)</option>
                            </select>
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

<!-- Modal Thêm Tài Khoản -->
<div class="modal fade" id="addUserModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="NguoiDungServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold">Tạo Tài Khoản Mới</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Tên Đăng Nhập *</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Mật Khẩu *</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Họ và Tên *</label>
                        <input type="text" name="hoTen" class="form-control" required>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Số Điện Thoại</label>
                            <input type="text" name="soDienThoai" class="form-control">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Quyền Hạn</label>
                        <select name="quyen" class="form-select" required>
                            <option value="NHANVIENKHO">Nhân Viên Kho</option>
                            <option value="QUANLY">Quản Lý Kho</option>
                            <option value="ADMIN">Quản Trị Viên (ADMIN)</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary fw-bold">Tạo Tài Khoản</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>