<%--
 * edit.jsp
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

<form:form action="category/administrator/edit.do"
	modelAttribute="category">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="category.title" path="title"
		placeholder="Category title" />
	<br />

	<acme:textbox code="category.picture" path="picture"
		placeholder="http://www.picture.com" />
	<br />

	<acme:submit name="save" code="category.save" />

	<acme:cancel url="welcome/index.do" code="category.cancel" />

</form:form>