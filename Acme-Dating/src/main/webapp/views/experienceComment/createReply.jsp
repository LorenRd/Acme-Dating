<%--
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
 <%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
<jstl:when test="${not empty experienceComment.experienceComment}">
<b><spring:message code="experienceComment.reply" /></b>: <br /><br />
<jstl:out value="${experienceComment.experienceComment.actor.name}"/>: <jstl:out value="${experienceComment.experienceComment.body}"/>
</jstl:when>
</jstl:choose>

<form:form action="experienceComment/create.do" modelAttribute="experienceComment">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textarea code="experienceComment.body" path="body"/>
		<br />		
		
		<acme:submit name="save" code="experienceComment.save"/>
		
		<acme:cancel url="welcome/index.do" code="experienceComment.cancel"/>
		
</form:form>
