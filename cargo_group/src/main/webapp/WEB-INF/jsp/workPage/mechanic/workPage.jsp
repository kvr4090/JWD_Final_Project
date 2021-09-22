<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="mechanic_title" var="title"/>
<fmt:message key="find_all_vehicles" var="find_all_vehicles"/>
<fmt:message key="add_vehicle_type" var="add_vehicle_type"/>
<fmt:message key="add_vehicle" var="add_vehicle"/>
<fmt:message key="add_requirement" var="add_requirement"/>
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
									<div class="work"><a href="controller?command=motorpool">
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/mechanic_motorpool.jpg" alt=""/></div>
											<div class="hb_back">
												<h2 class="work__title">${find_all_vehicles}</h2><span class="work__text">${details}</span>
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
									<div class="work"><a href="controller?command=new_vehicle_type">
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/mechanic_vehicle_type.jpg" alt=""/></div>
											<div class="hb_back">
												<h2 class="work__title">${add_vehicle_type}</h2><span class="work__text">${details}</span>
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
									<div class="work"><a href="controller?command=new_vehicle">													
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/mechanic_new_vehicle.jpg" alt=""/></div>
											<div class="hb_back">
												<h2 class="work__title">${add_vehicle}</h2><span class="work__text">${details}</span>
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
									<div class="work"><a href="controller?command=new_requirement">																			
										<div class="hoverbox ef-zoom-in light">
											<div class="hb_front"><img src="./images/mechanic_requirement.jpg" alt=""/></div>
											<div class="hb_back">
												<h2 class="work__title">${add_requirement}</h2><span class="work__text">${details}</span>
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