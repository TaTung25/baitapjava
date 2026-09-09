<%-- 
    Document   : login
    Created on : Sep 8, 2026, 7:27:49 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Nhập - Hệ Thống Kho TNT</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="auth-shell">
            <div class="auth-card">
                <div class="card-body">
                    <div class="text-center mb-4">
                        <div class="auth-logo"><i class="bi bi-box-seam-fill"></i></div>
                        <h3 class="fw-bold mb-1">KHO HÀNG TNT</h3>
                        <p class="text-muted small mb-0">Đăng nhập hệ thống quản lý kho máy tính</p>
                    </div>

                    <% if (request.getAttribute("error") != null) {%>
                    <div class="alert alert-danger py-2 small"><i class="bi bi-exclamation-triangle-fill me-1"></i><%= request.getAttribute("error")%></div>
                    <% }%>

                    <form action="LoginServlet" method="post">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required placeholder="Nhập tên đăng nhập" autofocus>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required placeholder="Nhập mật khẩu">
                        </div>
                        <button type="submit" class="btn btn-primary w-100 fw-bold py-2 mt-2">
                            <i class="bi bi-box-arrow-in-right me-1"></i>Đăng Nhập
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
