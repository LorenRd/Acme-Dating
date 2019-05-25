<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<a href="#"><img width="300px" src="${bannerWelcome }" alt="Acme Dating Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>
					<li><a href="administrator/register.do"><spring:message code="master.page.administrator.register" /></a></li>						
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="user/register.do"><spring:message code = "master.page.user.register" /></a></li>
				<li><a href="company/register.do"><spring:message code = "master.page.company.register" /></a></li>
			</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.terms" /></a>
				<ul>
					<li class="arrow"></li>
					<jstl:if test="${cookie['language'].getValue()=='en'}">
					<li><a href="terms/englishTerms.do"><spring:message
								code="master.page.terms" /></a></li>
					</jstl:if>
					<jstl:if test="${cookie['language'].getValue()=='es'}">
					<li><a href="terms/terms.do"><spring:message
								code="master.page.terms" /></a></li>
					</jstl:if>
				</ul>
			</li>	
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('USER')">
					<li><a href="user/display.do"><spring:message code="master.page.user.display" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
					<li><a href="company/display.do"><spring:message code="master.page.company.display" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="administrator/display.do"><spring:message code="master.page.administrator.display" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

