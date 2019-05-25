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

<display:table name="coupleRequests" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="coupleRequest/user/display.do?coupleRequestId=${row.id}"><spring:message
					code="coupleRequest.display" /></a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->

	<spring:message code="coupleRequest.moment" var="coupleRequestMoment" />
	<display:column property="moment" title="${coupleRequestMoment}"
		sortable="true" />

	<spring:message code="coupleRequest.sender.username" var="senderName" />
	<display:column property="sender.userAccount.username" title="${senderName}"
		sortable="true" />

	<spring:message code="coupleRequest.status" var="coupleRequestStatus" />
	<display:column property="status" title="${coupleRequestStatus}"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('USER')">
<jstl:if test="${!requestSended && !haveCouple}">
	<div>
		<a href="coupleRequest/user/create.do"><spring:message
				code="coupleRequest.create" /></a>
	</div>
</jstl:if>
</security:authorize>
