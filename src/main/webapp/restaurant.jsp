<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurant</title>
    <style>
        img {
        	width: 50px;
        	height: 50px;
        }
        li {
            display: flex;
            flex-direction: row;
        	padding: 0 0 5px;
        }
        div, form {
            padding: 0 5px
        }
    </style>
</head>
<body>
<ul>
    <li>id: <c:out value="${restaurant.id}"/></li>
    <li>name: <c:out value="${restaurant.name}"/></li>
    <li>location: (<c:out value="${restaurant.location.x}"/>, <c:out value="${restaurant.location.y}"/>)</li>
    <li>logo: <img src="${restaurant.logo}" alt="logo"></li>
    <li>menu:
        <ul>
            <c:forEach var="food" items="${restaurant.menu}">
            <li>
                <img src="${food.image}" alt="logo">
                <div><c:out value="${food.name}"/></div>
                <div><c:out value="${food.price}"/> Toman</div>
                <form action="/loghme_war_exploded/cart"  method="POST">
                    <input type="hidden" id="name" name="name" value="${food.name}" />
                    <input type="hidden" id="restaurantName" name="restaurantName" value="${restaurant.name}" />
                    <input type="hidden" id="price" name="price" value="${food.price}" />
                    <button type="submit">addToCart</button>
                </form>
            </li>
            </c:forEach>
        </ul>

    </li>
</ul>
</body>
</html>