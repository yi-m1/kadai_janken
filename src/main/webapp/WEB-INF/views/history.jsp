<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="jp.co.sfrontier.ss3.janken_game.model.ResultHistory" %>
<% List<ResultHistory> resultHistory = (List<ResultHistory>)request.getAttribute("history"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>じゃんけんゲーム対戦履歴</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
    <div class="mx-auto text-center" style="width: 70%;">
    <h1 class="text-center mb-3">じゃんけんゲーム対戦履歴</h1>
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">対戦日時</th>
                <th scope="col">ユーザ</th>
                <th scope="col">対戦相手</th>
                <th scope="col">対戦結果</th>
            </tr>
        </thead>
        <tbody>
            <% for(ResultHistory rhi : resultHistory){ %>
            <tr>
                <td><%= rhi.getExecuteDatetime() %></td>
                <td><%= rhi.getUserName() %></td>
                <td><%= rhi.getOpponent() %></td>
                <td><%= rhi.getResult() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>
</body>
</html>