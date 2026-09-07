/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/products")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(id);
            response.sendRedirect("products");
            return;
        }
        request.setAttribute("productList", productDAO.getAllProducts());
        request.getRequestDispatcher("product-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String description = request.getParameter("description");

            Part part = request.getPart("image");
            String fileName = uploadImage(part, request);

            Product p = new Product(0, name, price, quantity, category, fileName, description);
            productDAO.insertProduct(p);

        } else if ("update".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String description = request.getParameter("description");

            Part part = request.getPart("image");
            String fileName = uploadImage(part, request);

            if ("default.jpg".equals(fileName) || fileName.isEmpty()) {
                Product oldP = productDAO.getProductById(id);
                if (oldP != null) {
                    fileName = oldP.getImage();
                }
            }

            Product p = new Product(id, name, price, quantity, category, fileName, description);
            productDAO.updateProduct(p);
        }

        response.sendRedirect("products");
    }

    // Hàm hỗ trợ upload file ảnh vào thư mục 'uploads'
    private String uploadImage(Part part, HttpServletRequest request) throws IOException {
        if (part == null || part.getSubmittedFileName() == null || part.getSubmittedFileName().trim().isEmpty()) {
            return "default.jpg";
        }
        String fileName = part.getSubmittedFileName();
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        part.write(uploadPath + File.separator + fileName);
        return fileName;
    }
}
