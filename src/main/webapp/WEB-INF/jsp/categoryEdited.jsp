<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="jqueryUrl" value="/js/jquery-1.11.3.min.js" />
<c:url var="bootstrapCssUrl" value="/bootstrap-3.3.4/css/bootstrap.css" />
<c:url var="bootstrapJsUrl" value="/bootstrap-3.3.4/js/bootstrap.js" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	    <link rel="stylesheet" href="${bootstrapCssUrl}">
	</head>
	<body>
		<div class="container">
			<h1>Categories</h1>
			<p>You have edited a Category with id ${id} at <%= new java.util.Date() %></p>
			<c:url var="mainUrl" value="/ui/category-ui" />
			<a class="btn btn-primary" href="${mainUrl}" role="button">Return to Main List</a>
		</div>
	
		<script src="${jqueryUrl}"></script>
	    <script src="${bootstrapJsUrl}"></script>
	</body>
</html>