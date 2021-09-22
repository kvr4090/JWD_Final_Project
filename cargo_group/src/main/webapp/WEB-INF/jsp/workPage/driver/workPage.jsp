<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="driver_title" var="title"/>
<fmt:message key="request_trip" var="request_trip"/>
<fmt:message key="finish_trip" var="finish_trip"/>
<fmt:message key="details" var="details"/>

<title>${title}</title>			

<div class="wil-content">
	<section class="awe-section bg-gray">
		<div class="container">
			<div class="grid-css grid-css--masonry" data-col-lg="3" data-col-md="2" data-col-sm="2" data-col-xs="1" data-gap="30">
				<div class="grid__inner">
					<div class="grid-sizer"></div>
								
					<div class="grid-item">
						<div class="grid-item__inner">
							<div class="grid-item__content-wrapper">
								<div class="work"><a href="controller?command=trip_page">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/forDriver.png" alt=""/>
										</div>
										<div class="hb_back">
											<h2 class="work__title">${request_trip}</h2><span class="work__text">${details}</span>
										</div>													
									</div>
								</a>
								</div>		
							</div>
						</div>
					</div>
								
					<div class="grid-item">
						<div class="grid-item__inner">
							<div class="grid-item__content-wrapper">
								<div class="work"><a href="controller?command=finish_trip_page">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/forDriver2.png" alt=""/>
										</div>
										<div class="hb_back">
											<h2 class="work__title">${finish_trip}</h2><span class="work__text">${details}</span>
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