<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

//	Logger logger = LoggerFactory.getLogger("500.jsp");
//	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
	<title>500 - Internal Server Error</title>
</head>

<body>
	<h2>500 - Internal Server Error</h2>
	<p><a href="<c:url value="/index.action"/>">Internal Server Error</a></p>
	<pre>
	<% 
		exception.printStackTrace(new java.io.PrintWriter(out));
	%>
	</pre>
</body>
</html>
