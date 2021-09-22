<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="finish_trip_title" var="title"/>
<fmt:message key="finish_trip_slogan" var="slogan"/>
<fmt:message key="about_title_truck" var="about_truck"/>
<fmt:message key="about_title_trailer" var="about_trailer"/>
<fmt:message key="about_title_malfunction" var="malfunction"/>
<fmt:message key="radio_truck_true" var="truck_true"/>
<fmt:message key="radio_truck_false" var="truck_false"/>
<fmt:message key="radio_trailer_true" var="trailer_true"/>
<fmt:message key="radio_trailer_false" var="trailer_false"/>
<fmt:message key="finish_trip" var="finish"/>
<fmt:message key="your_message" var="message"/>

<title>${title}</title>				

<div class="wil-content">
				
	<section class="awe-section">
		<div class="container">
			<div class="page-title pb-40">
				<h2 class="page-title__title">${title}</h2>
				<p class="page-title__text">${slogan}</p>
				<div class="page-title__divider"></div>
			</div>				
		</div>
	</section>

	<section class="awe-section bg-gray">
		<div class="container">
			<div class="row">			
				<div class="col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-0 col-lg-offset-1 ">
					<form action = "controller" method = "post">
					<input type = "hidden" name = "command"  value = "finish_trip"/>

						<div class="mt-30">
							<h2 class="about__title">${about_truck} ${sessionScope.vehicle.licensePlate} ${sessionScope.vehicle.brand}</h2>
											
							<label for="f-option" class="l-radio">
							    <input type="radio" name="selectorTruck" checked="checked" value="true">
							    <span>${truck_true}</span>
							</label>
										
							<label for="q-option" class="l-radio">
							    <input type="radio" name="selectorTruck" value="false">
							    <span>${truck_false}</span>
							</label>
											
							<p class="about__subtitle">${malfunction}</p>										
								<div class="form-item">
									<textarea class="form-control" name="messageTruck" placeholder="${message}"  style="min-height:200px;"></textarea>
								</div>			
						</div>
								
						<c:if test="${vehicle.trailerId ne '0' && not empty vehicle.trailerId}">	
							<div class="mt-30">
								<h2 class="about__title">${about_trailer} ${sessionScope.trailer.licensePlate} ${sessionScope.trailer.brand}</h2>							
								
								<label for="a-option" class="l-radio">
								    <input type="radio" name="selectorTrailer" checked="checked" value="true">
								    <span>${trailer_true}</span>
								</label>
								
								<label for="q-option" class="l-radio">
								    <input type="radio" name="selectorTrailer" value="false">
								    <span>${trailer_false}</span>
								</label>
										
								<p class="about__subtitle">${malfunction}</p>										
									<div class="form-item">
										<textarea class="form-control" name="messageTrailer" placeholder="${message}"  style="min-height:200px;"></textarea>
									</div>			
							</div>								
						</c:if>
							
					   <div class="form-group"><br>												
             			  <button type="submit" class="md-btn md-btn--primary md-btn--lg">${finish}</button>		             					
          			   </div>	
					</form>	
				</div>
			</div>
		</div>
	</section>			
</div>
			
<%@include file="/WEB-INF/jsp/footer.jsp"%>