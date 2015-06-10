<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="saveUrl" value="/ui/category-ui/add" />
<c:url var="listUrl" value="/ui/category-ui" />
<%@ include file="_commonLinks.jsp" %>

<html>
	<c:set var="pageTitle" value="Add a new Category"/>  
	<%@ include file="_commonHeader.jsp" %>
	<body>
		<div class="container">
			<%@ include file="menu.jsp" %>
			<h3>${pageTitle}</h3>
			<form:form class="well form-search" modelAttribute="categoryAttribute" method="POST" action="${saveUrl}">
				<div class="form-group">
					<form:label path="categoryName">Category Name:</form:label>
					<form:input class="form-control" path="categoryName"/>
					<form:errors class="input-medium has-error" path="categoryName"/>
				</div>
				<a class="btn btn-default" href="${listUrl}" role="button">Close</a>
				<input class="btn btn-primary" type="submit" value="Save" />
			</form:form>
		</div>
		
		<script src="${jqueryUrl}"></script>
	    <script src="${bootstrapJsUrl}"></script>
	</body>
</html>