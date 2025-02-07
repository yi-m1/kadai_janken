<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン/ログアウトテスト画面</title>
</head>
<body>
	<form action="<%= request.getContextPath() %>/logout" method="post">
        <div>
        	<button type="submit">ログアウト</button>
        </div>
    </form>
</body>
</html>