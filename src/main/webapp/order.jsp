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
        <li>food 1:‌ 2</li>
        <li>food 2: 3</li>
        <li>food 3: 1</li>
    </ul>
    <!-- One of these states -->
    <div>
        status : finding delivery
    </div>
    <!-- or -->
    <div>
        <div>status : delivering</div>
        <div>remained time : 10 min 12 sec</div>
    </div>
    <!-- or -->
    <div>
        status : done
    </div>
</body>
</html>