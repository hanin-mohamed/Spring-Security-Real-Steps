<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        .error {
            color: red;
            font-style: italic;
            text-align: center;
        }
        .success {
            color: green;
            font-style: italic;
            text-align: center;
        }
        button {
            padding: 10px 20px;
            background-color: #4CAF50;
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
<div class="form-container">
    <h3>Login Form</h3>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="success">${success}</div>
    </c:if>
    <form:form action="/auth" method="post">
        <p>
            <label>Username</label>
            <input type="text" name="username" placeholder="Enter username" class="form-control" required/>
        </p>
        <p>
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter password" class="form-control" required/>
        </p>
        <div>
            <button type="submit">Login</button>
        </div>
    </form:form>
</div>
</body>
</html>