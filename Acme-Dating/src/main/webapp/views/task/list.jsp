<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Listing grid -->

<jstl:choose>
	<jstl:when test="${not empty couple}">
	<table class="tab2">
	<jstl:forEach items="${tasks}" var="task" >
	<tr style="text-align:center; vertical-align: middle;">
	<td width="30px"><a href="task/couple/changeStatus.do?taskId=${task.id}"><jstl:choose><jstl:when test="${task.isCompleted}"><img src="images/icons/checked.png" /></jstl:when><jstl:otherwise><img src="images/icons/unchecked.png" /></jstl:otherwise></jstl:choose></a></td> <td width="150px" style="text-align:left; vertical-align: middle;">  <a href="task/couple/edit.do?taskId=${task.id}"><b><jstl:out value="${task.title}" /></b></a></td> <td width="30px"><a href="task/couple/delete.do?taskId=${task.id}"><img src="images/icons/minus.png" /></a></td>
	</tr>
	</jstl:forEach>
	</table>

	<security:authorize access="hasRole('USER')">

		<!-- Create task -->
		<acme:button url="task/couple/create.do" code="task.create" />

	</security:authorize>

</jstl:when>
	<jstl:otherwise>
		<spring:message code="couple.single" />
		<a href="coupleRequest/user/list.do"><spring:message
				code="couple.coupleRequest" /></a>
	</jstl:otherwise>
</jstl:choose>
