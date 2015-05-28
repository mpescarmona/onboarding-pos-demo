<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="mainUrl" value="/ui/categories" />
<c:url var="jqueryUrl" value="/js/jquery-1.10.2.min.js" />
<c:url var="bootstrapCssUrl" value="/bootstrap/css/bootstrap.css" />
<c:url var="bootstrapJsUrl" value="/bootstrap/js/bootstrap.js" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Add a new Category</title>
	    <link rel="stylesheet" href="${bootstrapCssUrl}">
	</head>
	<body>
		<div class="container">
			<h1>Category</h1>
			<p>You have added a new category at <%= new java.util.Date() %></p>
			<p>Return to <a href="${mainUrl}">Main List</a></p>
		</div>
	
		<script src="${jqueryUrl}"></script>
	    <script src="${bootstrapJsUrl}"></script>
	</body>
</html>
