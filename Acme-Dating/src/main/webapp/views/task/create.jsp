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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="task/couple/create.do" modelAttribute="task">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		
		<acme:textbox code="task.title" path="title" placeholder="Task title"/>
		<br />
		
		<acme:textbox code="task.isCompleted" path="isCompleted" placeholder="true or false"/>
		<br />
		
		<acme:submit name="save" code="task.save"/>
		
		<acme:cancel url="welcome/index.do" code="task.cancel"/>
		
</form:form>