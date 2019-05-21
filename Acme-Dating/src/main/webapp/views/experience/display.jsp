<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

		<b><spring:message code="experience.title" /></b>:
		<jstl:out value="${experience.title}"/><br/>
	
		<b><spring:message code="experience.body" /></b>:
		<jstl:out value="${experience.body }"/><br/>	
	
		<b><spring:message code="experience.photo" /></b>:
		<acme:image src="${experience.photo}" /><br/>
			
		<b><spring:message code="experience.ubication" /></b>:
		<jstl:out value="${experience.ubication }"/><br/>
		
		<b><spring:message code="experience.price" /></b>:
		<jstl:out value="${experience.price * vat}"/><br/>
		
		<b><spring:message code="experience.coupleLimit" /></b>:
		<jstl:out value="${experience.coupleLimit }"/><br/>
		
		<!-- Company -->
		<b><spring:message code="experience.company" /></b>:
		<a href="company/display.do?companyId=${experience.company.id}">
			<jstl:out value="${experience.company.commercialName}"/>
		</a><br/>
		
		<!-- Features -->
		
		<b><spring:message code="experience.problems" /></b>:
		<br/><ul>
		<jstl:forEach items="${experience.features}" var="feature" >
			<jstl:if test="${feature != null}">
	        	<li><jstl:out value="${feature.title}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>

		<!-- TABLA DE COMENTARIOS -->
<h3> <spring:message code="position.audits" /> </h3>
<jstl:choose>
<jstl:when test="${not empty audits}">
<display:table pagesize="5" class="displaytag" name="audits" requestURI="position/display.do" id="audits">
		
		<!-- Display -->
		<display:column>
			<a href="audit/display.do?auditId=${audits.id}"><spring:message code="audit.display"/></a>
		</display:column>
		
		<spring:message code="audit.text" var="text" />
		<display:column property="text" title="${text}" sortable="text"/>
	
		<spring:message code="audit.score" var="score" />
		<display:column property="score" title="${score}" sortable="true"/>
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="position.audits.empty" /> 
</jstl:otherwise>
</jstl:choose>


<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${position.company.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="experience/company/edit.do?positionId=${position.id}"><spring:message code="position.edit"/></a><br/>
<br/>
	<a href="experience/company/delete.do?positionId=${position.id}"><spring:message code="position.delete"/></a><br/>
</jstl:if>
</security:authorize>
