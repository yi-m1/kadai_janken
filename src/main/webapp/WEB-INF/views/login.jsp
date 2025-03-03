<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
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
}
</style>
</head>
<body>
	<div class="f-container">
		<h1>じゃんけんアプリ ログイン</h1>
		<%
		if (request.getAttribute("loginError") != null) {
		%>
		<p class="error"><%=request.getAttribute("loginError")%></p>
		<%
		}
		%>

		<form action="<%=request.getContextPath()%>/login" method="post">
			<label for="mailAddress">メールアドレス：</label>
			<input type="text" name="mailAddress" required><br>
			<%
			String mailAddressError = (String) request.getAttribute("mailAddressError");
			if (mailAddressError != null) {
			%>
			<span class="error"><%=mailAddressError%></span><br>
			<br>
			<%
			}
			%>
			<button type="submit">ログイン</button>
		</form>
		<p class="link">
			新規ユーザ登録は<a href="<%=request.getContextPath()%>/register">こちら</a>をクリックしてください。
		</p>
	</div>
</body>
</html>

