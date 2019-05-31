<%--
 * dashboard.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<security:authorize access="hasRole('ADMIN')">

	<h3>
		<spring:message code="administrator.statistics" />
	</h3>

	<table class="displayStyle">
		<tr>
			<th colspan="5"><spring:message code="administrator.statistics" /></th>
		</tr>

		<tr>
			<th><spring:message code="administrator.metrics" /></th>
			<th><spring:message code="administrator.average" /></th>
			<th><spring:message code="administrator.minimum" /></th>
			<th><spring:message code="administrator.maximum" /></th>
			<th><spring:message code="administrator.std" /></th>
			<th><spring:message code="administrator.averagePrice" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.experiencesPerCouple" /></td>
			<td><jstl:out value="${avgExperiencesPerCouple }" /></td>
			<td><jstl:out value="${minExperiencesPerCouple }" /></td>
			<td><jstl:out value="${maxExperiencesPerCouple }" /></td>
			<td><jstl:out value="${stddevExperiencesPerCouple }" /></td>
		</tr>

		<tr>
			<td><spring:message code="administrator.experiencesPerCompany" /></td>
			<td><jstl:out value="${avgExperiencesPerCompany }" /></td>
			<td><jstl:out value="${minExperiencesPerCompany }" /></td>
			<td><jstl:out value="${maxExperiencesPerCompany }" /></td>
			<td><jstl:out value="${stddevExperiencesPerCompany }" /></td>
			<td><jstl:out value="${avgPriceOfExperiencesPerCompany }" /></td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.completedChallengesPerSender" /></td>
			<td><jstl:out value="${avgCompletedChallengesPerSender }" /></td>
			<td><jstl:out value="${minCompletedChallengesPerSender }" /></td>
			<td><jstl:out value="${maxCompletedChallengesPerSender }" /></td>
			<td><jstl:out value="${stddevCompletedChallengesPerSender }" /></td>
		</tr>
	</table>

	<h3>
		<spring:message code="administrator.leastUsedCategory" />
	</h3>
	<jstl:out value="${leastUsedCategory.title}"></jstl:out>

	<!-- Compute -->
	<h3>
		<spring:message code="administrator.controlPanel" />
	</h3>
	<input type="button" name="computeTrophies"
		value="<spring:message code="administrator.computeTrophies" />"
		onclick="redirect: location.href = 'dashboard/administrator/display.do?computeTrophies';" />

</security:authorize>
