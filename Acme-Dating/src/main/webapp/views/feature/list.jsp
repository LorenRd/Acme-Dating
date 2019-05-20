<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->

<display:table name="features" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="feature/company/display.do?featureId=${row.id}"><spring:message code="feature.display"/></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="feature.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		
	<spring:message code="feature.supplement" var="supplementHeader" />
	<display:column property="supplement" title="${supplementHeader}"
		sortable="true" />

		
</display:table>
<br />
<acme:button url="feature/company/create.do" code="feature.create"/>