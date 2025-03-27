<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
    <style>
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
<h3>  Hello  </h3>
    <security:authentication property="principal.username"/>
    you are logged in as :  <security:authentication property="principal.authorities"/>
<security:authorize access="hasRole('ADMIN')">
<p>
<a href="${pageContext.request.contextPath}/system"> SystemPage</a>
    for Admin Only.
</p>
</security:authorize>
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit" class="logout-btn">Logout</button>
</form>
</body>
</html>