<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="addUrl" value="/ui/category/add" />
<%@ include file="_commonLinks.jsp" %>

<html>
	<c:set var="pageTitle" value="Categories"/>  
	<%@ include file="_commonHeader.jsp" %>
	
	<body>
		<div class="container">
			<%@ include file="menu.jsp" %>
			<h3>${pageTitle}</h3>
			<!-- <table style="border: 1px solid; width: 500px; text-align:center"> -->
<!-- 			<table class="table table-striped table-bordered"> -->
			<table class="table table-striped table-bordered table-condensed">
				<thead style="background:#EBECE4">
					<tr>
						<th colspan="3"><a href="${addUrl}" class="btn" >New</a></th>
					</tr>
					<tr>
						<th>Id</th>
						<th>Category</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${categories}" var="category">
					<c:url var="editUrl" value="/ui/category/edit?id=${category.id}" />
					<c:url var="deleteUrl" value="/ui/category/delete?id=${category.id}" />
					<tr>
						<td><c:out value="${category.id}" /></td>
						<td><c:out value="${category.category}" /></td>
						<td>
							<a href="${editUrl}" class="btn" >Edit</a>
							<a href="${deleteUrl}" class="btn" >Delete</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagination">
				<ul>
				<c:forEach items="${links}" var="link">
					<li><a href="${baseUrl}${link.link}">${link.text}</a>
				</c:forEach>
				</ul>
			</div>
		</div>
		<c:if test="${empty categories}">
			There are currently no categories in the list. <a href="${addUrl}">Add</a> a category.
		</c:if>
	
		<script src="${jqueryUrl}"></script>
	    <script src="${bootstrapJsUrl}"></script>
	</body>
</html>