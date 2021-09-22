<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="motorpool_title" var="title"/>
<fmt:message key="placeholder_search" var="placeholder_search"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="brand" var="brand"/>
<fmt:message key="vehicle_plate" var="vehicle_plate"/>
<fmt:message key="road_train" var="road_train"/>
<fmt:message key="note" var="note"/>
<fmt:message key="current_condition" var="current_condition"/>
<fmt:message key="odometer" var="odometer"/>
<fmt:message key="action" var="action"/>
<fmt:message key="available" var="available"/>
<fmt:message key="in_coupling" var="in_coupling"/>
<fmt:message key="current_condition_true" var="current_condition_true"/>
<fmt:message key="current_condition_false" var="current_condition_false"/>
<fmt:message key="edit" var="edit"/>
<fmt:message key="back" var="back"/>

<title>${title}</title>

<div class="wil-content">
	<section class="awe-section bg-gray">
		<div class="container" style = "margin-left: 190px;margin-right: 56px;">
	        <h3>${title}</h3>
	      	<div class="header_wrap">
	        	<div class="tb_search">
					<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" placeholder="${placeholder_search}" style="width: 140px;" class="form-control">
	        	</div>
	     	</div>
			<table class="table table-striped table-class" id= "table-id">
				<thead>
					<tr>
						<th>${brand}</th>
					    <th>${vehicle_plate}</th>
					    <th>${road_train}</th>
					    <th>${note}</th>
					    <th>${current_condition}</th>
					    <th>${odometer}</th>
					    <th>${action}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vehicle" items="${listVehicle}">
			      		<tr>
			            	<td>${vehicle.brand}</td>
			                <td>${vehicle.licensePlate}</td>
			                <c:choose>
								<c:when test="${vehicle.trailerId eq '0'}">
									<td>${available}</td>
							    </c:when>
							    <c:when test="${vehicle.trailerId ne '0'}">
							        <td>${in_coupling}</td>
							    </c:when>
							</c:choose>
			                    <td>${vehicle.note}</td>
			                    <c:choose>
								    <c:when test="${vehicle.isAvailable eq 'true'}">
								       <td>${current_condition_true}</td>
								    </c:when>
								    <c:when test="${vehicle.isAvailable eq 'false'}">
								       <td>${current_condition_false}</td>
								    </c:when>
								</c:choose>
			                <td>${vehicle.odometer}</td>
			                <td><p class="page-title__text"><a href="controller?command=prepare_to_update_vehicle&vehicle=${vehicle.licensePlate}">${edit}</a></p></td>	                                 
			            </tr>
			   		</c:forEach>
		    	<tbody>
			</table>
			<p>${count_items}</p>
	        <div class="num_rows">
				<div class="form-group">
			 		<select class  ="form-control" name="state" id="maxRows" style="height: 33.5px; width: 80px;">
						 <option value="10">10</option>
						 <option value="15">15</option>
						 <option value="20">20</option>
						 <option value="50">50</option>
						 <option value="70">70</option>
						 <option value="100">100</option>          
					</select>			 		
				</div>
	       	</div>
			<div class='pagination-container'>
				<nav>
					<ul class="pagination"> </ul>
				</nav>
			</div>
	      	<div class="rows_count" style="width: 300px;"></div>
    	</div>
    	<div class="form-group">
        	 <a class="md-btn md-btn--outline-primary" style = "margin-left: 600px;" href="controller?command=back">${back}</a>
        </div>   
	</section>			
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>