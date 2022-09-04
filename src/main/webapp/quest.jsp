<%@ page import="entities.QuestPage" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <%@page contentType="text/html; charset=UTF-8" %>
    <title>Квест</title>
</head>
<body>
<%  Map<Integer, QuestPage> pages = (Map<Integer, QuestPage>) session.getAttribute("pages");
    int currentPageNumber = (int) session.getAttribute("pageNumber");
    QuestPage currentPage = pages.get(currentPageNumber);
%>
<h2><%= currentPage.getQuestion() %></h2>
<form>
    <label>
        <input type="radio" name="variant" value="yes">
    </label><%= currentPage.getPositiveAnswer() %><br>
    <label>
        <input type="radio" name="variant" value="no">
    </label><%= currentPage.getNegativeAnswer() %><br>
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
