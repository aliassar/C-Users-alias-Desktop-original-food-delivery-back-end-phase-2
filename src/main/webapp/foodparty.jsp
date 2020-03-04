<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Food Party</title>
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

        .old-price {
            text-decoration: line-through;
        }
    </style>
</head>
<body>
<ul>
    <c:forEach var="restaurant" items="${restaurants}">
        <li>menu:
            <ul>
                <li>
                    <img src="${restaurant.logo}" alt="logo">
                    <div><c:out value="${restaurant.name}"/></div>
                    <c:forEach var="food" items="${restaurant.menu}">
                        <li>
                            <img src="${food.image}" alt="logo">
                            <form action="/loghme_war_exploded/cart" method="POST">
                                <div><c:out value="${food.name}"/></div>
                                <div><c:out value="${food.description}"/></div>
                                <div class="old-price"><c:out value="${food.oldPrice}"/> Toman</div>
                                <div><c:out value="${food.price}"/> Toman</div>
                                <div>remaining count:<c:out value="${food.count}"/></div>
                                <input type="hidden" id="name" name="name" value="${food.name}"/>
                                <input type="hidden" id="restaurantName" name="restaurantName" value="${restaurant.name}"/>
                                <input type="hidden" id="price" name="price" value="${food.price}"/>
                                <input type="hidden" id="ID" name="ID" value="${restaurant.id}"/>
                                <input type="hidden" id="type" name="type" value="foodParty"/>
                                <input type="hidden" id="oldPrice" name="oldPrice" value="${food.oldPrice}"/>
                                <input type="hidden" id="count" name="count" value="${food.count}"/>
                                <button type="submit">addToCart</button>
                            </form>
                        </li>
                     </c:forEach>
                <div>popularity: <c:out value="${restaurant.calculatePopularity()}"/></div>
                </li>
            </ul>
        </li>
    </c:forEach>

</ul>
</body>
</html>