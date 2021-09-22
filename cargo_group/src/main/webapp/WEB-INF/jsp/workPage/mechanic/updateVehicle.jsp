<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="accept" var="accept"/>
<fmt:message key="back" var="back"/>
<fmt:message key="driver_vehicle_title" var="title"/>
<fmt:message key="truck" var="truck"/>
<fmt:message key="vehicle_plate" var="vehicle_plate"/>
<fmt:message key="odometer" var="odometer"/>
<fmt:message key="color" var="color"/>
<fmt:message key="params_vehicle_body" var="params_vehicle_body"/>
<fmt:message key="current_vehicle_type" var="current_vehicle_type"/>
<fmt:message key="current" var="current"/>
<fmt:message key="road_train" var="road_train"/>
<fmt:message key="change_to" var="change_to"/>
<fmt:message key="leave_unchanged" var="leave_unchanged"/>
<fmt:message key="uncouple" var="uncouple"/>
<fmt:message key="current_condition" var="current_condition"/>
<fmt:message key="current_condition_true" var="current_condition_true"/>
<fmt:message key="current_condition_false" var="current_condition_false"/>
<fmt:message key="note" var="note"/>

<title>${title}</title>
    
<div class="registration-form">
	<form action = "controller" method = "post">
		<input type = "hidden" name = "command" value = "update_vehicle"/>
		<input type = "hidden" name = "id" value = "${currentVehicle.id}"/>
        <div class="page-title">
        	<h2 class="work__title">${driver_vehicle_title}</h2>
        </div>
       	<div class="form-group">
       		<label for="standard-select" style="height: 30px;">${truck}: ${currentVehicle.licensePlate}</label><br>
     			<c:out value="${vehicle_plate}"></c:out>
                <input type="text" class="form-control item" name="license_plate" pattern="^[0-9A-Z]{7}$" value = "${currentVehicle.licensePlate}" required>                      
            	<c:out value="${odometer}"></c:out>
                <input type="text" class="form-control item" name="odometer" pattern="^[0-9]{1,7}$" value = "${currentVehicle.odometer}" required>
            	<c:out value="${color}"></c:out>
                <input type="text" class="form-control item" name="color" pattern="^[A-zА-я]{3,20}$" value = "${currentVehicle.color}" required>            
           		<c:out value="${current_vehicle_type}"></c:out>
           		<h2 class="work__title">${currentPrimaryVehicleType}</h2>
       		<label for="standard-select" style="height: 30px;">${params_vehicle_body}</label>
				<div class="select">
		  			<select id="standard-select" name = "vehicle_type">		 
		  				<c:forEach var="vehicleType" items="${primaryVehicleType}">
							<c:if test="${vehicleType.type eq currentPrimaryVehicleType}">
								<option value="${vehicleType.id}" selected>(${current})${currentPrimaryVehicleType}</option>
							</c:if>
		  						<option value = "${vehicleType.id}">${vehicleType.type}</option>		  		
		   				</c:forEach>
		  			</select>
		  			<span class="focus"></span>
				</div>            	
			<label for="standard-select" style="height: 30px;">${road_train},${change_to}:</label>
				<div class="select">
		  			<select id="standard-select" name = "hook_up">
						<option value="${currentVehicle.trailerId}">(${leave_unchanged})${currentSecondaryVehicleType}</option>
		    			<c:forEach var="secondVehicle" items="${secondaryVehicle}">		    	
					 		<c:forEach var="secondaryVehicleType" items="${secondaryVehicleType}">
								<c:if test="${secondVehicle.typeId eq secondaryVehicleType.id}">
									<option value="${secondVehicle.id}">${secondVehicle.licensePlate} - ${secondaryVehicleType.type}</option>
							 	</c:if>
				  	 		</c:forEach>				
		   				</c:forEach>
		    			<option value="0">${uncouple}</option>
		  			</select>
		  			<span class="focus"></span>
				</div>         
            <label for="standard-select" style="height: 30px;">${current_condition}</label>
				<div class="select">
		  			<select id="standard-select" name = "condition">
		  				<c:if test="${currentVehicle.isAvailable eq true}">
		  			 		<option value="true" selected>${current_condition_true}</option>
		    				<option value="false">${current_condition_false}</option>
		  				</c:if>
		  				<c:if test="${currentVehicle.isAvailable eq false}">
		  			 		<option value="true">${current_condition_true}</option>
		    				<option value="false" selected>${current_condition_false}</option>
		  				</c:if>		  	
		  			</select>
		  			<span class="focus"></span>
				</div>
			<div class="form-item">
           		<label for="standard-select" style="height: 30px;">${note}</label>
				<textarea class="form-control" name="note" placeholder="${note}">${currentVehicle.note}</textarea>
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