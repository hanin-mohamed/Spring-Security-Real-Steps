<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; }
        .container { width: 300px; margin: 50px auto; padding: 20px; background: white; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; }
        label { display: block; margin: 10px 0 5px; }
        input { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 10px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
<div class="container">
    <h2>Register New User</h2>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <form:form action="/register" method="post" modelAttribute="user">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <label>Username:</label>
        <form:input path="username" required="true"/>

        <label>Password:</label>
        <form:password path="password" required="true"/>

        <label>Confirm Password:</label>
        <form:password path="confirmPassword" required="true"/>

        <label>First Name:</label>
        <form:input path="firstName" required="true"/>

        <label>Last Name:</label>
        <form:input path="lastName" required="true"/>

        <label>Email:</label>
        <form:input path="email" type="email" required="true"/>

        <button type="submit">Register</button>
    </form:form>
</div>
</body>
</html>