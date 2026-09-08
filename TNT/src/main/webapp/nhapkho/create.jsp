<%-- 
    Document   : create
    Created on : Sep 8, 2026, 7:30:16 AM
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
            <div class="card shadow">
                <div class="card-header bg-success text-white fw-bold">
                    <i class="bi bi-box-arrow-in-down me-2"></i>TẠO PHIẾU NHẬP KHO
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/PhieuNhapServlet" method="post" id="pnForm">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Nhà Cung Cấp *</label>
                                <select name="maNCC" class="form-select" required>
                                    <option value="">-- Chọn nhà cung cấp --</option>
                                    <c:forEach items="${listNCC}" var="ncc">
                                        <option value="${ncc.maNCC}">${ncc.tenNCC}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Ghi Chú</label>
                                <input type="text" name="ghiChu" class="form-control" placeholder="Ghi chú nhập hàng...">
                            </div>
                        </div>

                        <h5 class="fw-bold mt-4 mb-3 text-secondary">Chi Tiết Sản Phẩm Nhập</h5>
                        <table class="table table-bordered" id="productTable">
                            <thead class="table-light">
                                <tr>
                                    <th style="width: 35%;">Sản Phẩm</th>
                                    <th style="width: 15%;">Số Lượng</th>
                                    <th style="width: 25%;">Đơn Giá Nhập (VNĐ)</th>
                                    <th style="width: 20%;">Thành Tiền</th>
                                    <th style="width: 5%;">Xóa</th>
                                </tr>
                            </thead>
                            <tbody id="productBody">
                                <tr>
                                    <td>
                                        <select name="maSP" class="form-select sp-select" required onchange="calculateRow(this)">
                                            <option value="">-- Chọn sản phẩm --</option>
                                            <c:forEach items="${listSP}" var="sp">
                                                <option value="${sp.maSP}" data-price="${sp.donGiaNhap}">${sp.tenSP}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><input type="number" name="soLuong" class="form-control sl-input" min="1" value="1" required oninput="calculateRow(this)"></td>
                                    <td><input type="number" name="donGia" class="form-control gia-input" min="0" step="1000" value="0" required oninput="calculateRow(this)"></td>
                                    <td><input type="text" class="form-control thanh-tien" value="0" readonly></td>
                                    <td class="text-center"><button type="button" class="btn btn-danger btn-sm" onclick="removeRow(this)"><i class="bi bi-x-lg"></i></button></td>
                                </tr>
                            </tbody>
                        </table>

                        <button type="button" class="btn btn-outline-primary mb-3" onclick="addRow()">
                            <i class="bi bi-plus-circle me-1"></i> Thêm Dòng Sản Phẩm
                        </button>

                        <div class="row justify-content-end text-end mt-3">
                            <div class="col-md-4">
                                <h4 class="fw-bold text-danger">Tổng Tiền: <span id="lblTongTien">0</span> VNĐ</h4>
                            </div>
                        </div>

                        <hr>
                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/PhieuNhapServlet" class="btn btn-secondary">Hủy</a>
                            <button type="submit" class="btn btn-success fw-bold px-4">Lưu Phiếu Nhập Kho</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function addRow() {
        let tbody = document.getElementById("productBody");
        let firstRow = tbody.children[0];
        let newRow = firstRow.cloneNode(true);

        // Reset values
        newRow.querySelector('.sl-input').value = 1;
        newRow.querySelector('.gia-input').value = 0;
        newRow.querySelector('.thanh-tien').value = 0;
        tbody.appendChild(newRow);
    }

    function removeRow(btn) {
        let tbody = document.getElementById("productBody");
        if (tbody.children.length > 1) {
            btn.closest("tr").remove();
            calculateTotal();
        } else {
            alert("Phiếu nhập phải có ít nhất 1 sản phẩm!");
        }
    }

    function calculateRow(element) {
        let tr = element.closest("tr");
        let select = tr.querySelector(".sp-select");
        let giaInput = tr.querySelector(".gia-input");

        // Auto populate price if user selects product and price input is zero
        if (element.classList.contains("sp-select")) {
            let price = select.options[select.selectedIndex].getAttribute("data-price");
            if (price)
                giaInput.value = price;
        }

        let sl = parseFloat(tr.querySelector(".sl-input").value) || 0;
        let gia = parseFloat(giaInput.value) || 0;
        let thanhTien = sl * gia;

        tr.querySelector(".thanh-tien").value = thanhTien.toLocaleString('vi-VN');
        calculateTotal();
    }

    function calculateTotal() {
        let total = 0;
        document.querySelectorAll("#productBody tr").forEach(tr => {
            let sl = parseFloat(tr.querySelector(".sl-input").value) || 0;
            let gia = parseFloat(tr.querySelector(".gia-input").value) || 0;
            total += (sl * gia);
        });
        document.getElementById("lblTongTien").innerText = total.toLocaleString('vi-VN');
    }
</script>