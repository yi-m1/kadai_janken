<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" href="resources/login.css">
</head>
<body>
    <h1>じゃんけんアプリ</h1>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
	<% } %>
	
    <form action="<%= request.getContextPath() %>/login" method="post">
        メールアドレス: <input type="text" name="mailAdress" required><br>
        <% 
           String mailAdressError = (String) request.getAttribute("mailAdressError");
           if (mailAdressError != null) {
         %>
        <span><%= mailAdressError %></span><br>
        <% 
           }
        %>
        ユーザID: <input type="text" name="userId" required><br>
        <% 
           String userIdError = (String) request.getAttribute("userIdError");
           if (userIdError != null) {
         %>
        <span><%= userIdError %></span>
        <% 
           }
        %>
        <div>
        	<button type="submit">ログイン</button>
        </div>
    </form>
</body>
</html>