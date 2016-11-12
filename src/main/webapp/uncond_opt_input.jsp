<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Function and Initial Point Input</title>
</head>
<body>
<c:out value="${param.dimension}"/>

<form method="post" action="uncond_opt">
    Function:
    <br>
    <input type="text" name="function" required>
    <br>

    Initial point:
    <br>
    <c:forEach var="k" begin="1" end="${param.dimension}">
        <input name="vec_${k}" type="number" step="any" min="-2147483648" max="2147483647" required>
        <br>
    </c:forEach>
    <br>

    Precision:
    <br>
    <input type="number" step="any" min="0.000001" max="100000" name="precision" required>
    <br>

    Increment:
    <br>
    <input type="number" step="any" min="-2147483648" max="2147483647" name="increment" required>
    <br>

    Acceleration:
    <br>
    <input type="number" step="any" min="-2147483648" max="2147483647" name="acceleration" required>
    <br>

    <input type="hidden" name="dim" value="${param.dimension}">
    <input type="submit" value="solve">
</form>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>

