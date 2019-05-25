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

<c:if test="${not empty couple}">

	<display:table name="tasks" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<!-- Attributes -->

		<spring:message code="task.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}"
			sortable="true" />

		<spring:message code="task.isCompleted" var="isCompletedHeader" />
		<display:column property="isCompleted" title="${isCompletedHeader}"
			sortable="true" />

		<security:authorize access="hasRole('USER')">

			<!-- Edit -->
			<display:column>
				<a href="task/couple/edit.do?taskId=${row.id}"><spring:message
						code="task.edit" /></a>
			</display:column>

			<!-- Delete -->
			<display:column>
				<a href="task/couple/delete.do?taskId=${row.id}"><spring:message
						code="task.delete" /></a>
			</display:column>

		</security:authorize>

	</display:table>

	<security:authorize access="hasRole('USER')">

		<!-- Create task -->
		<acme:button url="task/couple/create.do" code="task.create" />

	</security:authorize>

</c:if>

<c:if test="${empty couple}">

    <jstl:if test="${cookie['language'].getValue()=='en'}">
    
    	<jstl:out value="You do not have couple"></jstl:out>
    
    </jstl:if>
    <jstl:if test="${cookie['language'].getValue()=='es'}">
    
    	<jstl:out value="No tienes pareja"></jstl:out>
    	
    </jstl:if>
    
</c:if>
