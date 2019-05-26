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

<form:form action="socialNetwork/create.do" modelAttribute="socialNetwork">
	<form:hidden path="id" />
	<form:hidden path="version"/>

	<div class="ui equal width form">
		<div class="fields">
			<!-- Name -->
			<acme:textbox code="socialNetwork.name" path="name"/>
			<!-- Surname -->
			<acme:textbox code="socialNetwork.linkProfile" path="linkProfile"/>
			
		</div>
			
	</div>

	<input type="submit" name="create" id="create"
		value="<spring:message code="socialNetwork.save" />" />
	
	<acme:cancel url="user/display.do" code="socialNetwork.cancel"/>


</form:form>

