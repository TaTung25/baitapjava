<%--
    Document   : index
    Mô tả      : Trang Thống Kê tổng hợp của hệ thống kho TNT.
                 Toàn bộ số liệu lấy trực tiếp từ database qua BaoCaoServlet.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="tnt" uri="http://tnt.com/functions" %>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid py-4 px-4">
    <div class="row g-4">
        <div class="col-lg-2">
            <%@ include file="../includes/sidebar.jsp" %>
        </div>

        <div class="col-lg-10">

            <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                <div>
                    <h3 class="fw-bold mb-1">Thống Kê Kho Hàng TNT</h3>
                    <p class="text-muted small mb-0">
                        Đang xem theo <strong>${tenKy}</strong>, từ
                        <strong>${tuNgayHienThi}</strong> đến <strong>${denNgayHienThi}</strong>
                    </p>
                </div>
            </div>

            <!-- ============================================================
                 BỘ LỌC THỜI GIAN
                 ============================================================ -->
            <form action="${pageContext.request.contextPath}/BaoCaoServlet" method="get" class="filter-bar mb-4">
                <input type="hidden" name="tonToiThieu" value="${tonToiThieu}">
                <input type="hidden" name="tuKhoaTon" value="<c:out value='${tuKhoaTon}'/>">
                <input type="hidden" name="maLoaiTon" value="${maLoaiTon}">

                <div class="row g-3 align-items-end">
                    <div class="col-md-3">
                        <label class="form-label">Thống kê theo</label>
                        <select name="ky" class="form-select">
                            <option value="ngay"  ${ky == 'ngay'  ? 'selected' : ''}>Ngày</option>
                            <option value="tuan"  ${ky == 'tuan'  ? 'selected' : ''}>Tuần</option>
                            <option value="thang" ${ky == 'thang' ? 'selected' : ''}>Tháng</option>
                            <option value="quy"   ${ky == 'quy'   ? 'selected' : ''}>Quý</option>
                            <option value="nam"   ${ky == 'nam'   ? 'selected' : ''}>Năm</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Từ ngày</label>
                        <input type="date" name="tuNgay" class="form-control" value="${tuNgay}">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Đến ngày</label>
                        <input type="date" name="denNgay" class="form-control" value="${denNgay}">
                    </div>
                    <div class="col-md-1">
                        <label class="form-label">Top</label>
                        <select name="topN" class="form-select">
                            <option value="5"  ${topN == 5  ? 'selected' : ''}>5</option>
                            <option value="10" ${topN == 10 ? 'selected' : ''}>10</option>
                            <option value="20" ${topN == 20 ? 'selected' : ''}>20</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary fw-bold w-100">
                                <i class="bi bi-funnel-fill me-1"></i>Lọc
                            </button>
                            <a href="${pageContext.request.contextPath}/BaoCaoServlet"
                               class="btn btn-outline-secondary" title="Đặt lại bộ lọc">
                                <i class="bi bi-arrow-counterclockwise"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </form>

            <!-- ============================================================
                 THẺ SỐ LIỆU TỔNG QUAN
                 ============================================================ -->
            <div class="row g-3 mb-4">
                <div class="col-md-3">
                    <div class="kpi-card kpi-chi">
                        <span class="kpi-label">Tổng Chi Nhập Kho</span>
                        <div class="kpi-value text-danger">${tnt:vnd(tongChi)}</div>
                        <p class="kpi-sub"><i class="bi bi-receipt me-1"></i>${soPhieuNhap} phiếu nhập</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="kpi-card kpi-thu">
                        <span class="kpi-label">Tổng Thu Xuất Kho</span>
                        <div class="kpi-value text-success">${tnt:vnd(tongThu)}</div>
                        <p class="kpi-sub"><i class="bi bi-receipt me-1"></i>${soPhieuXuat} phiếu xuất</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="kpi-card kpi-loi">
                        <span class="kpi-label">Lợi Nhuận Gộp</span>
                        <div class="kpi-value ${loiNhuan >= 0 ? 'text-primary' : 'text-danger'}">
                            ${tnt:vndCoDau(loiNhuan)}
                        </div>
                        <p class="kpi-sub">
                            <c:choose>
                                <c:when test="${coSoSanh and tangTruongCuoi > 0}">
                                    <span class="trend-up"><i class="bi bi-arrow-up-right"></i>
                                        ${tnt:phanTram(tangTruongCuoi)}</span> so với ${tenKy} trước
                                </c:when>
                                <c:when test="${coSoSanh and tangTruongCuoi < 0}">
                                    <span class="trend-down"><i class="bi bi-arrow-down-right"></i>
                                        ${tnt:phanTram(tangTruongCuoi)}</span> so với ${tenKy} trước
                                </c:when>
                                <c:otherwise>
                                    <span class="trend-flat">Chưa đủ dữ liệu so sánh</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="kpi-card kpi-kho">
                        <span class="kpi-label">Giá Trị Tồn Kho</span>
                        <div class="kpi-value text-warning">${tnt:vnd(tongQuanKho.giaTriTon)}</div>
                        <p class="kpi-sub">
                            <i class="bi bi-boxes me-1"></i>${tnt:so(tongQuanKho.tongTon)} sản phẩm /
                            ${tnt:so(tongQuanKho.soMatHang)} mặt hàng
                        </p>
                    </div>
                </div>
            </div>

            <!-- ============================================================
                 BIỂU ĐỒ MIỀN - TĂNG GIẢM DÒNG TIỀN
                 ============================================================ -->
            <div class="card border-0 shadow-sm p-3 mb-4">
                <div class="d-flex justify-content-between align-items-center mb-2 flex-wrap gap-2">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-graph-up-arrow text-primary me-2"></i>Biểu Đồ Miền Tăng Giảm Dòng Tiền
                    </h5>
                    <span class="badge bg-primary-subtle text-primary">Theo ${tenKy}</span>
                </div>
                <div class="chart-box chart-box-lg">
                    <c:choose>
                        <c:when test="${empty thongKeKy}">
                            <div class="chart-empty">Không có dữ liệu giao dịch trong khoảng thời gian đã chọn.</div>
                        </c:when>
                        <c:otherwise>
                            <canvas id="chartDongTien"></canvas>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- ============================================================
                 BIỂU ĐỒ TRÒN LOẠI SP BÁN CHẠY + CỘT SẢN PHẨM BÁN CHẠY
                 ============================================================ -->
            <div class="row g-4 mb-4">
                <div class="col-md-5">
                    <div class="card border-0 shadow-sm p-3 h-100">
                        <h5 class="fw-bold text-center mb-3">
                            <i class="bi bi-pie-chart-fill text-primary me-2"></i>Loại Sản Phẩm Bán Chạy Nhất
                        </h5>
                        <div class="chart-box">
                            <c:choose>
                                <c:when test="${empty topLoai}">
                                    <div class="chart-empty">Chưa có sản phẩm nào được bán ra trong kỳ.</div>
                                </c:when>
                                <c:otherwise>
                                    <canvas id="chartLoaiBanChay"></canvas>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-7">
                    <div class="card border-0 shadow-sm p-3 h-100">
                        <h5 class="fw-bold text-center mb-3">
                            <i class="bi bi-trophy-fill text-warning me-2"></i>Top ${topN} Sản Phẩm Bán Chạy
                        </h5>
                        <div class="chart-box">
                            <c:choose>
                                <c:when test="${empty topSanPham}">
                                    <div class="chart-empty">Chưa có sản phẩm nào được bán ra trong kỳ.</div>
                                </c:when>
                                <c:otherwise>
                                    <canvas id="chartSanPhamBanChay"></canvas>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ============================================================
                 BẢNG CHI TIẾT THEO NGÀY / TUẦN / THÁNG / QUÝ / NĂM
                 ============================================================ -->
            <div class="card table-modern border-0 mb-4">
                <div class="card-header bg-white py-3 border-0 d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-table text-secondary me-2"></i>Chi Tiết Thống Kê Theo ${tenKy}
                    </h5>
                    <span class="text-muted small">Số liệu tổng hợp từ phiếu nhập và phiếu xuất</span>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">${tenKy}</th>
                                <th class="text-center">Số Phiếu Nhập</th>
                                <th class="text-center">Số Phiếu Xuất</th>
                                <th class="text-end">Tiền Chi Nhập</th>
                                <th class="text-end">Tiền Thu Xuất</th>
                                <th class="text-end">Lợi Nhuận</th>
                                <th class="text-end pe-3">Tăng/Giảm</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${thongKeKy}" var="k">
                                <tr>
                                    <td class="ps-3 fw-bold text-primary text-nowrap">${k.nhan}</td>
                                    <td class="text-center">${k.soPhieuNhap}</td>
                                    <td class="text-center">${k.soPhieuXuat}</td>
                                    <td class="text-end text-danger text-nowrap">${tnt:vnd(k.tongNhap)}</td>
                                    <td class="text-end text-success text-nowrap">${tnt:vnd(k.tongXuat)}</td>
                                    <td class="text-end fw-bold text-nowrap ${k.loiNhuan >= 0 ? 'text-success' : 'text-danger'}">
                                        ${tnt:vndCoDau(k.loiNhuan)}
                                    </td>
                                    <td class="text-end pe-3 text-nowrap">
                                        <c:choose>
                                            <c:when test="${not k.coKyTruoc}">
                                                <span class="trend-flat">—</span>
                                            </c:when>
                                            <c:when test="${k.tangTruong > 0}">
                                                <span class="trend-up"><i class="bi bi-caret-up-fill"></i>
                                                    ${tnt:phanTram(k.tangTruong)}</span>
                                            </c:when>
                                            <c:when test="${k.tangTruong < 0}">
                                                <span class="trend-down"><i class="bi bi-caret-down-fill"></i>
                                                    ${tnt:phanTram(k.tangTruong)}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="trend-flat">0%</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty thongKeKy}">
                                <tr><td colspan="7" class="empty-row">Không có dữ liệu trong khoảng thời gian đã chọn.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- ============================================================
                 HÀNG TỒN KHO CÒN NHIỀU
                 ============================================================ -->
            <div class="card table-modern border-0 mb-4">
                <div class="card-header bg-white py-3 border-0">
                    <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                        <h5 class="fw-bold mb-0">
                            <i class="bi bi-box-seam-fill text-warning me-2"></i>Hàng Tồn Kho Còn Nhiều
                        </h5>
                        <span class="badge bg-warning-subtle text-warning-emphasis">
                            Tồn từ ${tonToiThieu} sản phẩm trở lên
                        </span>
                    </div>

                    <form action="${pageContext.request.contextPath}/BaoCaoServlet" method="get">
                        <input type="hidden" name="ky" value="${ky}">
                        <input type="hidden" name="tuNgay" value="${tuNgay}">
                        <input type="hidden" name="denNgay" value="${denNgay}">
                        <input type="hidden" name="topN" value="${topN}">

                        <div class="row g-2 align-items-end">
                            <div class="col-md-3">
                                <label class="form-label small fw-semibold mb-1">Tồn tối thiểu</label>
                                <input type="number" name="tonToiThieu" class="form-control" min="0"
                                       value="${tonToiThieu}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label small fw-semibold mb-1">Tìm sản phẩm / hãng</label>
                                <input type="text" name="tuKhoaTon" class="form-control"
                                       placeholder="VD: Dell, Laptop Gaming..."
                                       value="<c:out value='${tuKhoaTon}'/>">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-semibold mb-1">Loại sản phẩm</label>
                                <select name="maLoaiTon" class="form-select">
                                    <option value="0">-- Tất cả loại --</option>
                                    <c:forEach items="${listLoai}" var="l">
                                        <option value="${l.maLoai}" ${maLoaiTon == l.maLoai ? 'selected' : ''}>
                                            ${l.tenLoai}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <button type="submit" class="btn btn-warning fw-bold w-100">
                                    <i class="bi bi-search me-1"></i>Lọc Tồn Kho
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Mã SP</th>
                                <th>Tên Sản Phẩm</th>
                                <th>Loại</th>
                                <th>Nhà Cung Cấp</th>
                                <th class="text-center">Tồn Kho</th>
                                <th class="text-center">Đã Bán</th>
                                <th class="text-end">Giá Trị Tồn</th>
                                <th class="pe-3">Đánh Giá</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${tonNhieu}" var="t">
                                <tr>
                                    <td class="ps-3 fw-semibold">#${t.maSP}</td>
                                    <td>
                                        <span class="fw-bold text-primary">${t.tenSP}</span>
                                        <c:if test="${not empty t.hangSX}">
                                            <br><span class="text-muted small">${t.hangSX}</span>
                                        </c:if>
                                    </td>
                                    <td>${t.tenLoai}</td>
                                    <td>${t.tenNCC}</td>
                                    <td class="text-center">
                                        <span class="badge bg-warning badge-stock">${tnt:so(t.soLuong)}</span>
                                    </td>
                                    <td class="text-center">${tnt:so(t.daBan)}</td>
                                    <td class="text-end fw-bold text-nowrap">${tnt:vnd(t.giaTriTon)}</td>
                                    <td class="pe-3">
                                        <c:choose>
                                            <c:when test="${t.daBan == 0}">
                                                <span class="badge bg-danger">${t.canhBao}</span>
                                            </c:when>
                                            <c:when test="${t.tyLeLuanChuyen < 20}">
                                                <span class="badge bg-danger-subtle text-danger-emphasis">${t.canhBao}</span>
                                            </c:when>
                                            <c:when test="${t.tyLeLuanChuyen < 50}">
                                                <span class="badge bg-warning-subtle text-warning-emphasis">${t.canhBao}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-success-subtle text-success">${t.canhBao}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty tonNhieu}">
                                <tr><td colspan="8" class="empty-row">
                                    Không có mặt hàng nào tồn từ ${tonToiThieu} sản phẩm trở lên.
                                </td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- ============================================================
                 XẾP HẠNG NHÀ CUNG CẤP VIP
                 ============================================================ -->
            <div class="card table-modern border-0 mb-4">
                <div class="card-header bg-white py-3 border-0 d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-truck text-primary me-2"></i>Top ${topN} Nhà Cung Cấp Giao Dịch Nhiều Nhất
                    </h5>
                    <span class="text-muted small">Điểm uy tín = 70% doanh số + 30% số lần giao dịch</span>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Hạng</th>
                                <th>Nhà Cung Cấp</th>
                                <th>Liên Hệ</th>
                                <th class="text-center">Số Đơn Nhập</th>
                                <th class="text-end">Tổng Giá Trị</th>
                                <th class="text-end">TB/Đơn</th>
                                <th class="text-center">Lần Cuối</th>
                                <th>Uy Tín</th>
                                <th class="pe-3">Xếp Hạng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${topNCC}" var="v">
                                <tr>
                                    <td class="ps-3">
                                        <span class="rank-badge rank-${v.thuHang}">${v.thuHang}</span>
                                    </td>
                                    <td class="fw-bold text-primary">${v.ten}</td>
                                    <td class="small text-muted">
                                        <c:if test="${not empty v.soDienThoai}">
                                            <i class="bi bi-telephone me-1"></i>${v.soDienThoai}<br>
                                        </c:if>
                                        <c:if test="${not empty v.email}">
                                            <i class="bi bi-envelope me-1"></i>${v.email}
                                        </c:if>
                                    </td>
                                    <td class="text-center fw-semibold">${v.soGiaoDich}</td>
                                    <td class="text-end fw-bold text-danger text-nowrap">${tnt:vnd(v.tongTien)}</td>
                                    <td class="text-end text-nowrap">${tnt:vnd(v.giaTriTrungBinh)}</td>
                                    <td class="text-center text-nowrap small">
                                        <fmt:formatDate value="${v.lanGiaoDichCuoi}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        <div class="uytin-wrap">
                                            <span class="uytin-bar">
                                                <span class="uytin-fill" style="width: ${v.diemUyTin}%"></span>
                                            </span>
                                            <span class="uytin-so">${v.diemUyTin}</span>
                                        </div>
                                    </td>
                                    <td class="pe-3">
                                        <span class="badge ${v.mauHang}">${v.hangVip}</span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty topNCC}">
                                <tr><td colspan="9" class="empty-row">
                                    Chưa có phiếu nhập nào trong khoảng thời gian đã chọn.
                                </td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- ============================================================
                 XẾP HẠNG KHÁCH HÀNG VIP
                 ============================================================ -->
            <div class="card table-modern border-0 mb-4">
                <div class="card-header bg-white py-3 border-0 d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-people-fill text-success me-2"></i>Top ${topN} Khách Hàng Giao Dịch Nhiều Nhất
                    </h5>
                    <span class="text-muted small">Điểm uy tín = 70% doanh số + 30% số lần giao dịch</span>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Hạng</th>
                                <th>Khách Hàng</th>
                                <th>Liên Hệ</th>
                                <th class="text-center">Số Đơn Mua</th>
                                <th class="text-end">Tổng Chi Tiêu</th>
                                <th class="text-end">TB/Đơn</th>
                                <th class="text-center">Lần Cuối</th>
                                <th>Uy Tín</th>
                                <th class="pe-3">Xếp Hạng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${topKH}" var="v">
                                <tr>
                                    <td class="ps-3">
                                        <span class="rank-badge rank-${v.thuHang}">${v.thuHang}</span>
                                    </td>
                                    <td class="fw-bold text-primary">${v.ten}</td>
                                    <td class="small text-muted">
                                        <c:if test="${not empty v.soDienThoai}">
                                            <i class="bi bi-telephone me-1"></i>${v.soDienThoai}<br>
                                        </c:if>
                                        <c:if test="${not empty v.email}">
                                            <i class="bi bi-envelope me-1"></i>${v.email}
                                        </c:if>
                                    </td>
                                    <td class="text-center fw-semibold">${v.soGiaoDich}</td>
                                    <td class="text-end fw-bold text-success text-nowrap">${tnt:vnd(v.tongTien)}</td>
                                    <td class="text-end text-nowrap">${tnt:vnd(v.giaTriTrungBinh)}</td>
                                    <td class="text-center text-nowrap small">
                                        <fmt:formatDate value="${v.lanGiaoDichCuoi}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        <div class="uytin-wrap">
                                            <span class="uytin-bar">
                                                <span class="uytin-fill" style="width: ${v.diemUyTin}%"></span>
                                            </span>
                                            <span class="uytin-so">${v.diemUyTin}</span>
                                        </div>
                                    </td>
                                    <td class="pe-3">
                                        <span class="badge ${v.mauHang}">${v.hangVip}</span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty topKH}">
                                <tr><td colspan="9" class="empty-row">
                                    Chưa có phiếu xuất nào trong khoảng thời gian đã chọn.
                                </td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- ============================================================
                 BẢNG CHI TIẾT LOẠI SẢN PHẨM BÁN CHẠY
                 ============================================================ -->
            <div class="card table-modern border-0">
                <div class="card-header bg-white py-3 border-0">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-bar-chart-line-fill text-primary me-2"></i>Chi Tiết Loại Sản Phẩm Bán Chạy
                    </h5>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="ps-3">Hạng</th>
                                <th>Loại Sản Phẩm</th>
                                <th class="text-center">Số Lượng Bán</th>
                                <th class="text-center">Số Đơn</th>
                                <th class="text-end">Doanh Thu</th>
                                <th class="text-end pe-3">Tỷ Trọng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${topLoai}" var="l" varStatus="st">
                                <tr>
                                    <td class="ps-3">
                                        <span class="rank-badge rank-${st.index + 1}">${st.index + 1}</span>
                                    </td>
                                    <td class="fw-bold text-primary">${l.ten}</td>
                                    <td class="text-center fw-semibold">${tnt:so(l.soLuongBan)}</td>
                                    <td class="text-center">${l.soLanBan}</td>
                                    <td class="text-end fw-bold text-success text-nowrap">${tnt:vnd(l.doanhThu)}</td>
                                    <td class="text-end pe-3">
                                        <div class="uytin-wrap">
                                            <span class="uytin-bar">
                                                <span class="uytin-fill" style="width: ${tnt:raw(l.tyLe)}%"></span>
                                            </span>
                                            <span class="uytin-so">${tnt:phanTram(l.tyLe)}</span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty topLoai}">
                                <tr><td colspan="6" class="empty-row">Chưa có dữ liệu bán hàng trong kỳ.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
