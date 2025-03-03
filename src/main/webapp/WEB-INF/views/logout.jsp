<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.kadai.aws.model.UserInfo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン/ログアウトテスト画面</title>
</head>
<body>
	<p>ユーザ情報を取り出せているか確認するためのテストページです。</p>
	<%
	UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
	%>
	<p>ユーザID:<%=userInfo.getUserId()%></p>
	<p>ユーザ名:<%=userInfo.getUserName()%></p>
	<p>メールアドレス:<%=userInfo.getMailAddress()%></p>
	<form action="<%=request.getContextPath()%>/logout" method="post">
		<div>
			<button type="submit">ログアウト</button>
		</div>
	</form>
</body>
</html>