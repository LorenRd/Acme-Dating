<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<form:form action ="challenge/user/create.do" modelAttribute ="challenge">
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textbox code="challenge.title" path="title" placeholder="Title"/>
		<br />
		<acme:textarea code="challenge.description" path="description"/>
		<br />		
		<spring:message code="challenge.endDate"/> <form:input path="endDate" type="text" id="idFechaAltaConv" class="form-control input-sm datepicker " /><form:errors cssClass="error" path="endDate" />		
		<br /><br />
		
		
		<acme:submit name="save" code="challenge.save"/>
		
		<acme:cancel url="welcome/index.do" code="challenge.cancel"/>
	</form:form>
  <script>
  $(function(){
	  $(".datepicker").datetimepicker({
	    format: "d/m/Y H:m"
	  });
	  $.datetimepicker.setLocale('es');
	})
  </script>