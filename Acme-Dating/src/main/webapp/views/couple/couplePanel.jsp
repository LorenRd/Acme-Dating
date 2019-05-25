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


<jstl:choose>
<jstl:when test="${hasCouple}">
<!-- Couple members -->
<h3><spring:message code="couple.members"/></h3>
<div align="center">
<table style="width:100%">
<tr>
<td>
<div align="center">
<img src="${principal.photo}" /><br />
<jstl:out value="${principal.name}"></jstl:out>
</div>
</td>
<td>
<div align="center">
<img src="${darling.photo}" /><br />
<jstl:out value="${darling.name}"></jstl:out>
</div>
</td>
</tr>
</table>
</div>
<!-- Score -->
<h3><spring:message code="couple.score"/></h3>
<h4><jstl:out value="${couple.score}"></jstl:out></h4>
<!-- Trophies -->
<h3><spring:message code="couple.trophies"/></h3>
<div align="center">
<table style="width:100%">
  <tr>
  <jstl:forEach items="${trophies}" var="trophy" >
  <td>
<div align="center">
<img src="${trophy.picture}" /><br />
<jstl:out value="${trophy.title}"></jstl:out>
</div>
</td>
</jstl:forEach> 
  </tr>
</table>
</div>

</jstl:when>
<jstl:otherwise>
<spring:message code="couple.single" /><a href="coupleRequest/user/list.do"><spring:message code="couple.coupleRequest"/></a>
</jstl:otherwise>
</jstl:choose>

