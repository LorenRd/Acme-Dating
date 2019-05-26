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

	<form:form action ="challenge/user/create.do" modelAttribute ="challenge">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="score"/>
		<form:hidden path="status"/>
		<form:hidden path="sender"/>
		<form:hidden path="recipient"/>
		
		
		<acme:textbox code="challenge.title" path="title" placeholder="Title"/>
		<br />
		<acme:textarea code="challenge.description" path="description"/>
		<br />		
		<acme:textbox code="challenge.endDate" path="endDate" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		
		
		<acme:submit name="save" code="challenge.save"/>
		
		<acme:cancel url="welcome/index.do" code="challenge.cancel"/>
	</form:form>