<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="new_order_title" var="title"/>
<fmt:message key="requirements_carriage" var="requirements_carriage"/>
<fmt:message key="price" var="price"/>
<fmt:message key="load_address" var="load_address"/>
<fmt:message key="unload_address" var="unload_address"/>
<fmt:message key="client_name" var="client_name"/>
<fmt:message key="distance" var="distance"/>
<fmt:message key="note" var="note"/>
<fmt:message key="your_message" var="your_message"/>
<fmt:message key="back" var="back"/>
<fmt:message key="sign_weight" var="sign_weight"/>
<fmt:message key="sign_volume" var="sign_volume"/>
<fmt:message key="sign_height" var="sign_height"/>
<fmt:message key="sign_length" var="sign_length"/>
<fmt:message key="sign_pallets" var="sign_pallets"/>
<fmt:message key="sign_width" var="sign_width"/>
<fmt:message key="add" var="add"/>

<title>${title}</title>

<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "add_order"/>
    	<div class="page-title">
        	<h2 class="work__title">${title}</h2>
        </div>      
    	<div class="form-group">
	        <label for="standard-select" style="height: 30px;">${client_name}</label>
			<div class="select">
				<select id="standard-select" name = "shipper">	 
					<c:forEach var="shipper" items="${listShipper}">
				  		<option value = "${shipper.id}">${shipper.name}</option>			  		
				    </c:forEach>
				</select>
				<span class="focus"></span>
			</div>
			
			<label for="standard-select" style="height: 30px;">${requirements_carriage}</label>
				<div class="select">
					<select id="standard-select" name = "requirement">	 
				  		<c:forEach var="requirement" items="${listRequirement}">
<option value = "${requirement.id}">${requirement.weight}${sign_weight} ${requirement.volume}${sign_volume} 
${requirement.palletsQuantity}${sign_pallets} ${requirement.maxLength}${sign_length} 
${requirement.maxWidth}${sign_width} ${requirement.maxHeight}${sing_height}</option>		  		
				   		</c:forEach>
				  	</select>
				  	<span class="focus"></span>
				</div>
      			<c:out value="${price}"></c:out>
                <input type="text" class="form-control item" name="price" pattern="^[0-9]{1,6}$" required>
                <c:out value="${load_address}"></c:out>
                <input type="text" class="form-control item" name="startPoint" pattern="^[0-9A-zА-я\s]{2,50}$" required>
                <c:out value="${unload_address}"></c:out>
                <input type="text" class="form-control item" name="finishPoint" pattern="^[0-9A-zА-я\s]{2,50}$" required>
                <c:out value="${distance}"></c:out>
                <input type="text" class="form-control item" name="distance" pattern="^[0-9]{1,7}$" required>
                <c:out value="${note}"></c:out>
                <textarea class="form-control" name="note" placeholder="${your_message}" style="min-height:200px;"></textarea>
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