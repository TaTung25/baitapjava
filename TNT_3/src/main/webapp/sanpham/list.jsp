<%-- 
    Document   : list
    Created on : Sep 8, 2026, 7:29:38 AM
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
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold mb-0">Quản Lý Sản Phẩm Máy Tính</h3>
                <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
                    <button class="btn btn-primary fw-bold" data-bs-toggle="modal" data-bs-target="#addSPModal">+ Thêm Sản Phẩm</button>
                </c:if>
            </div>

            <!-- ============ BỘ LỌC / TÌM KIẾM SẢN PHẨM ============ -->
            <form action="SanPhamServlet" method="get" class="filter-bar mb-4">
                <input type="hidden" name="action" value="search">
                <div class="row g-3 align-items-end">
                    <div class="col-md-3">
                        <label class="form-label">Từ khóa</label>
                        <input type="text" name="tuKhoa" class="form-control"
                               placeholder="Tên SP, hãng, cấu hình..."
                               value="<c:out value='${tuKhoa}'/>">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Loại sản phẩm</label>
                        <select name="maLoai" class="form-select">
                            <option value="0">Tất cả loại</option>
                            <c:forEach items="${listLoai}" var="l">
                                <option value="${l.maLoai}" ${selectedLoai == l.maLoai ? 'selected' : ''}>${l.tenLoai}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Nhà cung cấp</label>
                        <select name="maNCC" class="form-select">
                            <option value="0">Tất cả NCC</option>
                            <c:forEach items="${listNCC}" var="n">
                                <option value="${n.maNCC}" ${selectedNCC == n.maNCC ? 'selected' : ''}>${n.tenNCC}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Tình trạng tồn</label>
                        <select name="tinhTrang" class="form-select">
                            <option value="">Tất cả</option>
                            <option value="connhieu" ${selectedTinhTrang == 'connhieu' ? 'selected' : ''}>Còn nhiều (&ge; 20)</option>
                            <option value="conhang"  ${selectedTinhTrang == 'conhang'  ? 'selected' : ''}>Còn hàng (&gt; 5)</option>
                            <option value="saphet"   ${selectedTinhTrang == 'saphet'   ? 'selected' : ''}>Sắp hết (1 - 5)</option>
                            <option value="het"      ${selectedTinhTrang == 'het'      ? 'selected' : ''}>Hết hàng</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Sắp xếp</label>
                        <select name="sapXep" class="form-select">
                            <option value="moinhat"  ${selectedSapXep == 'moinhat'  ? 'selected' : ''}>Mới nhất</option>
                            <option value="tenaz"    ${selectedSapXep == 'tenaz'    ? 'selected' : ''}>Tên A &rarr; Z</option>
                            <option value="tenza"    ${selectedSapXep == 'tenza'    ? 'selected' : ''}>Tên Z &rarr; A</option>
                            <option value="giagiam"  ${selectedSapXep == 'giagiam'  ? 'selected' : ''}>Giá cao &rarr; thấp</option>
                            <option value="giatang"  ${selectedSapXep == 'giatang'  ? 'selected' : ''}>Giá thấp &rarr; cao</option>
                            <option value="tonnhieu" ${selectedSapXep == 'tonnhieu' ? 'selected' : ''}>Tồn nhiều nhất</option>
                            <option value="tonit"    ${selectedSapXep == 'tonit'    ? 'selected' : ''}>Tồn ít nhất</option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary fw-bold w-100" title="Tìm kiếm">
                                <i class="bi bi-search"></i>
                            </button>
                            <a href="SanPhamServlet" class="btn btn-outline-secondary" title="Xóa bộ lọc">
                                <i class="bi bi-arrow-counterclockwise"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </form>

            <c:if test="${dangLoc}">
                <p class="text-muted small mb-3">
                    <i class="bi bi-funnel-fill me-1"></i>Kết quả lọc:
                    <strong>${listSP.size()}</strong> sản phẩm
                </p>
            </c:if>

            <div class="card table-modern border-0">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã SP</th>
                                <th>Tên Sản Phẩm</th>
                                <th>Loại SP</th>
                                <th>Nhà Cung Cấp</th>
                                <th>Đơn Giá Nhập</th>
                                <th>Đơn Giá Bán</th>
                                <th>Tồn Kho</th>
                                <th>Bảo Hành</th>
                                <th class="text-center">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listSP}" var="sp">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${sp.maSP}</td>
                                    <td class="fw-bold text-primary">${sp.tenSP}</td>
                                    <td>${sp.tenLoai}</td>
                                    <td>${sp.tenNCC}</td>
                                    <td class="text-nowrap">${tnt:vnd(sp.donGiaNhap)}</td>
                                    <td class="text-nowrap fw-semibold">${tnt:vnd(sp.donGiaBan)}</td>
                                    <td>
                                        <span class="badge ${sp.soLuong <= 5 ? 'bg-danger' : 'bg-success'} badge-stock">
                                            ${sp.soLuong}
                                        </span>
                                    </td>
                                    <td>${sp.baoHanh} Thg</td>
                                    <td class="text-center">
                                        <c:if test="${sessionScope.user.quyen != 'NHANVIENKHO'}">
                                            <div class="d-flex justify-content-center gap-2">
                                                <button type="button" class="btn btn-sm btn-icon btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editSPModal${sp.maSP}" title="Sửa">
                                                    <i class="bi bi-pencil-fill"></i>
                                                </button>
                                                <a href="SanPhamServlet?action=delete&id=${sp.maSP}" class="btn btn-sm btn-icon btn-outline-danger" title="Xóa" onclick="return confirm('Bạn có chắc muốn xóa sản phẩm \'${sp.tenSP}\'?');">
                                                    <i class="bi bi-trash-fill"></i>
                                                </a>
                                            </div>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listSP}">
                                <tr>
                                    <td colspan="9" class="empty-row">
                                        Không tìm thấy sản phẩm nào phù hợp với bộ lọc.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal Sửa Sản Phẩm -->
