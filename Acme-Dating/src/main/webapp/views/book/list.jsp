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
			<a href="book/user/display.do?bookId=${row.id}"><spring:message code="book.display" /></a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="book/company/display.do?applicationId=${row.id}"><spring:message
					code="book.display" /></a>
		</display:column>
	</security:authorize>

	<!-- Action links -->
	<security:authorize access="hasRole('ROOKIE')">

		<display:column>
			<jstl:if test="${row.status == 'PENDING'}">
				<a href="application/rookie/edit.do?applicationId=${row.id }"> <spring:message
						code="application.answer" /></a>
			</jstl:if>
		</display:column>

	</security:authorize>

	<!-- Attributes -->

	<spring:message code="application.moment" var="applicationMoment" />
	<display:column property="moment" title="${applicationMoment}"
		sortable="true" />

	<spring:message code="application.problem.title" var="problemTitle" />
	<display:column property="problem.title" title="${problemTitle}"
		sortable="true" />

	<spring:message code="application.problem.statement"
		var="problemStatement" />
	<display:column property="problem.statement"
		title="${problemStatement}" sortable="true" />

	<spring:message code="application.answer.answerText" var="answerText" />
	<display:column property="answer.answerText" title="${answerText}"
		sortable="true" />

	<spring:message code="application.answer.codeLink" var="answerLink" />
	<display:column property="answer.codeLink" title="${answerLink}"
		sortable="true" />

	<spring:message code="application.answer.moment" var="answerMoment" />
	<display:column property="answer.moment" title="${answerMoment}"
		sortable="true" />

	<spring:message code="application.status" var="applicationStatus" />
	<display:column property="status" title="${applicationStatus}"
		sortable="true" />

	<spring:message code="application.position.title" var="positionTitle" />
	<display:column property="position.title" title="${positionTitle}"
		sortable="true" />

	<spring:message code="application.rookie.name" var="rookieName" />
	<display:column property="rookie.name" title="${rookieName}"
		sortable="true" />



</display:table>

<security:authorize access="hasRole('ROOKIE')">
	<div>
		<a href="application/rookie/create.do"><spring:message
				code="application.create" /></a>
	</div>
</security:authorize>
