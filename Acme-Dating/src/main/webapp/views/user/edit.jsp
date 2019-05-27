<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="user/edit.do" modelAttribute="user">
	<form:hidden path="id" />
	<form:hidden path="version"/>

	<div class="ui equal width form">
		<div class="fields">
			<!-- Name -->
			<acme:textbox code="user.name" path="name"/>
			<!-- Surname -->
			<acme:textbox code="user.surname" path="surname"/>
			<!-- Photo -->
			<acme:textbox code="user.photo" path="photo"/>
			<!-- Phone -->
			<acme:textbox code="user.phone" path="phone"/>
			<!-- Email -->
			<acme:textbox code="user.email" path="email"/>
		</div>
			
	</div>

	
	<input type="submit" name="save" id="save"
		value="<spring:message code="user.save" />" />
	
	<acme:cancel url="user/display.do" code="user.cancel"/>


</form:form>


<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='user.confirmationPhone'/>","${countryCode}");});
</script>
