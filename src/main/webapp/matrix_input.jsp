<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matrix Input</title>
</head>
<body>
<c:out value="${param.dimension}"/>

<form method="post" action="linear_system">
    <c:forEach var="i" begin="1" end="${param.dimension}">
        <c:forEach var="j" begin="1" end="${param.dimension}">
            <input name="mat_${i}${j}" type="number" step="any" min="-2147483648" max="2147483647" required>
        </c:forEach>
        <br>
    </c:forEach>
    <br><br><br>
    <c:forEach var="k" begin="1" end="${param.dimension}">
        <input name="vec_${k}" type="number" step="any" min="-2147483648" max="2147483647" required>
        <br>
    </c:forEach>
    <input type="hidden" name="dim" value="${param.dimension}">
    <input type="submit" value="solve">
</form>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>
