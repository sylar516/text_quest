<%@ page import="entities.QuestPage" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" %>
    <title>Конец игры</title>
</head>
<body>
<%
    String variant = request.getParameter("variant");
    int currentPageNumber = (int) session.getAttribute("pageNumber");
    Map<Integer, QuestPage> pages = (Map<Integer, QuestPage>) session.getAttribute("pages");
    QuestPage currentPage = pages.get(currentPageNumber);

    String gameOver;
    if (variant.equals("yes")) {
        gameOver = "Тебя вернули домой. Победа";
    } else gameOver = currentPage.getGameOver();
%>
<h2><%= gameOver%></h2>
<input type="button" value="Начать сначала" onclick="window.location = '/restart'">
</body>
</html>
