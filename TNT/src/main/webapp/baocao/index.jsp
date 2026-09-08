<%-- 
    Document   : index
    Created on : Sep 8, 2026, 8:31:22 AM
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
            <h3 class="fw-bold text-secondary mb-4">Thống Kê & Báo Cáo Doanh Thu Kho</h3>

            <div class="row mb-4">
                <div class="col-md-6">
                    <div class="card shadow p-3">
                        <h5 class="fw-bold text-center mb-3">Biểu Đồ So Sánh Nhập / Xuất Kho</h5>
                        <canvas id="importExportChart"></canvas>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card shadow p-3">
                        <h5 class="fw-bold text-center mb-3">Cơ Cấu Loại Sản Phẩm Tồn Kho</h5>
                        <canvas id="categoryPieChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Thêm thư viện Chart.js[cite: 1] -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
// 1. Biểu đồ cột Nhập/Xuất
    const ctx1 = document.getElementById('importExportChart').getContext('2d');
    new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
            datasets: [{
                    label: 'Tổng Chi Nhập Kho (VNĐ)',
                    data: [120000000, 190000000, 30000000, 50000000, 200000000, 150000000],
                    backgroundColor: 'rgba(255, 99, 132, 0.7)'
                }, {
                    label: 'Tổng Thu Xuất Kho (VNĐ)',
                    data: [150000000, 230000000, 80000000, 120000000, 250000000, 180000000],
                    backgroundColor: 'rgba(54, 162, 235, 0.7)'
                }]
        }
    });

// 2. Biểu đồ tròn cơ cấu loại SP[cite: 1]
    const ctx2 = document.getElementById('categoryPieChart').getContext('2d');
    new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: ['Laptop Business', 'Gaming Laptop', 'Linh kiện máy tính'],
            datasets: [{
                    data: [55, 30, 15],
                    backgroundColor: ['#4bc0c0', '#ffcd56', '#ff6384']
                }]
        }
    });
</script>