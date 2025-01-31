<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザ登録</title>
</head>
<body>
	<h1>新規ユーザ登録</h1>
	<% if (request.getAttribute("registerError") != null) { %>
    <p style="color: red;"><%= request.getAttribute("registerError") %></p>
	<% } %>
	
	<form action="<%= request.getContextPath() %>/register" method="post">
		<label for="mailAddress">メールアドレス：</label>
		<input type="text" id="mailAddress" name="mailAddress" required><br>
		<% 
           String mailAddressError = (String)request.getAttribute("mailAddressError");
           if (mailAddressError != null) {
         %>
        <span><%= mailAddressError %></span><br>
        <% 
           }
        %>

		<label for="userName">ユーザ名：</label>
		<input type="text" id="userName" name="userName" required><br>
		<% 
           String userNameError = (String)request.getAttribute("userNameError");
           if (userNameError != null) {
         %>
        <span><%= userNameError %></span><br>
        <% 
           }
        %>
        
		<button type= submit">新規登録</button>
	</form>
	<p>ログイン画面に戻るには<a href="<%= request.getContextPath() %>/login">こちら</a>をクリックしてください。</p>
</body>
</html>