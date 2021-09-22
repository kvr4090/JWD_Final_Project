<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="logist_title" var="title"/>
<fmt:message key="details" var="details"/>
<fmt:message key="work_title_add_order" var="add_order"/>
<fmt:message key="work_title_add_trip" var="add_trip"/>
<fmt:message key="work_title_add_requirement" var="add_requirement"/>
<fmt:message key="work_title_all_orders" var="all_orders"/>
<fmt:message key="work_title_all_drivers" var="all_drivers"/>
<fmt:message key="work_title_all_trips" var="all_trips"/>
<fmt:message key="work_title_all_shippers" var="all_shippers"/>
<fmt:message key="work_title_add_shipper" var="add_shipper"/>

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
								<div class="work"><a href="controller?command=new_order">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/order.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${add_order}</h2><span class="work__text">${details}</span>
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
								<div class="work"><a href="controller?command=new_trip">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/trip.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${add_trip}</h2><span class="work__text">${details}</span>
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
					<div class="grid-item">
						<div class="grid-item__inner">
							<div class="grid-item__content-wrapper">
								<div class="work"><a href="controller?command=all_orders">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/orders.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${all_orders}</h2><span class="work__text">${details}</span>
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
								<div class="work"><a href="controller?command=all_drivers">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/drivers.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${all_drivers}</h2><span class="work__text">${details}</span>
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
								<div class="work"><a href="controller?command=all_trip">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/trips.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${all_trips}</h2><span class="work__text">${details}</span>
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
								<div class="work"><a href="controller?command=all_shippers">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/clients.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${all_shippers}</h2><span class="work__text">${details}</span>
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
								<div class="work"><a href="controller?command=new_shipper">
									<div class="hoverbox ef-zoom-in light">
										<div class="hb_front"><img src="./images/client.jpg" alt=""/></div>
										<div class="hb_back">
											<h2 class="work__title">${add_shipper}</h2><span class="work__text">${details}</span>
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