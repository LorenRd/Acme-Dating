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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="experience/company/edit.do" modelAttribute="experience">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		
		<acme:textbox code="experience.title" path="title" placeholder="experience.title"/>
		<br />
		<acme:textarea code="experience.body" path="body"/>
		<br />		
		<acme:textbox code="experience.photo" path="photo" />
		<br />
		<acme:textarea code="experience.ubication" path="ubication"/>
		<br />
		<acme:textbox code="experience.price" path="price" />
		<br />
		<acme:textbox code="experience.coupleLimit" path="coupleLimit" />
		<br />
		<form:label path="features">
		<spring:message code="experience.features" />:
		</form:label>
		<form:select multiple="true" path="features" >
			<form:options items="${features}" itemValue="id" itemLabel="title" />
		</form:select>
		<form:errors cssClass="error" path="features" />	
		<br />	
		<br />		
		<br />
		<acme:submit name="saveFinal" code="experience.saveFinal"/>

		
		<acme:cancel url="welcome/index.do" code="experience.cancel"/>
		
</form:form>
