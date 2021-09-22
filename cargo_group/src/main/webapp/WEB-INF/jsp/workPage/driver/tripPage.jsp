<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="route_page_title" var="title"/>
<fmt:message key="trip_not_empty" var="trip_not_empty"/>
<fmt:message key="message_trip_not_empty" var="message_trip_not_empty"/>
<fmt:message key="trip_empty" var="trip_empty"/>
<fmt:message key="message_trip_empty" var="message_trip_empty"/>
<fmt:message key="truck" var="truck_trip"/>
<fmt:message key="trailer" var="trailer_trip"/>
<fmt:message key="note_trip" var="note_trip"/>
<fmt:message key="date" var="date"/>
<fmt:message key="start_point" var="start_point"/>
<fmt:message key="end_point" var="end_point"/>
<fmt:message key="distance" var="distance"/>
<fmt:message key="route_on_map" var="route_on_map"/>
<fmt:message key="order_note" var="order_note"/>
<fmt:message key="route" var="route"/>
<fmt:message key="back" var="back"/>
<fmt:message key="start_trip" var="start_trip"/>

<title>${title}</title>			

<div class="wil-content">

	<section class="awe-section">
		<div class="container">
			<div class="page-title pb-40">
				<c:if test="${empty trip}">
					<h2 class="page-title__title">${trip_empty}</h2>
					<p class="page-title__text">${message_trip_empty}</p>
				</c:if>
							
				<c:if test="${not empty trip}">
					<h2 class="page-title__title">${trip_not_empty}</h2>
					<p class="page-title__text">${message_trip_not_empty}</p>
				</c:if>							
			</div>						
		</div>
	</section>

	<section class="awe-section bg-gray" style = "margin-left: 20px; margin-right: 20px;">
		<div class="row">
			<div class="col-lg-6 ">
				<c:if test="${not empty trip}">
					<div class="title">
						<h2 class="title__title">${truck_trip} ${vehicle.licensePlate}, ${vehicle.brand}, ${vehicle.model}, ${vehicle.color}</h2>
										
						<c:if test="${vehicle.trailerId ne '0'}">	
							<h2 class="title__title">${trailer_trip} ${trailer.licensePlate}, ${trailer.brand}, ${trailer.model}, ${trailer.color}</h2>
						</c:if>	
									
						<c:if test="${not empty trip.note}">	
							<p class="page-title__text">${note_trip} ${trip.note}</p>
						</c:if>									
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="card mb-4">
 	       <div class="card-body">  
	  	     <c:if test="${not empty requestScope.trip}">
	             <table class="table table-striped table-class" id= "table-id">
	             	<thead>
	             		<tr>
	                    	<th>${date}</th>
	                       	<th>${start_point}</th>
	                       	<th>${end_point}</th>
	                       	<th>${distance}</th>
	                       	<th>${route_on_map}</th>
	                       	<th>${order_note}</th>
	                    </tr>
	                </thead>
	                <tbody>  
	                    <c:forEach var="order" items="${listOrder}">
	        				<tr>
		                        <td>${trip.startDate}</td>
		                        <td>${order.routeStartPoint}</td>
		                        <td>${order.routeEndPoint}</td>
		                        <td>${order.distance}</td>
		                        <td><a href="https://www.google.by/maps/dir/${order.routeStartPoint}/${order.routeEndPoint}/" target="_blank">${route}</a></td>
		                        <td>${order.note}</td>
	                    	</tr>
	   					</c:forEach>                         
	                </tbody>
	             </table>
	         </c:if>
           </div>
        </div>
        <form action = "controller" method = "post">
		<input type = "hidden" name = "command" value = "start_trip"/>
		<input type = "hidden" name = "trip" value = "${trip.id}">
			<c:if test="${not empty trip}">
				<div class="form-group">
	            	<button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 550px; margin-top: 20px;" >${start_trip}</button>
	         	</div>
			</c:if>
        </form>
        <div class="form-group">
              <a class="md-btn md-btn--outline-primary" style = "margin-left: 580px;" href="controller?command=back">${back}</a>
         </div>
	</section>			
</div>
			
<%@include file="/WEB-INF/jsp/footer.jsp"%>