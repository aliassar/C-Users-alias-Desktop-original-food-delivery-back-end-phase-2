<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
    <style>
        table {
            text-align: center;
            margin: auto;
        }

        th, td {
            padding: 5px;
            text-align: center;
        }

        .logo {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>logo</th>
        <th>name</th>
        <th>location</th>
    </tr>
    <c:forEach var="restaurant" items="${restaurants}">
        <tr>
            <td><c:out value="${restaurant.id}"/></td>
            <td><img class="logo" src="${restaurant.logo}" alt="logo"></td>
            <td><c:out value="${restaurant.name}"/></td>
            <td>(<c:out value="${restaurant.location.x}"/>, <c:out value="${restaurant.location.y}"/>)</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>