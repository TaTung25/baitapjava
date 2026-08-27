<%-- 
    Document   : ptbac2
    Created on : Aug 25, 2026, 11:10:52 AM
    Author     : 56745654242453456656
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Giải phương trình bậc 2</h1>
        <form action="ptbac2.jsp" method="POST">
            <div class="form-group">
                <label for="a">a:</label>
                <input type="text" id="a" name="a" value="<%= request.getParameter("a") != null ? request.getParameter("a") : ""%>" required>
            </div>
            <div class="form-group">
                <label for="b">b:</label>
                <input type="text" id="b" name="b" value="<%= request.getParameter("b") != null ? request.getParameter("b") : ""%>" required>
            </div>
            <div class="form-group">
                <label for="c">c:</label>
                <input type="text" id="c" name="c" value="<%= request.getParameter("c") != null ? request.getParameter("c") : ""%>" required>
            </div>
            <input type="submit" value="Giải Phương Trình">
        </form>

        <%
            String strA = request.getParameter("a");
            String strB = request.getParameter("b");
            String strC = request.getParameter("c");

            if (strA != null && strB != null && strC != null) {
                out.println("<div class='result'>");
                out.println("<h3>Kết quả:</h3>");
                try {
                    double a = Double.parseDouble(strA);
                    double b = Double.parseDouble(strB);
                    double c = Double.parseDouble(strC);

                    if (a == 0) {
                        if (b == 0) {
                            if (c == 0) {
                                out.println("<p>Phương trình có vô số nghiệm.</p>");
                            } else {
                                out.println("<p>Phương trình vô nghiệm.</p>");
                            }
                        } else {
                            double x = -c / b;
                            out.println("<p>Phương trình bậc nhất có 1 nghiệm: x = " + x + "</p>");
                        }
                    } else {
                        double delta = b * b - 4 * a * c;
                        if (delta < 0) {
                            out.println("<p>Phương trình vô nghiệm.</p>");
                        } else if (delta == 0) {
                            double x = -b / (2 * a);
                            out.println("<p>Phương trình có nghiệm kép: x1 = x2 = " + x + "</p>");
                        } else {
                            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                            double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                            out.println("<p>Phương trình có 2 nghiệm phân biệt:</p>");
                            out.println("<p>x1 = " + x1 + "</p>");
                            out.println("<p>x2 = " + x2 + "</p>");
                        }
                    }
                } catch (NumberFormatException e) {
                    out.println("<p style='color:red;'>Vui lòng nhập hệ số a, b, c là số hợp lệ!</p>");
                }
                out.println("</div>");
            }
        %>
    </body>
</html>
