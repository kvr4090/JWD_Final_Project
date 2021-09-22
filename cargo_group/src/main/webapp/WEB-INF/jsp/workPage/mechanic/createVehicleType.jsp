<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="vehicle_type_title" var="title"/>
<fmt:message key="enter_data" var="enter_data"/>
<fmt:message key="vehicle_body" var="vehicle_body"/>
<fmt:message key="truck" var="truck"/>
<fmt:message key="trailer" var="trailer"/>
<fmt:message key="tractor" var="tractor"/>
<fmt:message key="chassis" var="chassis"/>
<fmt:message key="tent" var="tent"/>
<fmt:message key="refrigerator" var="refrigerator"/>
<fmt:message key="isothermic" var="isothermic"/>
<fmt:message key="params_vehicle_body" var="params_vehicle_body"/>
<fmt:message key="sign_weight" var="sign_weight"/>
<fmt:message key="sign_volume" var="sign_volume"/>
<fmt:message key="sing_height" var="sing_height"/>
<fmt:message key="sign_length" var="sign_length"/>
<fmt:message key="sign_pallets" var="sign_pallets"/>
<fmt:message key="sign_width" var="sign_width"/>
<fmt:message key="add" var="add"/>
<fmt:message key="back" var="back"/>
<fmt:message key="note" var="note"/>
<fmt:message key="placeholder_short_note" var="placeholder_note"/>

<title>${title}</title>

<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "add_new_vehicle_type"/>
    	<div class="page-title">
        	<h2 class="work__title">${enter_data}</h2>
       	</div>        
       	<div class="form-group">
      		<label for="standard-select" style="height: 30px;">${chassis}</label>
				<div class="select">
					<select id="standard-select" name = "chassis">
						<option value = "truck">${truck}</option>
					    <option value = "trailer">${trailer}</option>
					</select>
					<span class="focus"></span>
				</div>
		    <label for="standard-select" style="height: 30px;">${vehicle_body}</label>
				<div class="select">
					<select id="standard-select" name = "vehicle_type">
						<option value = "tractor">${tractor}</option>
					  	<option value = "tent">${tent}</option>
					    <option value = "ref">${refrigerator}</option>
					    <option value = "isot">${isothermic}</option>
					</select>
					<span class="focus"></span>
				</div>
      		<label for="standard-select" style="height: 30px;">${params_vehicle_body}</label>
				<div class="select">
		  			<select id="standard-select" name = "requirement">	 
		  				<c:forEach var="requirement" items="${requirement}">
<option value = "${requirement.id}">${requirement.weight}${sign_weight}, ${requirement.volume}${sign_volume}, 
${requirement.palletsQuantity}${sign_pallets}, ${requirement.maxLength}${sign_length}, 
${requirement.maxWidth}${sign_width}, ${requirement.maxHeight}${sing_height}</option>
		   				</c:forEach>
		  			</select>
		  			<span class="focus"></span>
				</div>
                <c:out value="${note}"></c:out>
                <input type="text" class="form-control item" name="note" pattern="^[A-z0-9]{0,10}$" placeholder = "${placeholder_note}" value = "">
        </div>
        <div class="form-group">
        	<button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 170px;">${add}</button>
        </div>
        <div class="form-group">
        	<a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
        </div>
	</form>
</div>
    
<%@include file="/WEB-INF/jsp/footer.jsp"%> 