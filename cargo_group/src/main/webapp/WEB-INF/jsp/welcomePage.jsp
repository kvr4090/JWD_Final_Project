<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="header.jsp"%> 

<fmt:message key="title" var="title"/>
<fmt:message key="title_span1" var="span1"/>
<fmt:message key="title_span2" var="span2"/>
<fmt:message key="title_span3" var="span3"/>
<fmt:message key="home_page_title" var="home_page"/>
<fmt:message key="info_title" var="info_title"/>
<fmt:message key="details" var="details"/>
<fmt:message key="personal_area" var="personal_area"/>
<fmt:message key="log_in" var="log_in"/>
<fmt:message key="registration" var="registration"/>
<fmt:message key="registrate" var="registrate"/>
<fmt:message key="workpage" var="workpage"/>
<fmt:message key="update_personal_data" var="update_data"/>
<fmt:message key="log_out" var="log_out"/>

<title>${home_page}</title>

<div class="wil-content">

	<section class="awe-section">
		<div class="container">
			<div class="page-title">
				<h2 class="page-title__title">${title}<br>
					<div class="typing__module">
						<div class="typed-strings"><span>${span1}</span><span>${span2}</span><span>${span3}</span>
						</div><span class="typed"></span>
					</div>				
				</h2>
				
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
									<div class="work"><a href="controller?command=about_page">
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/mainAbout.png" alt=""/>
											</div>
											<div class="hb_back">
												<h2 class="work__title">${info_title}</h2><span class="work__text">${details}</span>
											</div>					
										</div>
									</a>
									</div>												
								</div>
							</div>
						</div>
						
						<c:if test="${empty sessionScope.user}">
							<div class="grid-item">
								<div class="grid-item__inner">
									<div class="grid-item__content-wrapper">
										<div class="work"><a href="controller?command=log_in_page">														
											<div class="hoverbox ef-zoom-in light">
												<div class="hb_front"><img src="./images/mainLogIn.png" alt=""/>
												</div>
												<div class="hb_back">
													<h2 class="work__title">${personal_area}</h2><span class="work__text">${log_in}</span>
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
										<div class="work"><a href="controller?command=registration_page">
											<div class="hoverbox ef-zoom-in light">
												<div class="hb_front"><img src="./images/registration.png" alt=""/>
												</div>
												<div class="hb_back">
													<h2 class="work__title">${registration}</h2><span class="work__text">${registrate}</span>
												</div>
											</div>
										</a>
										</div>													
									</div>
								</div>
							</div>
					</c:if>
								
					<c:if test="${not empty sessionScope.user}">
						<div class="grid-item">
							<div class="grid-item__inner">
								<div class="grid-item__content-wrapper">
									<div class="work"><a href="controller?command=workspace">
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/goWork.jpg" alt=""/>
											</div>
											<div class="hb_back">
												<h2 class="work__title">${workpage}</h2><span class="work__text">${log_in}</span>
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
									<div class="work"><a href="controller?command=update_user_data_page">
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/forData.png" alt=""/>
											</div>
											<div class="hb_back">
												<h2 class="work__title">${update_data}</h2><span class="work__text">${details}</span>
											</div>															
										</div>
									</a>
									</div>										
								</div>
							</div>
						</div>
					</c:if>
					
				</div>
			</div>

			<c:if test="${not empty sessionScope.user}">
				<div class="awe-text-center mt-50">
					<a class="md-btn md-btn--outline-primary" href="controller?command=log_out">${log_out}</a>
				</div>
			</c:if>
		</div>
	</section>			
</div>

<%@include file="footer.jsp"%>			