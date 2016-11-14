<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="res_use">
    <c:forEach var="i" begin="1" end="${param.resourceCount}">
        Resource ${i}:
        <c:forEach var="j" begin="1" end="${param.productCount}">
            <input name="res_per_prod${i}${j}" type="number" step="any" min="0" max="2147483647" required>
        </c:forEach>
        Amount:
            <input name="res_amount${i}" type="number" step="any" min="0" max="2147483647" required>
        <br>
    </c:forEach>
    <br><br><br>
    <c:forEach var="k" begin="1" end="${param.productCount}">
        Proceed ${k}: 
        <input name="proceed_from_prod${k}" type="number" step="any" min="0" max="2147483647" required>
        <br>
    </c:forEach>
    <input type="hidden" name="resourceCount" value="${param.resourceCount}">
    <input type="hidden" name="productCount" value="${param.productCount}">
    <input type="submit" value="optimize">
</form>
</body>
</html>
