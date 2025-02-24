<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tính tổng hai số</title>
</head>
<body>
    <h1>Xin chào</h1>
    <p>Thời gian hiện tại: <%= new Date() %></p>

    <form action="" method="GET">
        Nhập số a: <input type="text" name="a" value="<%= request.getParameter("a") != null ? request.getParameter("a") : "" %>"><br>
        Nhập số b: <input type="text" name="b" value="<%= request.getParameter("b") != null ? request.getParameter("b") : "" %>"><br>
        <input type="submit" value="Tính tổng">
    </form>

    <% 
        // Lấy giá trị từ request
        String strA = request.getParameter("a");
        String strB = request.getParameter("b");
        
        // Kiểm tra nếu người dùng đã nhập dữ liệu
        if (strA != null && strB != null && !strA.isEmpty() && !strB.isEmpty()) {
            try {
                int a = Integer.parseInt(strA);
                int b = Integer.parseInt(strB);
                int sum = a + b;
    %>
                <p>Tổng của <%= a %> và <%= b %> là: <b><%= sum %></b></p>
    <%
            } catch (NumberFormatException e) {
    %>
                <p style="color: red;">Vui lòng nhập số hợp lệ!</p>
    <%
            }
        }
    %>
</body>
</html>
