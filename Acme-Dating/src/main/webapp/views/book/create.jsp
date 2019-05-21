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

	<form:form action ="book/couple/create.do" modelAttribute ="bookForm">
		
		<form:hidden path="id"/>
		
		<div>
			<form:label path="experience">
		<spring:message code="book.experience.title" />
	</form:label>	
	<form:select id="experience" path="experience">
		<form:options items="${experiences}" itemLabel="title" />
	</form:select>
	<form:errors path="experience" cssClass="error" />
		</div>
		
		
		<input type="submit" name="save" id="save"
		value="<spring:message code="book.save" />" >&nbsp; 
		
		<acme:cancel url="welcome/index.do" code="book.cancel"/>
	</form:form>




