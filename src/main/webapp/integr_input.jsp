<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Numeric Integration</title>
</head>
<body>
<form method="post" action="num_integr">
    Function:
    <br>
    <input type="text" name="function" required>
    From <input type="number" step="any" min="-2147483648" max="2147483647" name="from" required>
    to <input type="number" step="any" min="-2147483648" max="2147483647" name="to" required><br>
    With <input type="number" min="1" max="10000" name="steps" required> steps<br>
    <input type="submit" value="integrate">
</form>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>
