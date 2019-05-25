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


<b><spring:message code="book.moment" />:</b> <jstl:out value="${book.moment}" />
<br/>
<b><spring:message code="book.date" />:</b><jstl:out value="${book.date}" />
<br/>
<b><spring:message code="book.experience.title" />:</b> <jstl:out value="${book.experience.title}" />
<br/>
<b><spring:message code="book.features" />:</b> <br/>
<jstl:choose>
<jstl:when test="${not empty features}">
<ul>
<jstl:forEach items="${features}" var="feature" >
	<li><jstl:out value="${feature.title}"/></li>
</jstl:forEach>
</ul>
</jstl:when>
<jstl:otherwise>
<spring:message code="book.features.empty" /> 
</jstl:otherwise>
</jstl:choose>

<br/>
<b><spring:message code="book.totalPrice" /></b><jstl:out value="${totalPrice}" />
<br/><br/>
<jstl:if test="${scored}">
<b><spring:message code="book.score" />:</b> <jstl:out value="${book.score}"/>
</jstl:if>
<jstl:if test="${!scored}">
	<a href="book/couple/edit.do?bookId=${book.id}"><spring:message code="book.score.experience"/></a><br/>
</jstl:if>