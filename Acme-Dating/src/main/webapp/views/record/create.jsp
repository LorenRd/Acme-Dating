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

<jstl:choose>
	<jstl:when test="${not empty couple}">

	<form:form action="record/couple/create.do" modelAttribute="record">
		<form:hidden path="id" />
		<form:hidden path="version" />

		<acme:datebox code="record.day" path="day" placeholder="dd/MM/yyyy" />
		<br />

		<acme:textbox code="record.title" path="title" placeholder="Record title" />
		<br />

		<acme:textbox code="record.body" path="body" placeholder="Record body" />
		<br />
   		
   		<acme:textbox code="record.photo" path="photo" placeholder="http://www.photo.com" />
		<br />
   		
   		<acme:select code="record.category" path="category"
   		items="${categories}" itemLabel="title" id="category"/>
   		<br />
   		
		<acme:submit name="save" code="record.save" />

		<acme:cancel url="welcome/index.do" code="record.cancel" />

	</form:form>

</jstl:when>
	<jstl:otherwise>
		<spring:message code="couple.single" />
		<a href="coupleRequest/user/list.do"><spring:message
				code="couple.coupleRequest" /></a>
	</jstl:otherwise>
</jstl:choose>