<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>じゃんけんゲーム</title>
</head>
<body>
    <h1>じゃんけんゲーム</h1>

    <form action="play" method="post">
        <h3>あなたの手を選んでください</h3>
        <label>
            <input type="radio" name="choice" value="rock" required> グー
        </label><br>
        <label>
            <input type="radio" name="choice" value="scissors"> チョキ
        </label><br>
        <label>
            <input type="radio" name="choice" value="paper"> パー
        </label><br>
        <input type="submit" value="じゃんけん！">
    </form>

    <c:if test="${not empty userChoice}">
        <h3>結果</h3>
        <p>あなたの選択: ${userChoice}</p>
        <p>コンピュータの選択: ${computerChoice}</p>
        <p>結果: ${resultMessage}</p>
    </c:if>

</body>
</html>
