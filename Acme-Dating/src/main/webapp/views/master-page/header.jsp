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
					<li><a href="administrator/administrator/register.do"><spring:message code="master.page.administrator.register"/></a></li>
					<li><a href="customisation/administrator/display.do"><spring:message code="master.page.administrator.customisation" /></a></li>	
					<li><a href="dashboard/administrator/display.do"><spring:message code="master.page.administrator.dashboard" /></a></li>		
					<li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
					<li><a href="trophy/administrator/list.do"><spring:message code="master.page.administrator.trophies" /></a></li>					
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
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message code="master.page.experiences" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="experience/list.do"><spring:message
								code="master.page.customer.list.experiences" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="experience/company/list.do"><spring:message code="master.page.company.experiences" /></a></li>
					<li><a href="feature/company/list.do"><spring:message code="master.page.company.features" /></a></li>
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
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message
						code="master.page.experiences" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="experience/list.do"><spring:message
								code="master.page.customer.list.experiences" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.couple" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="couple/display.do"><spring:message code="master.page.couple.display" /></a></li>					
					<li><a href="book/couple/list.do"><spring:message code="master.page.couple.book" /></a></li>
					<li><a href="task/couple/list.do"><spring:message code="master.page.couple.tasks" /></a></li>	
					<li><a href="record/couple/list.do"><spring:message code="master.page.couple.records" /></a></li>
					<li><a href="challenge/user/list.do"><spring:message code="master.page.couple.challenge" /></a></li>									
				</ul>
			</li>
		</security:authorize>		
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="coupleRequest/user/list.do"><spring:message code="master.page.coupleRequest.list" /></a></li>
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
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message
						code="master.page.experiences" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="experience/list.do"><spring:message
								code="master.page.customer.list.experiences" /></a></li>
				</ul>
			</li>
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
					<li><a href="messageBox/actor/list.do"><spring:message code="master.page.profile.messageboxes" /></a></li>					
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

