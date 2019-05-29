<%--
 * create.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form:form action="trophy/administrator/create.do" modelAttribute="trophy">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="trophy.title" path="title"
		placeholder="Trophy title" />
	<br />

	<acme:textbox code="trophy.picture" path="picture"
		placeholder="http://www.picture.com" />
	<br />
	
	<spring:message code="trophy.scoreToReach" />: <form:input type="number" path="scoreToReach" min="0"/>
	<form:errors cssClass="error" path="scoreToReach" />	
	<br /><br />
	
	<spring:message code="trophy.challengesToComplete" />: <form:input type="number" path="challengesToComplete" min="0"/>
	<form:errors cssClass="error" path="challengesToComplete" />	
	<br /><br />
	

	<spring:message code="trophy.experiencesToShare" />: <form:input type="number" path="experiencesToShare" min="0"/>
	<form:errors cssClass="error" path="experiencesToShare" />	
	<br /><br />
	
	<acme:submit name="save" code="trophy.save" />

	<acme:cancel url="welcome/index.do" code="trophy.cancel" />

</form:form>