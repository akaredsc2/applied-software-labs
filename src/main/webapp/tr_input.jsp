<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="tr">
    <c:forEach var="i" begin="1" end="${param.amountCount}">
        Cost ${i}:
        <c:forEach var="j" begin="1" end="${param.demandsCount}">
            <input name="costs${i}${j}" type="number" step="any" min="0" max="2147483647" required>
        </c:forEach>
        Amount:
        <input name="amount${i}" type="number" step="any" min="0" max="2147483647" required>
        <br>
    </c:forEach>
    <br><br><br>
    <c:forEach var="k" begin="1" end="${param.demandsCount}">
        Demands ${k}:
        <input name="demand${k}" type="number" step="any" min="0" max="2147483647" required>
        <br>
    </c:forEach>
    <input type="hidden" name="amountCount" value="${param.amountCount}">
    <input type="hidden" name="demandsCount" value="${param.demandsCount}">
    <input type="submit" value="optimize">
</form>
</body>
</html>
