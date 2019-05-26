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
			<td><spring:message code="challenge.moment" />
			<td data-label="moment"><jstl:out value="${challenge.moment}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.title" />
			<td data-label="title"><jstl:out value="${challenge.title}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.description" />
			<td data-label="description"><jstl:out value="${challenge.description}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.score" />
			<td data-label="score"><jstl:out value="${challenge.score}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.endDate" />
			<td data-label="endDate"><jstl:out value="${challenge.endDate}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.status" />
			<td data-label="status"><jstl:out value="${challenge.status}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.sender.username" />
			<td data-label="sender.userAccount.username"><jstl:out value="${challenge.sender.userAccount.username}" /></td>
		</tr>
		<tr>
			<td><spring:message code="challenge.recipient.username" />
			<td data-label="recipient.userAccount.username"><jstl:out value="${challenge.recipient.userAccount.username}" /></td>
		</tr>
	</tbody>
</table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${challenge.status == 'PENDING' && challenge.sender.userAccount.username != pageContext.request.userPrincipal.name}">
	<br/>
		<a href="challenge/user/reject.do?challengeId=${challenge.id}" ><spring:message code="challenge.reject" /></a><br/>			
		<a href="challenge/user/accept.do?challengeId=${challenge.id}" ><spring:message code="challenge.accept" /></a>								
	</jstl:if>
	
	<jstl:if test="${challenge.status == 'ACCEPTED' && challenge.sender.userAccount.username == pageContext.request.userPrincipal.name}">
	<br/>
		<a href="challenge/user/complete.do?challengeId=${challenge.id}" ><spring:message code="challenge.complete" /></a><br/>								
	</jstl:if>
</security:authorize>