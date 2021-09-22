<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="vehicle_title" var="title"/>
<fmt:message key="vehicle_work_title" var="work_title"/>
<fmt:message key="odometer" var="odometer"/>
<fmt:message key="color" var="color"/>
<fmt:message key="brand" var="brand"/>
<fmt:message key="model" var="model"/>
<fmt:message key="year" var="year"/>
<fmt:message key="vehicle_type" var="type"/>
<fmt:message key="current_condition" var="current_condition"/>
<fmt:message key="add" var="add"/>
<fmt:message key="back" var="back"/>
<fmt:message key="current_condition_true" var="current_condition_true"/>
<fmt:message key="current_condition_false" var="current_condition_false"/>
<fmt:message key="vehicle_plate" var="vehicle_plate"/>
<fmt:message key="note" var="note"/>
<fmt:message key="drive_license" var="drive_license"/>

<title>${title}</title>
   
<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "add_new_vehicle"/>
    	<div class="page-title">
        	<h2 class="work__title">${work_title}</h2>
        </div>
       	<div class="form-group">
      			<c:out value="${vehicle_plate}"></c:out>
                <input type="text" class="form-control item" name="license_plate" pattern="^[0-9A-Z]{7}$" placeholder = "1215AC7" value = "" required>
                <c:out value="${odometer}"></c:out>
                <input type="text" class="form-control item" name="odometer" pattern="^[0-9]{1,7}$" value = "" required>
                <c:out value="${color}"></c:out>
                <input type="text" class="form-control item" name="color" pattern="^[A-zА-я]{3,20}$" value = "" required>
                <c:out value="${brand}"></c:out>
                <input type="text" class="form-control item" name="brand" pattern="^[A-zА-я]{3,20}$" value = "" required>
                <c:out value="${model }"></c:out>
                <input type="text" class="form-control item" name="model" pattern="^[0-9A-zА-я\s]{2,20}$" value = "" required>
                <c:out value="${year}"></c:out>
                <input type="text" class="form-control item" name="year" pattern="^[0-9]{4}$" value = "" required>
				
				<label for="standard-select" style="height: 30px;">${drive_license}</label>
				<div class="select">
					<select id="standard-select" name = "drive_license">
				  			<option>C</option>
				    		<option>CE</option>
				  	</select>
				  	<span class="focus"></span>
				</div>
       			<label for="standard-select" style="height: 30px;">${type}</label>
				<div class="select">
				  <select id="standard-select" name = "vehicle_type">	 
					  <c:forEach var="vehicleType" items="${vehicle_type}">
					  		<option value = "${vehicleType.id}">${vehicleType.type}</option>		  		
					   </c:forEach>
				  </select>
				  <span class="focus"></span>
				</div>
               	<label for="standard-select" style="height: 30px;">${current_condition}</label>
				<div class="select">
				  <select id="standard-select" name = "condition">
				  			<option value="true">${current_condition_true}</option>
				    		<option value="false">${current_condition_false}</option>
				  </select>
				  <span class="focus"></span>
				</div>         
           		<label for="standard-select" style="height: 30px;">${note}</label>
				<textarea class="form-control" name="note" placeholder="Note about vehicle"></textarea>           
        </div>   
        <div class="form-group">
        	<button type="submit" style = "margin-left: 160px;" class="md-btn md-btn--outline-primary">${add}</button>
        </div>
        <div class="form-group">
            <a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
        </div>
	</form>
</div>
    
<%@include file="/WEB-INF/jsp/footer.jsp"%> 