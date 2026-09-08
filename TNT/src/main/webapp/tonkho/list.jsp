<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:30:54 AM
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
            <h3 class="fw-bold text-secondary mb-3">Báo Cáo Tồn Kho Máy Tính</h3>

            <div class="table-responsive">
                <table class="table table-custom table-hover">
                    <thead>
                        <tr>
                            <th>Mã SP</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Hãng SX</th>
                            <th>Giá Bán</th>
                            <th>Số Lượng Tồn</th>
                            <th>Cảnh Báo Kho</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listSP}" var="sp">
                            <tr>
                                <td>${sp.maSP}</td>
                                <td class="fw-bold">${sp.tenSP}</td>
                                <td>${sp.hangSX}</td>
                                <td>${sp.donGiaBan} VNĐ</td>
                                <td class="fw-bold fs-5">${sp.soLuong}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${sp.soLuong == 0}">
                                            <span class="badge bg-dark">Hết hàng</span>
                                        </c:when>
                                        <c:when test="${sp.soLuong <= 5}">
                                            <span class="badge bg-danger badge-low-stock">Sắp hết (<=5)</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-success">An toàn</span>
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