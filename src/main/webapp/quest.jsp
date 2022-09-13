<%@ page import="entities.QuestPage" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <%@page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Квест</title>
</head>
<body>
<c:set var="currentPage" scope="page" value="${pages.get(pageNumber)}"/>
<h2>${currentPage.getQuestion()}</h2>
<form>
    <label>
        <input type="radio" name="variant" value="yes" required>
    </label>${currentPage.getPositiveAnswer()}<br>
    <label>
        <input type="radio" name="variant" value="no" required>
    </label>${currentPage.getNegativeAnswer()}<br>
    <input type="submit" value="Ответить" formaction="/logic">
</form>
<form id="stats">
    <p>Статистика:</p>
    <p>IP address: <%= session.getAttribute("adress")%></p>
    <p>Имя в игре: <%= session.getAttribute("name")%></p>
    <p>Количество игр: <%= session.getAttribute("numberGames")%></p>
</form>
</body>
</html>
