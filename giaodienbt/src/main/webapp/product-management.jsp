<%-- 
    Document   : product-management
    Created on : Sep 5, 2026, 10:21:26 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quan Ly Kho Hang May Tinh TNT</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    </head>
    <body class="bg-light p-4">

        <div class="container bg-white p-4 rounded shadow-sm">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="m-0 text-primary"><i class="fa-solid fa-desktop me-2"></i>Danh Sách Máy Tính & Linh Kiện</h3>
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addProductModal">
                    <i class="fa-solid fa-plus me-1"></i> Thêm sản phẩm
                </button>
            </div>
            <!-- Thông báo lỗi nếu số lượng hoặc giá bị âm -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fa-solid fa-triangle-exclamation me-2"></i>${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <!-- Bảng danh sách sản phẩm -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th style="width: 50px;">ID</th>
                            <th style="width: 80px;">Hình ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Danh mục</th>
                            <th>Giá bán</th>
                            <th>Tồn kho</th>
                            <th style="width: 140px;" class="text-center">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${productList}">
                            <tr>
                                <td>${p.id}</td>
                                <td class="text-center">
                                    <img src="uploads/${p.image}" 
                                         onerror="this.src='https://via.placeholder.com/60?text=No+Image'" 
                                         width="50" height="50" class="rounded object-fit-cover border">
                                </td>
                                <td><strong>${p.name}</strong></td>
                                <td><span class="badge bg-secondary">${p.category}</span></td>
                                <td class="text-danger fw-bold">
                                    <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="đ"/>
                                </td>
                                <td>${p.quantity}</td>
                                <td class="text-center">
                                    <!-- Nút Sửa -->
                                    <button class="btn btn-warning btn-sm me-1" 
                                            onclick="openEditModal('${p.id}', '${p.name}', '${p.category}', '${p.price}', '${p.quantity}', '${p.description}')">
                                        <i class="fa-solid fa-pen"></i>
                                    </button>
                                    <!-- Nút Xóa -->
                                    <a href="products?action=delete&id=${p.id}" 
                                       class="btn btn-danger btn-sm" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">
                                        <i class="fa-solid fa-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal THÊM Sản Phẩm -->
        <div class="modal fade" id="addProductModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="products" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="add">
                        <div class="modal-header">
                            <h5 class="modal-title">Thêm sản phẩm mới</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Tên sản phẩm</label>
                                <input type="text" name="name" class="form-control" required placeholder="VD: Laptop Dell XPS 13">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Danh mục</label>
                                <select name="category" class="form-select">
                                    <option value="Laptop">Laptop</option>
                                    <option value="PC">PC</option>
                                    <option value="Linh kiện">Linh kiện (CPU/RAM/VGA)</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Giá bán (VNĐ)</label>
                                    <input type="number" name="price" class="form-control" required step="1000">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" name="quantity" class="form-control" min="0" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Hình ảnh</label>
                                <input type="file" name="image" class="form-control" accept="image/*">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mô tả cấu hình</label>
                                <textarea name="description" class="form-control" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Lưu sản phẩm</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal SỬA Sản Phẩm -->
        <div class="modal fade" id="editProductModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="products" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="edit_id">
                        <div class="modal-header">
                            <h5 class="modal-title">Sửa thông tin sản phẩm</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Tên sản phẩm</label>
                                <input type="text" name="name" id="edit_name" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Danh mục</label>
                                <select name="category" id="edit_category" class="form-select">
                                    <option value="Laptop">Laptop</option>
                                    <option value="PC">PC</option>
                                    <option value="Linh kiện">Linh kiện (CPU/RAM/VGA)</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Giá bán (VNĐ)</label>
                                    <input type="number" name="price" id="edit_price" class="form-control" required step="1000">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" name="quantity" id="edit_quantity" class="form-control" min="0" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Hình ảnh</label>
                                <input type="file" name="image" class="form-control" accept="image/*">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mô tả cấu hình</label>
                                <textarea name="description" id="edit_description" class="form-control" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-warning">Cập nhật</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                           // Hàm đẩy dữ liệu vào Modal Sửa
                                           function openEditModal(id, name, category, price, quantity, description) {
                                               document.getElementById('edit_id').value = id;
                                               document.getElementById('edit_name').value = name;
                                               document.getElementById('edit_category').value = category;
                                               document.getElementById('edit_price').value = price;
                                               document.getElementById('edit_quantity').value = quantity;
                                               document.getElementById('edit_description').value = description;

                                               var editModal = new bootstrap.Modal(document.getElementById('editProductModal'));
                                               editModal.show();
                                           }
        </script>
    </body>
</html>