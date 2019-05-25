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


<div class="content">
	<img src="${administrator.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
	
		<tr>
			<td><spring:message code="administrator.name" />
			<td data-label="name"><jstl:out value="${administrator.name}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.surname" />
			<td data-label="surname"><jstl:out value="${administrator.surname}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.email" />
			<td data-label="email"><jstl:out value="${administrator.email}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.phone" />
			<td data-label="phone"><jstl:out value="${administrator.phone}" /></td>
		</tr>
		
		
	</tbody>
</table>

		<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${administrator.userAccount.username == pageContext.request.userPrincipal.name}">
		
		<h3> <spring:message code="creditCard" /> </h3>
<table class="ui celled table">
	<tbody>
		
		<tr>
			<td><spring:message code="creditCard.holderName" />
			<td data-label="holderName"><jstl:out value="${administrator.creditCard.holderName}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.brandName" />
			<td data-label="brandName"><jstl:out value="${administrator.creditCard.brandName}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.number" />
			<td data-label="number"><jstl:out value="${administrator.creditCard.number}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.expirationMonth" />
			<td data-label="expirationMonth"><jstl:out value="${administrator.creditCard.expirationMonth}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.expirationYear" />
			<td data-label="expirationYear"><jstl:out value="${administrator.creditCard.expirationYear}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.CVV" />
			<td data-label="CVV"><jstl:out value="${administrator.creditCard.CVV}" /></td>
		</tr>
	
	</tbody>
</table>

		</jstl:if>
		</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${administrator.userAccount.username == pageContext.request.userPrincipal.name}">
<input type="button" name="save" class="ui button"
	value="<spring:message code="administrator.edit" />"
	onclick="javascript: relativeRedir('administrator/edit.do');" />
	
</jstl:if>
</security:authorize>
