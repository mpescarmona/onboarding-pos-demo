<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(404);%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
	<title>404 - Not Found</title>
</head>

<body>
	<h2>404 - Not Found</h2>
	<p><a href="<c:url value="/index.action"/>">Not Found</a></p>
</body>
</html>