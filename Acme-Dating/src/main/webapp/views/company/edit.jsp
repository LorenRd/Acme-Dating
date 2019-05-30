<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="company/edit.do" modelAttribute="company" onsubmit="return validatePhone()" >
	<form:hidden path="id" />
	<form:hidden path="version"/>

	<div class="ui equal width form">
		<div class="fields">
			<!-- Commercial Name -->
			<acme:textbox code="company.commercialName" path="commercialName"/>
			<!-- Name -->
			<acme:textbox code="company.name" path="name"/>
			<!-- Surname -->
			<acme:textbox code="company.surname" path="surname"/>
			<!-- Photo -->
			<acme:textbox code="company.photo" path="photo"/>
			<!-- Phone -->
			<acme:textbox code="company.phone" path="phone"/>
			<!-- Email -->
			<acme:textbox code="company.email" path="email"/>
		</div>
			
	</div>

	
	<input type="submit" name="save" id="save"
		value="<spring:message code="company.save" />" />
	
	<acme:cancel url="company/display.do" code="company.cancel"/>


</form:form>
