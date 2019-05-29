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
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

		<b><spring:message code="experience.title" /></b>:
		<jstl:out value="${experience.title}"/><br/>
	
		<b><spring:message code="experience.body" /></b>:
		<jstl:out value="${experience.body }"/><br/>	
	
		<b><spring:message code="experience.photo" /></b>:
		<acme:image src="${experience.photo}" /><br/>
			
		<b><spring:message code="experience.score" /></b>:
		<jstl:out value="${experience.score }"/><br/>	
			
		<b><spring:message code="experience.ubication" /></b>:
		<jstl:out value="${experience.ubication }"/><br/>
		
		<b><spring:message code="experience.price" /></b>:
		<fmt:formatNumber type="number" maxFractionDigits="2" value="${experience.price * vat}"/> <spring:message code="experience.vat" /><br/>	
		
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
<ul>
<c:forEach items="${comments}" var="comment">
    <!-- Padres: Tienen referencia a experience pero no a experience comment, opcion de reply -->
  	<jstl:if test="${empty comment.experienceComment}">
  		<li><jstl:if test="${comment.actor.id eq experience.company.id}"><img src="images/badge.png" /></jstl:if><b> <jstl:out value="${comment.actor.name}"/>:</b> <jstl:out value="${comment.body}"/> <a href="experienceComment/createReply.do?experienceCommentId=${comment.id}"><spring:message code="experience.comment.reply"/></a></li>
  	    	<!-- Hijos, habrá que volver a recorrer todos los comments buscando cuales son los hijos y poniendolos -->
			<ul>
			<c:forEach items="${commentsChild}" var="commentChild">
			  	<jstl:if test="${comment.id eq commentChild.experienceComment.id}">
			  		<li><jstl:if test="${commentChild.actor.id == experience.company.id}"><img src="images/badge.png" /></jstl:if><b> <jstl:out value="${commentChild.actor.name}"/>:</b> <jstl:out value="${commentChild.body}"/></li>
				</jstl:if>
			</c:forEach>
			</ul>
  	</jstl:if>
</c:forEach>
</ul>
</jstl:when>
<jstl:otherwise>
<spring:message code="experience.comments.empty" /> 
</jstl:otherwise>
</jstl:choose>
<br/><br/>
<security:authorize access="hasAnyRole('COMPANY','USER')">
		<a href="experienceComment/create.do?experienceId=${experience.id}"><spring:message code="experience.comment"/></a><br/>
</security:authorize>
<br/>
<security:authorize access="hasRole('USER')">
	<jstl:if test="${hasCouple}">
		<a href="book/couple/create.do?experienceId=${experience.id}"><spring:message code="experience.book"/></a><br/>
	</jstl:if>
</security:authorize>
<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${experience.company.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="experience/company/edit.do?experienceId=${experience.id}"><spring:message code="experience.edit"/></a><br/>
<br/>
	<a href="experience/company/delete.do?experienceId=${experience.id}"><spring:message code="experience.delete"/></a><br/>
</jstl:if>
</security:authorize>
