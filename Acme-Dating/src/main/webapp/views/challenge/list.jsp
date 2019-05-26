<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:choose>
	<jstl:when test="${not empty couple}">

<!-- Listing grid -->

<display:table name="myChallenges" id="row1" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="challenge/user/display.do?challengeId=${row1.id}"><spring:message
					code="challenge.display" /></a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->

	<spring:message code="challenge.title" var="challengeTitle" />
	<display:column property="title" title="${challengeTitle}"
		sortable="true" />
		
	<spring:message code="challenge.score" var="challengeScore" />
	<display:column property="score" title="${challengeScore}"
		sortable="true" />
		
	<spring:message code="challenge.endDate" var="challengeEndDate" />
	<display:column property="endDate" title="${challengeEndDate}"
		sortable="true" />

	<spring:message code="challenge.status" var="challengeStatus" />
	<display:column property="status" title="${challengeStatus}"
		sortable="true" />

</display:table>

<!-- Los retos a completar por la pareja -->

<display:table name="hisHerChallenges" id="row2" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="challenge/user/display.do?challengeId=${row2.id}"><spring:message
					code="challenge.display" /></a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->

	<spring:message code="challenge.title" var="challengeTitle" />
	<display:column property="title" title="${challengeTitle}"
		sortable="true" />
		
	<spring:message code="challenge.score" var="challengeScore" />
	<display:column property="score" title="${challengeScore}"
		sortable="true" />
		
	<spring:message code="challenge.endDate" var="challengeEndDate" />
	<display:column property="endDate" title="${challengeEndDate}"
		sortable="true" />

	<spring:message code="challenge.status" var="challengeStatus" />
	<display:column property="status" title="${challengeStatus}"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('USER')">
	<div>
		<a href="challenge/user/create.do"><spring:message
				code="challenge.create" /></a>
	</div>
</security:authorize>

</jstl:when>
	<jstl:otherwise>
		<spring:message code="couple.single" />
		<a href="coupleRequest/user/list.do"><spring:message
				code="couple.coupleRequest" /></a>
	</jstl:otherwise>
</jstl:choose>