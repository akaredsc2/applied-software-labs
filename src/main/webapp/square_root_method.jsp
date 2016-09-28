<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<form method="post" action="matrix_input.jsp">
    <input type="number" name="dimension" step="1" min="1" max="10" required>
    <input type="submit" value="done"/>
</form>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>
