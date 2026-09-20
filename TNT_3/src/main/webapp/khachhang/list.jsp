<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:50:46 AM
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
            <c:if test="${param.error == 'coliendon'}">
                <div class="alert alert-danger py-2 mb-3">
                    <i class="bi bi-exclamation-triangle-fill me-1"></i>
                    Không thể xóa khách hàng này vì đã có Phiếu Xuất liên quan trong hệ thống.
                </div>
            </c:if>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold mb-0">Danh Sách Khách Hàng</h3>
                <button class="btn btn-primary fw-bold" data-bs-toggle="modal" data-bs-target="#addKHModal">+ Thêm Khách Hàng</button>
            </div>

            <!-- ============ TÌM KIẾM KHÁCH HÀNG ============ -->
            <form action="KhachHangServlet" method="get" class="filter-bar mb-4">
                <div class="row g-3 align-items-end">
                    <div class="col-md-7">
                        <label class="form-label">Từ khóa</label>
                        <input type="text" name="tuKhoa" class="form-control"
                               placeholder="Tìm theo tên, số điện thoại, email hoặc địa chỉ..."
                               value="<c:out value='${tuKhoa}'/>">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Sắp xếp</label>
                        <select name="sapXep" class="form-select">
                            <option value="moinhat" ${selectedSapXep == 'moinhat' ? 'selected' : ''}>Mới nhất</option>
                            <option value="cunhat"  ${selectedSapXep == 'cunhat'  ? 'selected' : ''}>Cũ nhất</option>
                            <option value="tenaz"   ${selectedSapXep == 'tenaz'   ? 'selected' : ''}>Tên A &rarr; Z</option>
                            <option value="tenza"   ${selectedSapXep == 'tenza'   ? 'selected' : ''}>Tên Z &rarr; A</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary fw-bold w-100">
                                <i class="bi bi-search me-1"></i>Tìm
                            </button>
                            <a href="KhachHangServlet" class="btn btn-outline-secondary" title="Xóa bộ lọc">
                                <i class="bi bi-arrow-counterclockwise"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </form>

            <c:if test="${dangLoc}">
                <p class="text-muted small mb-3">
                    <i class="bi bi-funnel-fill me-1"></i>Kết quả:
                    <strong>${listKH.size()}</strong> khách hàng
                </p>
            </c:if>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã KH</th>
                                <th>Tên Khách Hàng</th>
                                <th>Số Điện Thoại</th>
                                <th>Email</th>
                                <th>Địa Chỉ</th>
                                <th class="text-center">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listKH}" var="kh">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${kh.maKH}</td>
                                    <td class="fw-bold text-primary">${kh.tenKH}</td>
                                    <td>${kh.soDienThoai}</td>
                                    <td>${kh.email}</td>
                                    <td>${kh.diaChi}</td>
                                    <td class="text-center">
                                        <div class="d-flex justify-content-center gap-2">
                                            <button type="button" class="btn btn-sm btn-icon btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editKHModal${kh.maKH}" title="Sửa">
                                                <i class="bi bi-pencil-fill"></i>
                                            </button>
                                            <a href="KhachHangServlet?action=delete&id=${kh.maKH}" class="btn btn-sm btn-icon btn-outline-danger" title="Xóa" onclick="return confirm('Bạn có chắc muốn xóa khách hàng \'${kh.tenKH}\'?');">
                                                <i class="bi bi-trash-fill"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listKH}">
                                <tr>
                                    <td colspan="6" class="empty-row">
                                        Không tìm thấy khách hàng nào phù hợp.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal Sửa Khách Hàng -->
<c:forEach items="${listKH}" var="kh">
    <div class="modal fade" id="editKHModal${kh.maKH}" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="KhachHangServlet" method="post">
                    <input type="hidden" name="maKH" value="${kh.maKH}">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fw-bold">Sửa Khách Hàng #${kh.maKH}</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên Khách Hàng *</label>
                            <input type="text" name="tenKH" class="form-control" required value="${kh.tenKH}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Số Điện Thoại</label>
                            <input type="text" name="soDienThoai" class="form-control" value="${kh.soDienThoai}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" value="${kh.email}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Địa Chỉ</label>
                            <input type="text" name="diaChi" class="form-control" value="${kh.diaChi}">
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

<div class="modal fade" id="addKHModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="KhachHangServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold">Thêm Khách Hàng Mới</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Tên Khách Hàng *</label>
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
                    <button type="submit" class="btn btn-primary fw-bold">Lưu Thông Tin</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>