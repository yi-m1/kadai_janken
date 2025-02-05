<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
    <h1>じゃんけんアプリ ログイン</h1>
    <% if (request.getAttribute("loginError") != null) { %>
    <p style="color: red;"><%= request.getAttribute("loginError") %></p>
	<% } %>
	
    <form action="<%= request.getContextPath() %>/login" method="post">
    	<label for="mailAddress">メールアドレス：</label>
        <input type="text" id="mailAddress" name="mailAddress" required><br>
        <% 
           String mailAddressError = (String) request.getAttribute("mailAddressError");
           if (mailAddressError != null) {
         %>
        <span><%= mailAddressError %></span><br>
        <% 
           }
        %>
        <button type="submit">ログイン</button>
    </form>
    <p>新規ユーザ登録は<a href="<%= request.getContextPath() %>/register">こちら</a>をクリックしてください。</p>
</body>
</html>