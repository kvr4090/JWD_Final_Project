<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="driver_vehicle_title" var="title"/>
<fmt:message key="driver_work_title_truck" var="title_truck"/>
<fmt:message key="driver_work_title_change" var="title_change"/>
<fmt:message key="truck" var="truck_word"/>
<fmt:message key="available" var="available"/>
<fmt:message key="unavailable" var="unavailable"/>
<fmt:message key="trailer" var="trailer_word"/>
<fmt:message key="current" var="current"/>
<fmt:message key="back" var="back"/>
<fmt:message key="accept" var="accept"/>
<fmt:message key="delete" var="delete"/>

<title>${title}</title>
 
<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "change_driver_truck"/>
	<input type = "hidden" name = "id" value = "${truck.id}"/>
	<input type = "hidden" name = "userId" value = "${userId}"/>
	    <div class="page-title">
	    	<h2 class="work__title">${title_truck}</h2>
	    </div>
	    <div class="form-group">
	       <label for="standard-select" style="height: 30px;">${truck_word} ${truck.licensePlate}</label>
	       		<c:forEach var="vehicleType" items="${listVehicleType}">
	       			<c:if test="${truck.typeId eq vehicleType.id}">
	       				<p class="page-title__text">${vehicleType.type}</p>					
					</c:if>
	       		</c:forEach>
	       		<c:choose>
					<c:when test="${truck.isAvailable eq 'true'}">
						<p class="page-title__text">${available}</p>				      
					</c:when>
					<c:when test="${truck.isAvailable eq 'false'}">
						<p class="page-title__text">${unavailable}, ${truck.note}</p>	
					</c:when>
				</c:choose>
	       <label for="standard-select" style="height: 30px;">${trailer_word} ${trailer.licensePlate}</label>
	      		 <c:forEach var="vehicleType" items="${listVehicleType}">
	       			<c:if test="${trailer.typeId eq vehicleType.id}">
	       			<p class="page-title__text">${vehicleType.type}</p>	
					</c:if>
	       		</c:forEach>
	       <c:choose>
				<c:when test="${trailer.isAvailable eq 'true'}">
				<p class="page-title__text">${available}</p>				      
				</c:when>
				<c:when test="${trailer.isAvailable eq 'false'}">
				<p class="page-title__text">${unavailable}, ${truck.note}</p>				
				</c:when>
			</c:choose>
	    </div>
	    <div class="form-group">
	    	<label for="standard-select" style="height: 30px;">${title_change}</label>
			<div class="select">
				<select id="standard-select" name = "newVehicle">
					<option value="${truck.id}">(${current})${truck.licensePlate}</option>
			   		<c:forEach var="vehicle" items="${listVehicle}">
			   			<c:if test="${(vehicle.driveLicense eq 'C') && (vehicle.isAvailable eq 'true')}">
			   				<option value="${vehicle.id}">${vehicle.licensePlate}</option>
			   			</c:if>
					</c:forEach>
					<option value="0">${delete}</option>
				</select>
				<span class="focus"></span>
			</div>
		</div>    
	    <div class="form-group">
	    	<button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 145px; margin-top: 20px;" >${accept}</button>
	    </div>
	    <div class="form-group">
	    	 <a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
	    </div>
	</form>       
</div>
    
<%@include file="/WEB-INF/jsp/footer.jsp"%> 