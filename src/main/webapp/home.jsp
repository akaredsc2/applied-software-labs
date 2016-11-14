<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <a href="<c:url value="/square_root_method.jsp"/>">Square Root Method for linear systems</a><br>
    <a href="<c:url value="/integr_input.jsp"/>">Numeric integration using right rectangle method</a><br>
    <a href="<c:url value="/diff_eq_input.jsp"/>">Runge-Kutta Third Rank Method for differential equations</a><br>
    <a href="<c:url value="/uncond_opt_dim_input.jsp"/>">Hooke-Jeeves method for unconditional optimization</a><br>
    <a href="<c:url value="/linear_programming_dim_input.jsp"/>">Simplex method for resource use problem</a><br>
</body>
</html>
