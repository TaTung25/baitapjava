<%--
    Document   : profile
    Created on : Sep 8, 2026, 8:31:37 AM
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
            <h3 class="fw-bold mb-4">Tài Khoản Cá Nhân</h3>

            <div class="row g-4">
                <div class="col-md-6">
                    <div class="card border-0 shadow-sm rounded-3 h-100">
                        <div class="card-header bg-dark text-white fw-bold py-3">
                            <i class="bi bi-person-circle me-2"></i>THÔNG TIN TÀI KHOẢN
                        </div>
                        <div class="card-body p-4">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger py-2 mb-3"><i class="bi bi-exclamation-triangle-fill me-1"></i>${error}</div>
                                </c:if>
                                <c:if test="${not empty success}">
                                <div class="alert alert-success py-2 mb-3"><i class="bi bi-check-circle-fill me-1"></i>${success}</div>
                                </c:if>

                            <form action="${pageContext.request.contextPath}/ProfileServlet" method="post">
                                <input type="hidden" name="action" value="updateInfo">
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tên đăng nhập</label>
                                    <input type="text" class="form-control bg-light" value="${sessionScope.user.tenDangNhap}" readonly>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Họ và Tên *</label>
                                    <input type="text" name="hoTen" class="form-control" required value="${sessionScope.user.hoTen}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Email</label>
                                    <input type="email" name="email" class="form-control" value="${sessionScope.user.email}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Số Điện Thoại</label>
                                    <input type="text" name="soDienThoai" class="form-control" value="${sessionScope.user.soDienThoai}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Quyền Hạn</label>
                                    <input type="text" class="form-control bg-light fw-bold text-primary" value="${sessionScope.user.quyen}" readonly>
                                </div>
                                <button type="submit" class="btn btn-primary fw-bold">
                                    <i class="bi bi-save me-1"></i>Lưu Cập Nhật
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="card border-0 shadow-sm rounded-3 h-100">
                        <div class="card-header bg-dark text-white fw-bold py-3">
                            <i class="bi bi-shield-lock-fill me-2"></i>ĐỔI MẬT KHẨU
                        </div>
                        <div class="card-body p-4">
                            <c:if test="${not empty errorPass}">
                                <div class="alert alert-danger py-2 mb-3"><i class="bi bi-exclamation-triangle-fill me-1"></i>${errorPass}</div>
                                </c:if>
                                <c:if test="${not empty successPass}">
                                <div class="alert alert-success py-2 mb-3"><i class="bi bi-check-circle-fill me-1"></i>${successPass}</div>
                                </c:if>

                            <form action="${pageContext.request.contextPath}/ProfileServlet" method="post">
                                <input type="hidden" name="action" value="changePassword">
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Mật khẩu hiện tại *</label>
                                    <input type="password" name="matKhauCu" class="form-control" required placeholder="Nhập mật khẩu hiện tại" autocomplete="current-password">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Mật khẩu mới *</label>
                                    <input type="password" name="matKhauMoi" class="form-control" required minlength="6" placeholder="Ít nhất 6 ký tự" autocomplete="new-password">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Xác nhận mật khẩu mới *</label>
                                    <input type="password" name="matKhauXacNhan" class="form-control" required minlength="6" placeholder="Nhập lại mật khẩu mới" autocomplete="new-password">
                                    <div class="form-text">Mật khẩu mới phải có ít nhất 6 ký tự và khác mật khẩu hiện tại.</div>
                                </div>
                                <button type="submit" class="btn btn-warning fw-bold">
                                    <i class="bi bi-key-fill me-1"></i>Đổi Mật Khẩu
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
