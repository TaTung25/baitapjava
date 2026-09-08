<%-- 
    Document   : list
    Created on : Sep 8, 2026, 7:29:38 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Quản Lý Danh Sách Sản Phẩm</h3>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">+ Thêm Sản Phẩm</button>
    </div>

    <table class="table table-bordered table-hover shadow-sm">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Tên SP</th>
                <th>Loại</th>
                <th>Nhà Cung Cấp</th>
                <th>Giá Nhập</th>
                <th>Giá Bán</th>
                <th>Tồn Kho</th>
                <th>Bảo Hành</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listSP}" var="sp">
                <tr>
                    <td>${sp.maSP}</td>
                    <td class="fw-bold">${sp.tenSP}</td>
                    <td>${sp.tenLoai}</td>
                    <td>${sp.tenNCC}</td>
                    <td>${sp.donGiaNhap} VNĐ</td>
                    <td>${sp.donGiaBan} VNĐ</td>
                    <td>
                        <span class="badge ${sp.soLuong <= 5 ? 'bg-danger' : 'bg-primary'}">
                            ${sp.soLuong}
                        </span>
                    </td>
                    <td>${sp.baoHanh} Tháng</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>