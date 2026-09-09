<%-- 
    Document   : create
    Created on : Sep 8, 2026, 7:31:01 AM
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
            <c:if test="${not empty error}">
                <div class="alert alert-danger p-3 mb-3">${error}</div>
            </c:if>

            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-danger text-white fw-bold py-3">
                    <i class="bi bi-box-arrow-up-right me-2"></i>TẠO PHIẾU XUẤT KHO MÁY TÍNH
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/PhieuXuatServlet" method="post">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Khách Hàng *</label>
                                <select name="maKH" class="form-select" required>
                                    <option value="">-- Chọn khách hàng --</option>
                                    <c:forEach items="${listKH}" var="kh">
                                        <option value="${kh.maKH}">${kh.tenKH} - ${kh.soDienThoai}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Ghi Chú Xuất Kho</label>
                                <input type="text" name="ghiChu" class="form-control" placeholder="Ghi chú đơn xuất...">
                            </div>
                        </div>

                        <h5 class="fw-bold mt-4 mb-3 text-secondary">Danh Sách Mặt Hàng Xuất Kho</h5>
                        <table class="table table-bordered align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th style="width: 35%;">Sản Phẩm (Tồn Kho Hiện Tại)</th>
                                    <th style="width: 15%;">Số Lượng Xuất</th>
                                    <th style="width: 25%;">Đơn Giá Bán (VNĐ)</th>
                                    <th style="width: 20%;">Thành Tiền</th>
                                    <th style="width: 5%;">Xóa</th>
                                </tr>
                            </thead>
                            <tbody id="exportBody">
                                <tr>
                                    <td>
                                        <select name="maSP" class="form-select sp-select" required onchange="calculateRow(this)">
                                            <option value="">-- Chọn sản phẩm --</option>
                                            <c:forEach items="${listSP}" var="sp">
                                                <option value="${sp.maSP}" data-stock="${sp.soLuong}" data-price="${sp.donGiaBan}">
                                                    ${sp.tenSP} (Còn: ${sp.soLuong})
                                                </option>
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

                        <button type="button" class="btn btn-outline-primary mb-3" onclick="addRow()">+ Thêm Sản Phẩm Xuất</button>

                        <div class="row justify-content-end text-end mt-3">
                            <div class="col-md-5">
                                <h4 class="fw-bold text-danger">Tổng Doanh Thu Xuất: <span id="lblTongTien">0</span> VNĐ</h4>
                            </div>
                        </div>

                        <hr>
                        <div class="d-flex justify-content-end gap-2">
                            <a href="DashboardServlet" class="btn btn-secondary">Hủy</a>
                            <button type="submit" class="btn btn-danger fw-bold px-4">Xác Nhận Xuất Kho</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function addRow() {
        let tbody = document.getElementById("exportBody");
        let newRow = tbody.children[0].cloneNode(true);
        newRow.querySelector('.sl-input').value = 1;
        newRow.querySelector('.gia-input').value = 0;
        newRow.querySelector('.thanh-tien').value = 0;
        tbody.appendChild(newRow);
    }

    function calculateRow(element) {
        let tr = element.closest("tr");
        let select = tr.querySelector(".sp-select");
        let selectedOpt = select.options[select.selectedIndex];

        if (element.classList.contains("sp-select") && selectedOpt.value !== "") {
            tr.querySelector(".gia-input").value = selectedOpt.getAttribute("data-price") || 0;
        }

        let stock = parseInt(selectedOpt.getAttribute("data-stock")) || 0;
        let sl = parseInt(tr.querySelector(".sl-input").value) || 0;

        if (sl > stock && selectedOpt.value !== "") {
            alert("Cảnh báo: Số lượng xuất (" + sl + ") vượt quá tồn kho (" + stock + ")!");
            tr.querySelector(".sl-input").value = stock;
            sl = stock;
        }

        let gia = parseFloat(tr.querySelector(".gia-input").value) || 0;
        tr.querySelector(".thanh-tien").value = (sl * gia).toLocaleString('vi-VN');
        calculateTotal();
    }

    function calculateTotal() {
        let total = 0;
        document.querySelectorAll("#exportBody tr").forEach(tr => {
            let sl = parseFloat(tr.querySelector(".sl-input").value) || 0;
            let gia = parseFloat(tr.querySelector(".gia-input").value) || 0;
            total += (sl * gia);
        });
        document.getElementById("lblTongTien").innerText = total.toLocaleString('vi-VN');
    }
</script>
</body>
</html>