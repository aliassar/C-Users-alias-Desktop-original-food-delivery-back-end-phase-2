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
        <li>id: 1</li>
        <li>name: restaurant 1 name</li>
        <li>location: (3, 5)</li>
        <li>logo: <img src="url of restaurant image" alt="logo"></li>

        <!-- IN CASE YOU WANT SOME BONUS : -->
        <!-- <li>estimated delivery time: 10 min 2 sec </li> -->

        <li>menu: 
        	<ul>
        		<li>
                    <img src="url of food image" alt="logo">
                    <div>food1</div>
                    <div>20000 Toman</div>
                    <form action="" method="POST">
                        <button type="submit">addToCart</button>
                    </form>
                </li>
                <li>
                    <img src="url of food image" alt="logo">
                    <div>food2</div>
                    <div>40000 Toman</div>
                    <form action="" method="POST">
                        <button type="submit">addToCart</button>
                    </form>
                </li>
        	</ul>
        </li>
    </ul>
</body>
</html>