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
        <li>full name: Ehsan KhamesPanah</li>
        <li>phone number: +989123456789</li>
        <li>email: ekhamespanah@yahoo.com</li>
        <li>credit: 50000 Toman</li>
        <form action="" method="POST">
            <button type="submit">increase</button>
            <label>
                <input type="text" name="credit" value="" />
            </label>
        </form>
        <li>
            Orders : 
            <ul>
                <li>
                    <a href="link to order page">order id : 1</a>
                </li>
                <li>
                    <a href="link to order page">order id : 2</a>
                </li>
            </ul>
        </li>
    </ul>
</body>

</html>