<%-- 
    Document   : dashboard
    Created on : Sep 8, 2026, 7:28:23 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>

<div class="container-fluid mt-4">
    <div class="row">
        <!-- Sidebar Menu -->
        <div class="col-md-2">
            <div class="list-group">
                <a href="DashboardServlet" class="list-group-item list-group-item-action active">Dashboard</a>
                <a href="SanPhamServlet" class="list-group-item list-group-item-action">Sản Phẩm</a>
                <a href="nhapkho/create.jsp" class="list-group-item list-group-item-action">Tạo Phiếu Nhập</a>
                <a href="xuatkho/create.jsp" class="list-group-item list-group-item-action">Tạo Phiếu Xuất</a>
            </div>
        </div>

        <!-- Dashboard Content -->
        <div class="col-md-10">
            <h2 class="mb-4">Tổng Quan Hệ Thống</h2>
            <div class="row text-center mb-4">
                <div class="col-md-4">
                    <div class="card bg-primary text-white shadow">
                        <div class="card-body">
                            <h5>Mặt Hàng Sản Phẩm</h5>
                            <h3>${totalProducts}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card bg-success text-white shadow">
                        <div class="card-body">
                            <h5>Tổng Tồn Kho</h5>
                            <h3>${totalStock}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card bg-danger text-white shadow">
                        <div class="card-body">
                            <h5>Sắp Hết Hàng (<=5)</h5>
                            <h3>${lowStockCount}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Cảnh Báo Tồn Kho -->
            <div class="card shadow">
                <div class="card-header bg-warning text-dark fw-bold">
                    <i class="bi bi-exclamation-triangle"></i> Cảnh Báo Sản Phẩm Cần Nhập Thêm
                </div>
                <div class="card-body">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Mã SP</th>
                                <th>Tên Sản Phẩm</th>
                                <th>Số Lượng Tồn</th>
                                <th>Hãng SX</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lowStockList}" var="sp">
                                <tr>
                                    <td>${sp.maSP}</td>
                                    <td class="fw-bold">${sp.tenSP}</td>
                                    <td><span class="badge bg-danger">${sp.soLuong}</span></td>
                                    <td>${sp.hangSX}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>