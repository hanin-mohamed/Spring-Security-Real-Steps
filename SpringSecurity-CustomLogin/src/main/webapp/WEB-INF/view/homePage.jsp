<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        .form-container {
            width: 300px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
        }
        button {
            padding: 10px 20px;
            background-color: #b71d39;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h3>Hello from home</h3>
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> //add csrf token
    <button type="submit" class="logout-btn">Logout</button>
</form>
</body>
</html>