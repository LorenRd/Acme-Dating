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

<h3> <jstl:out value="${company.commercialName}"> </jstl:out> </h3>

<div class="content">
	<img src="${company.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
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



<!-- Experiences -->
<h3> <spring:message code="company.experiences" /> </h3>
<jstl:choose>
<jstl:when test="${not empty experiences}">
<display:table pagesize="5" class="displaytag" name="experiences" requestURI="company/display.do" id="experiences">
		
		<!-- Display -->
		<display:column>
			<a href="experience/display.do?experienceId=${experiences.id}"><spring:message code="company.display"/></a>
		</display:column>
		
		<spring:message code="company.experience.title" var="title" />
		<display:column property="title" title="${title}" sortable="title"/>
	
		<spring:message code="company.experience.description" var="description" />
		<display:column property="body" title="${body}" sortable="true"/>
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="company.experience.empty" /> 
</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
<acme:button url="position/company/create.do" code="position.create"/>
</jstl:if>
</security:authorize>

<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
	<security:authorize access="hasRole('COMPANY')">
<br/>
<br/>
<input type="button" name="save" class="ui button"
	value="<spring:message code="company.edit" />"
	onclick="javascript: relativeRedir('company/edit.do');" />
	
</security:authorize>
</jstl:if>
<br/>
<br/>
<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
	<acme:button url="message/actor/exportData.do" code="actor.exportData"/>
</jstl:if>
	
<br/>
<br/>
<jstl:if test="${company.userAccount.username == pageContext.request.userPrincipal.name}">
	<acme:button url="company/delete.do" code="actor.delete"/>
</jstl:if>