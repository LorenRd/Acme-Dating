<%--
 * list.jsp
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

<!-- Listing grid -->

<display:table name="categories" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="category.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="category.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}"
		sortable="true" />

	<security:authorize access="hasRole('ADMIN')">

		<!-- Edit -->
		<display:column>
			<a href="category/administrator/edit.do?categoryId=${row.id}"><spring:message
					code="category.edit" /></a>
		</display:column>

		<!-- Delete -->
		<display:column>
			<a href="category/administrator/delete.do?categoryId=${row.id}"><spring:message
					code="category.delete" /></a>
		</display:column>

	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMIN')">

	<!-- Create category -->
	<acme:button url="category/administrator/create.do"
		code="category.create" />

</security:authorize>

