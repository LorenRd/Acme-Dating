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

	<form:form action ="book/couple/edit.do" modelAttribute ="bookForm">
		
		<form:hidden path="id"/>
		<form:hidden path="moment"/>
		<form:hidden path="date"/>
		<form:hidden path="couple"/>
		<form:hidden path="experience"/>
		<form:hidden path="features"/>
		
		
		<h3><spring:message code="experience.title" /></h3>
		<jstl:out value="${bookForm.experience.title}"/>
		<br />
		
		<div class="star-rating">
			<input id="star-5" type="radio" name="score" value="5">
			<label for="star-5" title="5 stars">
					<i class="active fa fa-star" ></i>
			</label>
			<input id="star-4" type="radio" name="score" value="4">
			<label for="star-4" title="4 stars">
					<i class="active fa fa-star" ></i>
			</label>
			<input id="star-3" type="radio" name="score" value="3">
			<label for="star-3" title="3 stars">
					<i class="active fa fa-star" ></i>
			</label>
			<input id="star-2" type="radio" name="score" value="2">
			<label for="star-2" title="2 stars">
					<i class="active fa fa-star" ></i>
			</label>
			<input id="star-1" type="radio" name="score" value="1">
			<label for="star-1" title="1 star">
					<i class="active fa fa-star" ></i>
			</label>
		</div>
		<br />
			
		<input type="submit" name="score" id="score" value="<spring:message code="book.save" />" >&nbsp; 
		<acme:cancel url="welcome/index.do" code="book.cancel"/>
	</form:form>




