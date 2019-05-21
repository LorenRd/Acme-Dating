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


<spring:message code="book.moment" />: <jstl:out value="${book.moment}" />
<br/>
<spring:message code="book.date" />: <jstl:out value="${book.date}" />
<br/>
<spring:message code="book.experience.title" />: <jstl:out value="${book.experience.title}" />
<br/>
<spring:message code="book.features" />
<ul>
<jstl:forEach items="${features}" var="feature" >
	<li><jstl:out value="${feature.title}"/></li>
</jstl:forEach>
</ul>
<br/>
<spring:message code="book.totalPrice" /><jstl:out value="${totalPrice}" />