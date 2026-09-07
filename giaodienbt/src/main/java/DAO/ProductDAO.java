/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // Khởi tạo danh sách tĩnh lưu trong RAM
    private static final List<Product> listProducts = new ArrayList<>();
    private static int autoIncrementId = 1;

    // Dữ liệu mẫu sẵn có
    static {
        listProducts.add(new Product(autoIncrementId++, "Laptop Asus ROG Strix G16", 285000, 10, "Laptop", "asus.jpg", "Core i7 13650HX, RAM 16GB, RTX 4060"));
        listProducts.add(new Product(autoIncrementId++, "PC Gaming i9 13900K", 45000000, 4, "PC", "pc.jpg", "i9 13900K, RAM 32GB, RTX 4080"));
        listProducts.add(new Product(autoIncrementId++, "Card màn hình RTX 4070 Ti", 215000, 8, "Linh kiện", "vga.jpg", "VRAM 12GB GDDR6X"));
    }

    // Lấy toàn bộ danh sách
    public List<Product> getAllProducts() {
        return listProducts;
    }

    // Tìm sản phẩm theo ID
    public Product getProductById(int id) {
        for (Product p : listProducts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Thêm sản phẩm mới
    public void insertProduct(Product product) {
        product.setId(autoIncrementId++);
        listProducts.add(product);
    }

    // Cập nhật/Sửa sản phẩm
    public void updateProduct(Product product) {
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getId() == product.getId()) {
                listProducts.set(i, product);
                break;
            }
        }
    }

    // Xóa sản phẩm theo ID
    public void deleteProduct(int id) {
        listProducts.removeIf(p -> p.getId() == id);
    }
}