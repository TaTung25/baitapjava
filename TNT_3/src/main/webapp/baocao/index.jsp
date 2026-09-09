<%-- 
    Document   : index
    Created on : Sep 8, 2026, 8:31:22 AM
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
            <h3 class="fw-bold mb-4">Thống Kê Doanh Thu Kho TNT</h3>

            <div class="row g-4">
                <div class="col-md-6">
                    <div class="card border-0 shadow-sm p-3">
                        <h5 class="fw-bold text-center mb-3">So Sánh Nhập / Xuất Kho</h5>
                        <canvas id="importExportChart"></canvas>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card border-0 shadow-sm p-3">
                        <h5 class="fw-bold text-center mb-3">Tỷ Tệ Loại Sản Phẩm Tồn Kho</h5>
                        <canvas id="categoryPieChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const ctx1 = document.getElementById('importExportChart').getContext('2d');
    new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9'],
            datasets: [{
                    label: 'Tổng Chi Nhập Kho (VNĐ)',
                    data: [120000000, 190000000, 150000000, 337000000, 200000000],
                    backgroundColor: 'rgba(239, 68, 68, 0.8)'
                }, {
                    label: 'Tổng Thu Xuất Kho (VNĐ)',
                    data: [150000000, 230000000, 180000000, 142000000, 250000000],
                    backgroundColor: 'rgba(34, 197, 94, 0.8)'
                }]
        }
    });

    const ctx2 = document.getElementById('categoryPieChart').getContext('2d');
    new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: ['Laptop Business', 'Gaming Laptop', 'Màn Hình & Phụ Kiện'],
            datasets: [{
                    data: [50, 30, 20],
                    backgroundColor: ['#2563eb', '#f59e0b', '#10b981']
                }]
        }
    });
</script>
</body>
</html>