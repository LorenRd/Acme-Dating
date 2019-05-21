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
		
		<b><spring:message code="experience.features" /></b>:
		<br/><ul>
		<jstl:forEach items="${experience.features}" var="feature" >
			<jstl:if test="${feature != null}">
	        	<li><jstl:out value="${feature.title}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>

		<!-- TABLA DE COMENTARIOS -->
<h3> <spring:message code="experience.comments" /> </h3>
<jstl:choose>
<jstl:when test="${not empty comments}">
<display:table pagesize="5" class="displaytag" name="comments" requestURI="experience/display.do" id="comments">
		
		<!-- Display -->
		<display:column>
			<a href="experienceComment/createReply.do?experienceCommentId=${comments.id}"><spring:message code="experience.comment.reply"/></a>
		</display:column>
		
		<spring:message code="experience.comment.name" var="name" />
		<display:column property="actor.name" title="${name}" sortable="false"/>
		<spring:message code="experience.comment.body" var="body" />
		<display:column property="body" title="${body}" sortable="false"/>
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="experience.comments.empty" /> 
</jstl:otherwise>
</jstl:choose>
<br/><br/>
<security:authorize access="hasAnyRole('COMPANY','USER')">
	<a href="experienceComment/create.do?experienceId=${experience.id}"><spring:message code="experience.comment"/></a><br/>
</security:authorize>
<br/><br/>

<security:authorize access="hasRole('USER')">
	<a href="book/couple/create.do?experienceId=${experience.id}"><spring:message code="experience.book"/></a><br/>
</security:authorize>
<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${experience.company.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="experience/company/edit.do?experienceId=${experience.id}"><spring:message code="experience.edit"/></a><br/>
<br/>
	<a href="experience/company/delete.do?experienceId=${experience.id}"><spring:message code="experience.delete"/></a><br/>
</jstl:if>
</security:authorize>
