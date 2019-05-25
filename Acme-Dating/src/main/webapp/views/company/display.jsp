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
	<img src="${company.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
	
		<tr>
			<td><spring:message code="company.commercialName" />
			<td data-label="commercialName"><jstl:out value="${company.commercialName}" /></td>
		</tr>
		<tr>
			<td><spring:message code="company.name" />
			<td data-label="name"><jstl:out value="${company.name}" /></td>
		</tr>
		<tr>
			<td><spring:message code="company.surname" />
			<td data-label="surname"><jstl:out value="${company.surname}" /></td>
		</tr>
		<tr>
			<td><spring:message code="company.email" />
			<td data-label="email"><jstl:out value="${company.email}" /></td>
		</tr>
		<tr>
			<td><spring:message code="company.phone" />
			<td data-label="phone"><jstl:out value="${company.phone}" /></td>
		</tr>
		
		
	</tbody>
</table>

		<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
		
		<h3> <spring:message code="creditCard" /> </h3>
<table class="ui celled table">
	<tbody>
		
		<tr>
			<td><spring:message code="creditCard.holderName" />
			<td data-label="holderName"><jstl:out value="${company.creditCard.holderName}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.brandName" />
			<td data-label="brandName"><jstl:out value="${company.creditCard.brandName}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.number" />
			<td data-label="number"><jstl:out value="${company.creditCard.number}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.expirationMonth" />
			<td data-label="expirationMonth"><jstl:out value="${company.creditCard.expirationMonth}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.expirationYear" />
			<td data-label="expirationYear"><jstl:out value="${company.creditCard.expirationYear}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.CVV" />
			<td data-label="CVV"><jstl:out value="${company.creditCard.CVV}" /></td>
		</tr>
	
	</tbody>
</table>

		</jstl:if>
		</security:authorize>

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
<input type="button" name="save" class="ui button"
	value="<spring:message code="company.edit" />"
	onclick="javascript: relativeRedir('company/edit.do');" />
	
</jstl:if>
</security:authorize>

<br/>
<br/>
<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
	<acme:button url="message/actor/exportData.do" code="actor.exportData"/>
</jstl:if>
