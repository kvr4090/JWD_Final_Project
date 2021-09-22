<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
    
<%@include file="/WEB-INF/jsp/header.jsp"%> 

<fmt:message key="page_about_title" var="title"/>
<fmt:message key="slogan_about_1" var="slog1"/>
<fmt:message key="slogan_about_2" var="slog2"/>
<fmt:message key="slogan_about_3" var="slog3"/>
<fmt:message key="message_about" var="message"/>

<title>${title}</title>

<div class="wil-content">
	<section class="awe-section">
		<div class="container">
			<div class="page-title pb-40">
				<h2 class="page-title__title">${title}</h2>
				<p class="page-title__text">${slog1}</p>
				<div class="page-title__divider"></div>
			</div>					
		</div>
	</section>

	<section class="awe-section bg-gray">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-lg-5 "><img src="./images/mainAbout.png" alt="">
				</div>
				<div class="col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-0 col-lg-offset-1 ">
					<div class="mt-30">
						<h2 class="about__title">${slog2}</h2>
						<p class="about__subtitle">${slog3}</p>
						<p class="about__text">${message}</p>									
					</div>							
				</div>
			</div>
		</div>
	</section>	
</div>
			
<%@include file="/WEB-INF/jsp/footer.jsp"%>	