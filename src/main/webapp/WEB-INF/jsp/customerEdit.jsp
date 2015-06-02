<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="saveUrl" value="/ui/customer-ui/edit?id=${customerAttribute.id}" />
<c:url var="listUrl" value="/ui/customer-ui" />
<%@ include file="_commonLinks.jsp" %>

<html>
	<c:set var="pageTitle" value="Edit Customer"/>  
	<%@ include file="_commonHeader.jsp" %>
	<body>
		<div class="container">
			<%@ include file="menu.jsp" %>
			<h3>${pageTitle}</h3>
			<form:form class="well form-search" modelAttribute="customerAttribute" method="POST" action="${saveUrl}">
				<table>
					<tr>
						<td><form:label path="id">Id:</form:label></td>
						<td><form:input class="input-medium" path="id" disabled="true" /></td>
					</tr>
					<tr>
						<td><form:label path="firstName">First Name:</form:label></td>
						<td><form:input class="input-medium" path="firstName"/></td>
					</tr>
					<tr>
						<td><form:label path="lastName">Last Name:</form:label></td>
						<td><form:input class="input-medium" path="lastName"/></td>
					</tr>
					<tr>
						<td><form:label path="phoneNumber">Phone Number:</form:label></td>
						<td><form:input class="input-medium" path="phoneNumber"/></td>
					</tr>
					<tr>
						<td><form:label path="email">Email:</form:label></td>
						<td><form:input class="input-medium" path="email"/></td>
					</tr>
				</table>
				<a class="btn btn-default" href="${listUrl}" role="button">Close</a>
				<input class="btn btn-primary" type="submit" value="Save" />
			</form:form>
		</div>
		
		<script src="${jqueryUrl}"></script>
	    <script src="${bootstrapJsUrl}"></script>
	</body>
</html>