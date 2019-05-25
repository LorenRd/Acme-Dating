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

<jstl:choose>
	<jstl:when test="${not empty couple}">
	
		<display:table name="records" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag" defaultsort="1">

			<!-- Attributes -->

			<spring:message code="record.day" var="dayHeader" />
			<display:column property="day" title="${dayHeader}" sortable="true" />

			<spring:message code="record.title" var="titleHeader" />
			<display:column property="title" title="${titleHeader}"
				sortable="true" />

			<spring:message code="record.body" var="bodyHeader" />
			<display:column property="body" title="${bodyHeader}" sortable="true" />

			<spring:message code="record.photo" var="photoHeader" />
			<display:column property="photo" title="${photoHeader}"
				sortable="true" />

			<spring:message code="record.category" var="categoryHeader" />
			<display:column property="category.title" title="${categoryHeader}"
				sortable="true" />

			<security:authorize access="hasRole('USER')">

				<!-- Display -->
				<display:column>
					<a href="record/couple/display.do?recordId=${row.id}"><spring:message
							code="record.display" /></a>
				</display:column>

				<!-- Edit -->
				<display:column>
					<a href="record/couple/edit.do?recordId=${row.id}"><spring:message
							code="record.edit" /></a>
				</display:column>

				<!-- Delete -->
				<display:column>
					<a href="record/couple/delete.do?recordId=${row.id}"><spring:message
							code="record.delete" /></a>
				</display:column>

			</security:authorize>

		</display:table>

		<security:authorize access="hasRole('USER')">

			<!-- Create record -->
			<acme:button url="record/couple/create.do" code="record.create" />

		</security:authorize>

	</jstl:when>
	<jstl:otherwise>
		<spring:message code="couple.single" />
		<a href="coupleRequest/user/list.do"><spring:message
				code="couple.coupleRequest" /></a>
	</jstl:otherwise>
</jstl:choose>