<c:forEach items="${listSP}" var="sp">
    <div class="modal fade" id="editSPModal${sp.maSP}" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <form action="SanPhamServlet" method="post">
                    <input type="hidden" name="maSP" value="${sp.maSP}">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fw-bold">Sửa Sản Phẩm #${sp.maSP}</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên Sản Phẩm *</label>
                            <input type="text" name="tenSP" class="form-control" required value="${sp.tenSP}">
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Loại Sản Phẩm *</label>
                                <select name="maLoai" class="form-select" required>
                                    <c:forEach items="${listLoai}" var="l">
                                        <option value="${l.maLoai}" ${l.maLoai == sp.maLoai ? 'selected' : ''}>${l.tenLoai}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Nhà Cung Cấp *</label>
                                <select name="maNCC" class="form-select" required>
                                    <c:forEach items="${listNCC}" var="ncc">
                                        <option value="${ncc.maNCC}" ${ncc.maNCC == sp.maNCC ? 'selected' : ''}>${ncc.tenNCC}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label class="form-label">Hãng Sản Xuất</label>
                                <input type="text" name="hangSX" class="form-control" value="${sp.hangSX}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Đơn Vị Tính</label>
                                <input type="text" name="donViTinh" class="form-control" value="${sp.donViTinh}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Bảo Hành (Tháng)</label>
                                <input type="number" name="baoHanh" class="form-control" value="${sp.baoHanh}">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Đơn Giá Nhập *</label>
                                <input type="number" name="donGiaNhap" class="form-control" required step="1000" value="${tnt:raw(sp.donGiaNhap)}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Đơn Giá Bán *</label>
                                <input type="number" name="donGiaBan" class="form-control" required step="1000" value="${tnt:raw(sp.donGiaBan)}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label fw-semibold">Số Lượng Tồn</label>
                                <input type="number" name="soLuong" class="form-control" value="${sp.soLuong}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mô Tả Cấu Hình</label>
                            <textarea name="moTa" class="form-control" rows="2">${sp.moTa}</textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-primary fw-bold">Lưu Thay Đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:forEach>

<!-- Modal Thêm Sản Phẩm -->
<div class="modal fade" id="addSPModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="SanPhamServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold">Thêm Máy Tính Mới</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Tên Sản Phẩm *</label>
                        <input type="text" name="tenSP" class="form-control" required placeholder="VD: Dell XPS 13 9310">
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Loại Sản Phẩm *</label>
                            <select name="maLoai" class="form-select" required>
                                <c:forEach items="${listLoai}" var="l">
                                    <option value="${l.maLoai}">${l.tenLoai}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Nhà Cung Cấp *</label>
                            <select name="maNCC" class="form-select" required>
                                <c:forEach items="${listNCC}" var="ncc">
                                    <option value="${ncc.maNCC}">${ncc.tenNCC}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Hãng Sản Xuất</label>
                            <input type="text" name="hangSX" class="form-control" placeholder="Dell, Asus...">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Đơn Vị Tính</label>
                            <input type="text" name="donViTinh" class="form-control" value="Chiếc">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Bảo Hành (Tháng)</label>
                            <input type="number" name="baoHanh" class="form-control" value="12">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Đơn Giá Nhập *</label>
                            <input type="number" name="donGiaNhap" class="form-control" required step="1000">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Đơn Giá Bán *</label>
                            <input type="number" name="donGiaBan" class="form-control" required step="1000">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Số Lượng Ban Đầu</label>
                            <input type="number" name="soLuong" class="form-control" value="0">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mô Tả Cấu Hình</label>
                        <textarea name="moTa" class="form-control" rows="2"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary fw-bold">Lưu Sản Phẩm</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>