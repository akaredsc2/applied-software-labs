<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
The answer is:<br>
${requestScope.result}
<br>
<a href="<c:url value="/home.jsp"/>">Go home</a>
</body>
</html>
