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
	<img src="${user.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
		<tr>
			<td><spring:message code="user.name" />
			<td data-label="name"><jstl:out value="${user.name}" /></td>
		</tr>
		<tr>
			<td><spring:message code="user.surname" />
			<td data-label="surname"><jstl:out value="${user.surname}" /></td>
		</tr>
		<tr>
			<td><spring:message code="user.email" />
			<td data-label="email"><jstl:out value="${user.email}" /></td>
		</tr>
		<tr>
			<td><spring:message code="user.phone" />
			<td data-label="phone"><jstl:out value="${user.phone}" /></td>
		</tr>
		
		
	</tbody>
</table>

		<security:authorize access="hasRole('USER')">
		<jstl:if test="${user.userAccount.username == pageContext.request.userPrincipal.name}">
		
		<h3> <spring:message code="creditCard" /> </h3>
<table class="ui celled table">
	<tbody>
		
		<tr>
			<td><spring:message code="creditCard.holderName" />
			<td data-label="holderName"><jstl:out value="${user.creditCard.holderName}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.brandName" />
			<td data-label="brandName"><jstl:out value="${user.creditCard.brandName}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.number" />
			<td data-label="number"><jstl:out value="${user.creditCard.number}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.expirationMonth" />
			<td data-label="expirationMonth"><jstl:out value="${user.creditCard.expirationMonth}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.expirationYear" />
			<td data-label="expirationYear"><jstl:out value="${user.creditCard.expirationYear}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="creditCard.CVV" />
			<td data-label="CVV"><jstl:out value="${user.creditCard.CVV}" /></td>
		</tr>
	
	</tbody>
</table>

		</jstl:if>
		</security:authorize>

<!-- Positions -->
<h3> <spring:message code="user.socialNetworks" /> </h3>
<jstl:choose>
<jstl:when test="${not empty socialNetworks}">
<display:table pagesize="5" class="displaytag" name="socialNetworks" requestURI="user/display.do" id="socialNetworks">
		
		<!-- Edit -->
		<security:authorize access="hasRole('USER')">
		<jstl:if test="${user.userAccount.username == pageContext.request.userPrincipal.name}">
		<display:column>
			<a href="socialNetwork/edit.do?socialNetworkId=${socialNetworks.id}"><spring:message code="user.socialNetwork.edit"/></a>
		</display:column>
		</jstl:if>
		</security:authorize>
		
		
		<spring:message code="user.socialNetworks.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
	
		<spring:message code="user.socialNetworks.linkProfile" var="linkProfile" />
		<display:column property="linkProfile" title="${linkProfile}" sortable="true"/>
		
		
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="user.socialNetworks.empty" /> 
</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('USER')">
<jstl:if test="${user.userAccount.username == pageContext.request.userPrincipal.name}">
<input type="button" name="save" class="ui button"
	value="<spring:message code="user.edit" />"
	onclick="javascript: relativeRedir('user/edit.do');" />
	
</jstl:if>
</security:authorize>
