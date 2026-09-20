<%-- 
    Document   : list
    Created on : Sep 8, 2026, 8:30:54 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="tnt" uri="http://tnt.com/functions" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid py-4 px-4">
    <div class="row g-4">
        <div class="col-lg-2">
            <%@ include file="../includes/sidebar.jsp" %>
        </div>
        <div class="col-lg-10">
            <h3 class="fw-bold mb-4">Báo Cáo Tồn Kho Thực Tế</h3>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã SP</th>
                                <th>Tên Sản Phẩm</th>
                                <th>Hãng SX</th>
                                <th>Số Lượng Tồn</th>
                                <th>Giá Bán Niêm Yết</th>
                                <th>Đánh Giá Tồn Kho</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listSP}" var="sp">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${sp.maSP}</td>
                                    <td class="fw-bold text-primary">${sp.tenSP}</td>
                                    <td>${sp.hangSX}</td>
                                    <td class="fw-bold fs-5">${sp.soLuong}</td>
                                    <td class="text-nowrap fw-semibold">${tnt:vnd(sp.donGiaBan)}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sp.soLuong == 0}">
                                                <span class="badge bg-dark">Hết hàng</span>
                                            </c:when>
                                            <c:when test="${sp.soLuong <= 5}">
                                                <span class="badge bg-danger">Sắp hết</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-success">Còn hàng</span>
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
</body>
</html>