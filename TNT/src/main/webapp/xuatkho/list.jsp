<%-- 
    Document   : list
    Created on : Sep 8, 2026, 7:30:40 AM
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
                <h3 class="fw-bold text-secondary">Lịch Sử Phiếu Xuất Kho</h3>
                <a href="PhieuXuatServlet?action=create" class="btn btn-danger">+ Tạo Phiếu Xuất Mới</a>
            </div>
            <div class="alert alert-info">Chức năng hiển thị danh sách phiếu xuất kho.</div>
        </div>
    </div>
</div>
