<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>じゃんけん結果</title>
</head>
<body>
    <h1>じゃんけんの結果</h1>

    <p>あなたの選択: ${userChoice}</p>
    <p>コンピュータの選択: ${computerChoice}</p>
    <p>結果: ${resultMessage}</p>

    <a href="index.jsp">もう一度プレイする</a> <!-- index.jsp に戻るリンク -->
</body>
</html>
