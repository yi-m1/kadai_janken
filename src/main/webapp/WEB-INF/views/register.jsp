<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザ登録</title>
<style>
.f-container {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

h1 {
	margin-top: 80px;
	margin-bottom: 45px;
}

label {
	display: inline-block;
	margin-bottom: 15px;
	width: 150px;
	vertical-align: middle;
}

input[type="text"] {
	width: 300px;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 16px;
}

button {
	margin-left: 25%;
	padding: 10px 20px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	width: 50%;
}

button:hover {
	background-color: #45a049;
}

.link {
	margin-top: 40px;
}

.error {
	color: red;
	margin-bottom: 10px; 
}
</style>
</head>
<body>
	<div class="f-container">
		<h1>新規ユーザ登録</h1>
		<% if (request.getAttribute("registerError") != null) { %>
		<p class="error"><%= request.getAttribute("registerError") %></p>
		<% } %>

		<form action="<%= request.getContextPath() %>/register" method="post">
			<label for="mailAddress">メールアドレス：</label>
			<input type="text" id="mailAddress" name="mailAddress" required><br>
			<% 
            String mailAddressError = (String)request.getAttribute("mailAddressError");
            if (mailAddressError != null) {
            %>
            <span class="error"><%= mailAddressError %></span><br><br>
            <% 
            }
            %>

			<label for="userName">ユーザ名：</label>
			<input type="text" id="userName" name="userName" required><br>
			<% 
            String userNameError = (String)request.getAttribute("userNameError");
            if (userNameError != null) {
                // エラーメッセージを改行で分割
                String[] errorLines = userNameError.split("\n");
            %>
            <span class="error"><%= errorLines[0] %></span><br>
            <% if (errorLines.length > 1) { %>
                <span class="error"><%= errorLines[1] %></span><br><br>
            <% } %>
            <% 
            }
            %>

			<button type="submit">新規登録</button>
		</form>
		<p class="link">ログイン画面に戻るには<a href="<%= request.getContextPath() %>/login">こちら</a>をクリックしてください。</p>
	</div>
</body>
</html>
