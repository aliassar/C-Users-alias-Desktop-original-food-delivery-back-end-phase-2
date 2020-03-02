<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }
    </style>
</head>

<body>
<ul>
    <li>id: 1</li>
    <li>full name: <c:out value="${user.fname}"/> <c:out value="${user.lname}"/></li>
    <li>phone number: <c:out value="${user.phoneNumber}"/></li>
    <li>email: <c:out value="${user.email}"/></li>
    <li>credit: <c:out value="${user.wallet}"/> Toman</li>
    <form action="/loghme_war_exploded/user" method="POST">
        <button type="submit">increase</button>
        <label>
            <input type="text" name="credit" value=""/>
        </label>
    </form>
    <li>
        Orders :
        <ul>
            <c:forEach var="cart" items="${cartsOfUser}" varStatus="i">
                <li>
                    <a href="/loghme_war_exploded/order/${i.index}">order id : <c:out value="${i.index}"/></a>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>
</body>

</html>