<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<%@include file="/WEB-INF/jsp/header.jsp"%> 

<fmt:message key="guest_title" var="title"/>
<fmt:message key="guest_message" var="message"/>
<fmt:message key="guest_work_title" var="work_title"/>
<fmt:message key="hello" var="hello"/>
<fmt:message key="details" var="details"/>

<title>${title}</title>		

<div class="wil-content">

	<section class="awe-section">
		<div class="container">
			<div class="page-title pb-40">
				<h2 class="page-title__title">${hello} ${sessionScope.user.login}</h2>
				<p class="page-title__text">${message}</p>
				<div class="page-title__divider"></div>
			</div>			
		</div>
	</section>

	<section class="awe-section bg-gray">
		<div class="container">
			<div class="grid-css grid-css--masonry" data-col-lg="3" data-col-md="2" data-col-sm="2" data-col-xs="1" data-gap="30">
				<div class="grid__inner">
					<div class="grid-sizer"></div>
					<div class="grid-item">
						<div class="grid-item__inner">
							<div class="grid-item__content-wrapper">
								<div class="work"><a href="controller?command=contact_page">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/forLogs.png" alt=""/>
										</div>
										<div class="hb_back">
											<h2 class="work__title">${work_title}</h2><span class="work__text">${details}</span>
										</div>													
									</div>
								</a>
								</div>										
							</div>
						</div>
					</div>
																			
				</div>
			</div>				
		</div>
	</section>			
</div>
			
<%@include file="/WEB-INF/jsp/footer.jsp"%>	