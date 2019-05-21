<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<display:table name="books" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="book/couple/display.do?bookId=${row.id}"><spring:message code="book.display" /></a>
		</display:column>
	</security:authorize>


	<!-- Attributes -->

	<spring:message code="book.moment" var="bookMoment" />
	<display:column property="moment" title="${bookMoment}"
		sortable="true" />

	<spring:message code="book.date" var="dateTitle" />
	<display:column property="date" title="${dateTitle}"
		sortable="true" />

	<spring:message code="book.experience" var="experienceText" />
	<display:column property="experience.title" title="${experienceText}"
		sortable="true" />


</display:table>

<!-- Book a experience -->