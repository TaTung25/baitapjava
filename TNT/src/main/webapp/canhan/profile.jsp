<%-- 
    Document   : profile
    Created on : Sep 8, 2026, 8:31:37 AM
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
            <div class="card shadow style="max-width: 650px;"">
                <div class="card-header bg-dark text-white fw-bold">
                    <i class="bi bi-person-circle me-2"></i>THÔNG TIN CÁ NHÂN
                </div>
                <div class="card-body">
                    <form action="ProfileServlet" method="post">
                        <div class="mb-3">
                            <label class="form-label">Tên đăng nhập</label>
                            <input type="text" class="form-control" value="${sessionScope.user.tenDangNhap}" disabled>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Họ và Tên</label>
                            <input type="text" name="hoTen" class="form-control" value="${sessionScope.user.hoTen}" required>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" value="${sessionScope.user.email}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Số Điện Thoại</label>
                                <input type="text" name="soDienThoai" class="form-control" value="${sessionScope.user.soDienThoai}">
                            </div>
                        </div>
                        <hr>
                        <h6 class="fw-bold text-danger">Đổi Mật Khẩu (Để trống nếu không muốn đổi)</h6>
                        <div class="mb-3">
                            <label class="form-label">Mật Khẩu Mới</label>
                            <input type="password" name="newPassword" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-primary">Lưu Thay Đổi</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
