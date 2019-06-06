<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<form:form action ="coupleRequest/user/create.do" modelAttribute ="coupleRequest">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		
		<div>
		<form:label path="recipient">
			<spring:message code="coupleRequest.recipient" />
		</form:label>
		<form:select id="recipient" path="recipient">
			<form:option value="0" label="----" />
			<form:options items="${recipients}" itemLabel="userAccount.username" />
		</form:select>
		<form:errors path="recipient" cssClass="error" />
		</div>
		
		
		<acme:submit name="save" code="coupleRequest.save"/>
		
		<acme:cancel url="welcome/index.do" code="coupleRequest.cancel"/>
	</form:form>