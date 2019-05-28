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

<security:authorize access="hasRole('USER')">
<jstl:choose>
<jstl:when test="${hasCouple}">
<!-- Couple members -->
<h3><spring:message code="couple.members"/></h3>
<div align="center">
<table class="tab">
<tr>
<td>
<div align="center">
<img src="${principal.photo}" /><br /><br />
<b><jstl:out value="${principal.name}"/></b>
</div>
</td>
<td style="text-align:center; vertical-align: middle;"><h4><jstl:out value="${couple.score}" /> <spring:message code="couple.score"/></h4></td>
<td>
<div align="center">
<img src="${darling.photo}" /><br /><br />
<b><jstl:out value="${darling.name}" /></b>
</div>
</td>
</tr>
</table>
</div>
<!-- Score -->
<!--<h3><spring:message code="couple.score"/></h3>
<h4><jstl:out value="${couple.score}"></jstl:out></h4>  -->
<!-- Trophies -->
<h3><spring:message code="couple.trophies"/></h3>
<div align="center">
<table class="tab">
  <tr>
  <jstl:forEach items="${trophies}" var="trophy" >
  <td>
<div align="center">
<img src="${trophy.picture}" /><br /><br />
<b><jstl:out value="${trophy.title}" /></b>
</div>
</td>
</jstl:forEach> 
  </tr>
</table>
</div>
<h3><spring:message code="couple.options"/></h3>
<div align="center">
<table class="tab">
<tr><td style="text-align:center; vertical-align: middle;"><a href="record/couple/list.do"><img src="images/icons/diary.png" /></a></td><td style="text-align:center; vertical-align: middle;"><a href="task/couple/list.do"><img src="images/icons/to-do-list.png" /></a></td><td style="text-align:center; vertical-align: middle;"><a href="challenge/user/list.do"><img src="images/icons/creativity.png" /></a></td><td style="text-align:center; vertical-align: middle;"><a href="experience/list.do"><img src="images/icons/waterfall.png" /></a></td><td style="text-align:center; vertical-align: middle;"><a href="book/couple/list.do"><img src="images/icons/luggage.png" /></a></td><td style="text-align:center; vertical-align: middle;"><img onclick="deleteCouple()" src="images/icons/broken-heart.png" /></td></tr>
</table>
</div>
</jstl:when>
<jstl:otherwise>
<spring:message code="couple.single" /><a href="coupleRequest/user/list.do"><spring:message code="couple.coupleRequest"/></a>
</jstl:otherwise>
</jstl:choose>
</security:authorize>

<script type="text/javascript">
function deleteCouple(){
	var r = confirm('<spring:message code="couple.break" />');
	if(r == true){
		window.location.href="couple/delete.do";
	}else{
		window.location.href="couple/display.do";
	}
}
</script>

