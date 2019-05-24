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
			<td><spring:message code="coupleRequest.moment" />
			<td data-label="moment"><jstl:out value="${coupleRequest.moment}" /></td>
		</tr>
		<tr>
			<td><spring:message code="coupleRequest.status" />
			<td data-label="status"><jstl:out value="${coupleRequest.status}" /></td>
		</tr>
		<tr>
			<td><spring:message code="coupleRequest.sender.username" />
			<td data-label="sender.userAccount.username"><jstl:out value="${coupleRequest.sender.userAccount.username}" /></td>
		</tr>
		<tr>
			<td><spring:message code="coupleRequest.recipient.username" />
			<td data-label="recipient.userAccount.username"><jstl:out value="${coupleRequest.recipient.userAccount.username}" /></td>
		</tr>
	</tbody>
</table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${coupleRequest.status == 'PENDING' && coupleRequest.sender.userAccount.username != pageContext.request.userPrincipal.name}">
	<br/>
		<a href="coupleRequest/user/reject.do?coupleRequestId=${coupleRequest.id}" ><spring:message code="coupleRequest.reject" /></a><br/>			
		<a href="coupleRequest/user/accept.do?coupleRequestId=${coupleRequest.id}" ><spring:message code="coupleRequest.accept" /></a>								
		
	</jstl:if>
</security:authorize>