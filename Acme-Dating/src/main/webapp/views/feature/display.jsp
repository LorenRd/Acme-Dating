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

		<b><spring:message code="feature.title" /></b>:
		<jstl:out value="${feature.title}"/><br/>
	
		<b><spring:message code="feature.description" /></b>:
		<jstl:out value="${feature.description }"/><br/>	
	
		<b><spring:message code="feature.photo" /></b>:
		<acme:image src="${feature.photo}" /><br/>
		
		<b><spring:message code="feature.supplement" /></b>:
		<fmt:formatNumber type="number" maxFractionDigits="2" value="${feature.supplement * vat}"/> <spring:message code="feature.vat" /><br/>	
				
		<!-- Company -->
		<b><spring:message code="feature.company" /></b>:
		<a href="company/display.do?companyId=${feature.company.id}">
			<jstl:out value="${feature.company.commercialName}"/>
		</a><br/>

		
<jstl:if test="${feature.company.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="feature/company/edit.do?featureId=${feature.id}"><spring:message code="feature.delete"/></a><br/>
<br/>
	<a href="feature/company/delete.do?featureId=${feature.id}"><spring:message code="feature.edit"/></a><br/>
</jstl:if>

	