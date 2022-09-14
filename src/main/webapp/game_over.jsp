<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Конец игры</title>
</head>
<body>
<c:set var="variant" scope="page" value="<%= request.getParameter(\"variant\") %>"/>
<c:set var="currentPage" scope="page" value="${pages.get(pageNumber)}"/>

<c:if test="${variant.equals(\"yes\")}">
    <c:set var="gameOver" scope="page" value="Тебя вернули домой. Победа"/>
</c:if>
<c:if test="${variant.equals(\"no\")}">
    <c:set var="gameOver" scope="page" value="${currentPage.getGameOver()}"/>
</c:if>
<h2>${gameOver}</h2>
<input type="button" value="Начать сначала" onclick="window.location = '/restart'">
</body>
</html>
