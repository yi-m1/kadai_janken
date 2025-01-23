<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
    <h1>じゃんけんアプリ</h1>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
	<% } %>
	
    <form action="login" method="post">
        メールアドレス: <input type="text" name="mailAdress" required><br>
        ユーザID: <input type="text" name="userId" required><br>
        <input type="submit" value="ログイン">
    </form>
</body>
</html>