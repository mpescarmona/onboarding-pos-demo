<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="addUrl" value="/ui/category-ui/add" />
<%@ include file="_commonLinks.jsp" %>

<html>
	<c:set var="pageTitle" value="Categories"/>  
	<%@ include file="_commonHeader.jsp" %>
	
	<body>
		<div class="container">
			<%@ include file="menu.jsp" %>
			<h3>${pageTitle}</h3>
			<table class="table table-striped table-bordered table-condensed table-hover">
				<thead style="background:#EBECE4">
					<tr>
						<th colspan="3"><a class="btn btn-primary" href="${addUrl}" role="button">New</a></th>
					</tr>
					<tr>
						<th>Id</th>
						<th>Category</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${categories}" var="category">
					<c:url var="editUrl" value="/ui/category-ui/edit?id=${category.id}" />
					<c:url var="deleteUrl" value="/ui/category-ui/delete?id=${category.id}" />
					<tr>
						<td><c:out value="${category.id}" /></td>
						<td><c:out value="${category.categoryName}" /></td>
						<td>
							<a class="btn btn-info" href="${editUrl}" role="button">Edit</a>
							<a class="btn btn-danger" href="${deleteUrl}" role="button">Delete</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<nav>
				<ul class="pager">
				<c:forEach items="${links}" var="link">
					<li ${link.enabled}><a class="btn btn-default" href="${baseUrl}${link.link}">${link.text}</a>
				</c:forEach>
				</ul>
			</nav>
		</div>
	
		<script src="${jqueryUrl}"></script>
	    <script src="${bootstrapJsUrl}"></script>
	</body>
</html>