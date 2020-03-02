<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li, div, form {
        	padding: 5px
        }
    </style>
</head>
<body>
    <div>restaurant name</div>
    <ul>
        <c:forEach var="order" items="${cart.orders}">
            <li><c:out value="${order.foodName}"/>:‌ <c:out value="${order.numOfOrder}"/></li>

        </c:forEach>
    </ul>
    <div>
        status : <c:out value="${cart.status}"/>
    </div>
</body>
</html>