<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>
<body>

The optimal plan is:
<br>
${requestScope.resultPoint}^T
<br>

Corresponding proceeds are equal to :
<br>
${requestScope.resultFunctionValue}
<br>

<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>