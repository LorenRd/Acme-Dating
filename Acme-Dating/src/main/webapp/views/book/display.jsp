<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<table class="ui celled table">
	<tbody>
		<tr>
			<td><spring:message code="book.moment" />
			<td data-label="moment"><jstl:out value="${book.moment}" /></td>
		</tr>
		<tr>
			<td><spring:message code="book.date" />
			<td data-label="book.date"><jstl:out value="${book.date}" /></td>
		</tr>
		<tr>
			<td><spring:message code="book.experience.title" />
			<td data-label="book.experience.title"><jstl:out value="${book.experience.title}" /></td>
		</tr>
		<!-- Features -->
		<tr>
		<spring:message code="book.features" />
		<jstl:forEach items="${features}" var="feature" >
        	<li><jstl:out value="${title}"/></li>
		</jstl:forEach>
		</tr>
		
		<tr>
			<td><spring:message code="book.totalPrice" />
			<td data-label="book.totalPrice"><jstl:out value="${totalPrice}" /></td>
		</tr>
	</tbody>
</table>