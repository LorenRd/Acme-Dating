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
		<form:hidden path="moment"/>
		<form:hidden path="couple"/>
		<form:hidden path="experience"/>
		
		
		<h3><spring:message code="experience.title" /></h3>
		<jstl:out value="${bookForm.experience.title}"/>: <jstl:out value="${bookForm.experience.price}"/>
		<br /><br />
		
		<acme:datebox code="book.date" path="date" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<!-- Features -->
		<b><spring:message code="experience.features" /></b>:
		<br/><ul>
		<jstl:forEach items="${features}" var="feature" >
			<jstl:if test="${feature != null}">
	        	<li><jstl:out value="${feature.title}"/>: <jstl:out value="${feature.supplement}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
		<!-- Seleccionable features -->
		<div>
			<form:select multiple="true" path="features" >
				<form:options items="${features}" itemValue="id" itemLabel="title" />
			</form:select>
			<form:errors cssClass="error" path="features" />	
		</div>
		<br /><br />
		<input type="submit" name="save" id="save" value="<spring:message code="book.save" />" >&nbsp; 
		<acme:cancel url="welcome/index.do" code="book.cancel"/>
	</form:form>




