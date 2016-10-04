<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Differential equation</title>
</head>
<body>
<form method="post" action="diff_eq">
    Function:
    <br>
    <input type="text" name="function" required>
    From <input type="number" step="any" min="-2147483648" max="2147483647" name="from" required>
    to <input type="number" step="any" min="-2147483648" max="2147483647" name="to" required><br>
    divide by <input type="number" min="1" max="10000" name="steps" required> parts<br>
    initial argument <input type="number" step="any" min="-2147483648" max="2147483647" name="xZero" required><br>
    corresponding function value <input type="number" step="any" min="-2147483648" max="2147483647" name="yZero" required><br>
    <input type="submit" value="solve">
</form>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>
