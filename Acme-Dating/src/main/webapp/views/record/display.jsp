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

<jstl:choose>
	<jstl:when test="${hasCouple == true}">

		<b><spring:message code="record.day" /></b>:
		<jstl:out value="${record.day}"/><br/>	

		<b><spring:message code="record.title" /></b>:
		<jstl:out value="${record.title}"/><br/>

		<b><spring:message code="record.body" /></b>:
		<jstl:out value="${record.body }"/><br/>
	
		<b><spring:message code="record.photo" /></b>:
		<acme:image src="${record.photo}" /><br/>
			
		<b><spring:message code="record.category" /></b>:
		<jstl:out value="${record.category.title}"/><br/>

		<!-- TABLA DE COMENTARIOS -->
<h3> <spring:message code="record.comments" /> </h3>
<jstl:choose>
<jstl:when test="${not empty comments}">
<ul>
<c:forEach items="${comments}" var="comment">
    <!-- Padres: Tienen referencia a record pero no a record comment, opcion de reply -->
  	<jstl:if test="${empty comment.recordComment}">
  		<li><jstl:if test="${comment.actor.id == record.company.id}"><img src="images/badge.png" /></jstl:if><b> <jstl:out value="${comment.actor.name}"/>:</b> <jstl:out value="${comment.body}"/> <a href="recordComment/createReply.do?recordCommentId=${comment.id}"><spring:message code="record.comment.reply"/></a></li>
  	    	<!-- Hijos, habrá que volver a recorrer todos los comments buscando cuales son los hijos y poniendolos -->
			<ul>
			<c:forEach items="${comments}" var="commentChild">
			  	<jstl:if test="${comment.id == commentChild.recordComment.id}">
			  		<li><jstl:if test="${commentChild.actor.id == record.company.id}"><img src="images/badge.png" /></jstl:if><b> <jstl:out value="${commentChild.actor.name}"/>:</b> <jstl:out value="${commentChild.body}"/></li>
				</jstl:if>
			</c:forEach>
			</ul>
  	</jstl:if>
</c:forEach>
</ul>
</jstl:when>
<jstl:otherwise>
<spring:message code="record.comments.empty" /> 
</jstl:otherwise>
</jstl:choose>
<br/><br/>
<security:authorize access="hasRole('USER')">
		<a href="recordComment/create.do?recordId=${record.id}"><spring:message code="record.comment"/></a><br/>
</security:authorize>
<br/>

</jstl:when>
	<jstl:otherwise>
		<spring:message code="couple.single" />
		<a href="coupleRequest/user/list.do"><spring:message
				code="couple.coupleRequest" /></a>
	</jstl:otherwise>
</jstl:choose>