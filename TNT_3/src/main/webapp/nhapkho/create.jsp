<%-- 
    Document   : create
    Created on : Sep 8, 2026, 7:30:16 AM
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
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-success text-white fw-bold py-3">
                    <i class="bi bi-box-arrow-in-down me-2"></i>TẠO PHIẾU NHẬP KHO MÁY TÍNH
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/PhieuNhapServlet" method="post">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Nhà Cung Cấp *</label>
                                <select name="maNCC" class="form-select" required>
                                    <option value="">-- Chọn nhà cung cấp --</option>
                                    <c:forEach items="${listNCC}" var="ncc">
                                        <option value="${ncc.maNCC}">${ncc.tenNCC}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Ghi Chú Phiếu Nhập</label>
                                <input type="text" name="ghiChu" class="form-control" placeholder="Ghi chú đợt nhập...">
                            </div>
                        </div>

                        <h5 class="fw-bold mt-4 mb-3 text-secondary">Danh Sách Mặt Hàng Nhập</h5>
                        <table class="table table-bordered align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th style="width: 35%;">Sản Phẩm Máy Tính</th>
                                    <th style="width: 15%;">Số Lượng</th>
                                    <th style="width: 25%;">Đơn Giá Nhập (VNĐ)</th>
                                    <th style="width: 20%;">Thành Tiền</th>
                                    <th style="width: 5%;">Xóa</th>
                                </tr>
                            </thead>
                            <tbody id="importBody">
                                <tr>
                                    <td>
                                        <select name="maSP" class="form-select sp-select" required onchange="calculateRow(this)">
                                            <option value="">-- Chọn sản phẩm --</option>
                                            <c:forEach items="${listSP}" var="sp">
                                                <option value="${sp.maSP}" data-price="${tnt:raw(sp.donGiaNhap)}">${sp.tenSP}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><input type="number" name="soLuong" class="form-control sl-input" min="1" value="1" required oninput="calculateRow(this)"></td>
                                    <td><input type="number" name="donGia" class="form-control gia-input" min="0" step="1000" value="0" required oninput="calculateRow(this)"></td>
                                    <td><input type="text" class="form-control thanh-tien" value="0" readonly></td>
                                    <td class="text-center"><button type="button" class="btn btn-danger btn-sm" onclick="this.closest('tr').remove(); calculateTotal();"><i class="bi bi-x-lg"></i></button></td>
                                </tr>
                            </tbody>
                        </table>

                        <button type="button" class="btn btn-outline-primary mb-3" onclick="addRow()">+ Thêm Sản Phẩm Nhập</button>

                        <div class="row justify-content-end text-end mt-3">
                            <div class="col-md-5">
                                <h4 class="fw-bold text-danger">Tổng Chi Nhập Kho: <span id="lblTongTien">0</span> VNĐ</h4>
                            </div>
                        </div>

                        <hr>
                        <div class="d-flex justify-content-end gap-2">
                            <a href="DashboardServlet" class="btn btn-secondary">Hủy</a>
                            <button type="submit" class="btn btn-success fw-bold px-4">Xác Nhận Lưu Phiếu Nhập</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function addRow() {
        let tbody = document.getElementById("importBody");
        let newRow = tbody.children[0].cloneNode(true);
        newRow.querySelector('.sl-input').value = 1;
        newRow.querySelector('.gia-input').value = 0;
        newRow.querySelector('.thanh-tien').value = 0;
        tbody.appendChild(newRow);
    }

    function calculateRow(element) {
        let tr = element.closest("tr");
        let select = tr.querySelector(".sp-select");
        let giaInput = tr.querySelector(".gia-input");

        if (element.classList.contains("sp-select")) {
            let price = select.options[select.selectedIndex].getAttribute("data-price");
            if (price)
                giaInput.value = price;
        }

        let sl = parseFloat(tr.querySelector(".sl-input").value) || 0;
        let gia = parseFloat(giaInput.value) || 0;
        tr.querySelector(".thanh-tien").value = (sl * gia).toLocaleString('vi-VN');
        calculateTotal();
    }

    function calculateTotal() {
        let total = 0;
        document.querySelectorAll("#importBody tr").forEach(tr => {
            let sl = parseFloat(tr.querySelector(".sl-input").value) || 0;
            let gia = parseFloat(tr.querySelector(".gia-input").value) || 0;
            total += (sl * gia);
        });
        document.getElementById("lblTongTien").innerText = total.toLocaleString('vi-VN');
    }
</script>
</body>
</html>