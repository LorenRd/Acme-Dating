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

<display:table name="trophies" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="trophy.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="trophy.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}"
		sortable="true" />

	<spring:message code="trophy.scoreToReach" var="scoreToReachHeader" />
	<display:column property="scoreToReach" title="${scoreToReachHeader}"
		sortable="true" />
		
	<spring:message code="trophy.challengesToComplete" var="challengesToCompleteHeader" />
	<display:column property="challengesToComplete" title="${challengesToCompleteHeader}"
		sortable="true" />

	<spring:message code="trophy.experiencesToShare" var="experiencesToShareHeader" />
	<display:column property="experiencesToShare" title="${experiencesToShareHeader}"
		sortable="true" />

	<security:authorize access="hasRole('ADMIN')">

		<!-- Edit -->
		<display:column>
			<a href="trophy/administrator/edit.do?trophyId=${row.id}"><spring:message
					code="trophy.edit" /></a>
		</display:column>

		<!-- Delete -->
		<display:column>
			<a href="trophy/administrator/delete.do?trophyId=${row.id}"><spring:message
					code="trophy.delete" /></a>
		</display:column>

	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMIN')">

	<!-- Create trophy -->
	<acme:button url="trophy/administrator/create.do"
		code="trophy.create" />

</security:authorize>

