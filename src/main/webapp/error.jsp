<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h1>Something went wrong</h1>
<c:if test="${!empty requestScope.trouble}">
    Because of
    <br>
    <c:out value="${requestScope.trouble}"/>
</c:if>
<br>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>