<script>
    (function () {
        'use strict';

        if (typeof Chart === 'undefined') {
            console.warn('Không tải được thư viện Chart.js, các bảng số liệu vẫn hiển thị bình thường.');
            return;
        }

        // ---------- Dữ liệu lấy từ database qua BaoCaoServlet ----------
        var nhanKy = ${empty jsonNhanKy ? '[]' : jsonNhanKy};
        var duLieuNhap = ${empty jsonNhap ? '[]' : jsonNhap};
        var duLieuXuat = ${empty jsonXuat ? '[]' : jsonXuat};
        var duLieuLoiNhuan = ${empty jsonLoiNhuan ? '[]' : jsonLoiNhuan};
        var nhanLoai = ${empty jsonNhanLoai ? '[]' : jsonNhanLoai};
        var soLuongLoai = ${empty jsonSoLuongLoai ? '[]' : jsonSoLuongLoai};
        var nhanSanPham = ${empty jsonNhanSanPham ? '[]' : jsonNhanSanPham};
        var soLuongSanPham = ${empty jsonSoLuongSanPham ? '[]' : jsonSoLuongSanPham};

        var DON_VI = ' VNĐ';

        /** Định dạng tiền kiểu Việt Nam: 1000000 -> 1.000.000 */
        function dinhDangTien(giaTri) {
            return Number(giaTri || 0).toLocaleString('vi-VN', {maximumFractionDigits: 0});
        }

        /** Rút gọn cho nhãn trục: 1500000 -> 1,5 Tr */
        function rutGon(giaTri) {
            var so = Number(giaTri) || 0;
            var abs = Math.abs(so);
            if (abs >= 1e9) return (so / 1e9).toLocaleString('vi-VN', {maximumFractionDigits: 1}) + ' Tỷ';
            if (abs >= 1e6) return (so / 1e6).toLocaleString('vi-VN', {maximumFractionDigits: 1}) + ' Tr';
            if (abs >= 1e3) return (so / 1e3).toLocaleString('vi-VN', {maximumFractionDigits: 1}) + ' N';
            return dinhDangTien(so);
        }

        var BANG_MAU = [
            '#2563eb', '#f59e0b', '#10b981', '#ef4444', '#8b5cf6',
            '#06b6d4', '#ec4899', '#84cc16', '#f97316', '#6366f1'
        ];

        // =================================================================
        // 1. BIỂU ĐỒ MIỀN - TĂNG GIẢM DÒNG TIỀN
        // =================================================================
        var oDongTien = document.getElementById('chartDongTien');
        if (oDongTien) {
            new Chart(oDongTien.getContext('2d'), {
                type: 'line',
                data: {
                    labels: nhanKy,
                    datasets: [
                        {
                            label: 'Tiền Thu (Xuất Kho)',
                            data: duLieuXuat,
                            borderColor: '#16a34a',
                            backgroundColor: 'rgba(22, 163, 74, 0.22)',
                            fill: true,
                            tension: 0.35,
                            borderWidth: 2,
                            pointRadius: 3,
                            pointHoverRadius: 6
                        },
                        {
                            label: 'Tiền Chi (Nhập Kho)',
                            data: duLieuNhap,
                            borderColor: '#e11d48',
                            backgroundColor: 'rgba(225, 29, 72, 0.18)',
                            fill: true,
                            tension: 0.35,
                            borderWidth: 2,
                            pointRadius: 3,
                            pointHoverRadius: 6
                        },
                        {
                            label: 'Chênh Lệch (Lợi Nhuận)',
                            data: duLieuLoiNhuan,
                            borderColor: '#2563eb',
                            backgroundColor: 'rgba(37, 99, 235, 0.14)',
                            fill: 'origin',
                            tension: 0.35,
                            borderWidth: 2,
                            borderDash: [6, 4],
                            pointRadius: 3,
                            pointHoverRadius: 6
                        }
                    ]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    interaction: {mode: 'index', intersect: false},
                    plugins: {
                        legend: {position: 'top', labels: {usePointStyle: true, boxWidth: 8}},
                        tooltip: {
                            callbacks: {
                                label: function (ctx) {
                                    return ctx.dataset.label + ': ' + dinhDangTien(ctx.parsed.y) + DON_VI;
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {callback: function (v) { return rutGon(v); }},
                            grid: {color: 'rgba(15, 23, 42, 0.06)'}
                        },
                        x: {grid: {display: false}}
                    }
                }
            });
        }

        // =================================================================
        // 2. BIỂU ĐỒ TRÒN - LOẠI SẢN PHẨM BÁN CHẠY NHẤT
        // =================================================================
        var oLoai = document.getElementById('chartLoaiBanChay');
        if (oLoai) {
            new Chart(oLoai.getContext('2d'), {
                type: 'pie',
                data: {
                    labels: nhanLoai,
                    datasets: [{
                        data: soLuongLoai,
                        backgroundColor: BANG_MAU,
                        borderColor: '#ffffff',
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {position: 'bottom', labels: {usePointStyle: true, boxWidth: 8, padding: 12}},
                        tooltip: {
                            callbacks: {
                                label: function (ctx) {
                                    var tong = ctx.dataset.data.reduce(function (a, b) {
                                        return a + Number(b || 0);
                                    }, 0);
                                    var phanTram = tong > 0 ? (ctx.parsed * 100 / tong) : 0;
                                    return ctx.label + ': ' + dinhDangTien(ctx.parsed) + ' sản phẩm ('
                                            + phanTram.toLocaleString('vi-VN', {maximumFractionDigits: 1}) + '%)';
                                }
                            }
                        }
                    }
                }
            });
        }

        // =================================================================
        // 3. BIỂU ĐỒ CỘT NGANG - SẢN PHẨM BÁN CHẠY
        // =================================================================
        var oSanPham = document.getElementById('chartSanPhamBanChay');
        if (oSanPham) {
            new Chart(oSanPham.getContext('2d'), {
                type: 'bar',
                data: {
                    labels: nhanSanPham,
                    datasets: [{
                        label: 'Số lượng đã bán',
                        data: soLuongSanPham,
                        backgroundColor: 'rgba(37, 99, 235, 0.85)',
                        borderRadius: 6,
                        maxBarThickness: 26
                    }]
                },
                options: {
                    indexAxis: 'y',
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {display: false},
                        tooltip: {
                            callbacks: {
                                label: function (ctx) {
                                    return 'Đã bán: ' + dinhDangTien(ctx.parsed.x) + ' sản phẩm';
                                }
                            }
                        }
                    },
                    scales: {
                        x: {beginAtZero: true, grid: {color: 'rgba(15, 23, 42, 0.06)'}},
                        y: {grid: {display: false}}
                    }
                }
            });
        }
    })();
</script>
</body>
</html>
